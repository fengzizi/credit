/**
 * Copyright (c) 2011-2016, hubin (jobob@qq.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.credit.diversion.util.mybatisplus;

import com.baomidou.mybatisplus.entity.TableFieldInfo;
import com.baomidou.mybatisplus.entity.TableInfo;
import com.baomidou.mybatisplus.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.mapper.SqlHelper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.toolkit.*;
import com.baomidou.mybatisplus.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.toolkit.StringUtils;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanMap;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * IService 实现类（ 泛型：M 是 mapper 对象，T 是实体 ， PK 是主键泛型 ）
 * </p>
 *
 * @author hubin
 * @Date 2016-04-20
 */
public class MyServiceImpl<M extends MyBaseMapper<T>, T> extends ServiceImpl<M, T> implements MyIService<T> {

    private static final Log logger = LogFactory.getLog(MyServiceImpl.class);

    @Autowired
    protected M baseMapper;

    /**
     * 复制表
     *
     * @param oldTableName
     * @param newTableName
     * @return
     */
    public void copyTable(String oldTableName, String newTableName) {
        baseMapper.dropTable(newTableName);
        baseMapper.copyTable(oldTableName, newTableName);
    }

    @SuppressWarnings("unchecked")
    protected Class<T> currentModleClass() {
        return ReflectionKit.getSuperClassGenricType(getClass(), 1);
    }

    /**
     * <p>
     * 批量操作 SqlSession
     * </p>
     */
    protected SqlSession sqlSessionBatch() {
        return SqlHelper.sqlSessionBatch(currentModleClass());
    }

    /**
     * <p>
     * 判断数据库操作是否成功
     * </p>
     * <p>
     * 注意！！ 该方法为 Integer 判断，不可传入 int 基本类型
     * </p>
     *
     * @param result 数据库操作返回影响条数
     * @return boolean
     */
    protected static boolean retBool(Integer result) {
        return SqlHelper.retBool(result);
    }

    /**
     * <p>
     * TableId 注解存在更新记录，否插入一条记录
     * </p>
     *
     * @param entity 实体对象
     * @return boolean
     */
    public boolean insertOrUpdate(T entity, String tableName) {
        if (null != entity) {
            Class<?> cls = entity.getClass();
            TableInfo tableInfo = TableInfoHelper.getTableInfo(cls);
            if (null != tableInfo && StringUtils.isNotEmpty(tableInfo.getKeyProperty())) {
                Object idVal = ReflectionKit.getMethodValue(cls, entity, tableInfo.getKeyProperty());
                if (StringUtils.checkValNull(idVal)) {
                    return insert(entity, tableName);
                } else {
                    /*
                     * 更新成功直接返回，失败执行插入逻辑
					 */
                    boolean rlt = updateById(entity);
                    if (!rlt) {
                        return insert(entity, tableName);
                    }
                    return rlt;
                }
            } else {
                throw new MybatisPlusException("Error:  Can not execute. Could not find @TableId.");
            }
        }
        return false;
    }

    public boolean insert(T entity, String tableName) {
        Class<?> cl = entity.getClass();
        if (entity != null) {
            BeanMap beanMap = BeanMap.create(entity);
            TableInfo table = TableInfoHelper.getTableInfo(cl);
            List<TableFieldInfo> list = table.getFieldList();
            StringBuilder sqlColumns = new StringBuilder("(");
            StringBuilder sqlValues = new StringBuilder("(");
            for (int i = 0; i < list.size(); i++) {
                for (Object key : beanMap.keySet()) {
                    if (list.get(i).getProperty().equals(key)) {//字段对应实体属性
                        if (!org.springframework.util.StringUtils.isEmpty(beanMap.get(key))) {
                            sqlColumns.append(list.get(i).getColumn()).append(",");
                            if ("java.lang.String".equals(list.get(i).getPropertyType().getName())) {//字符串
                                sqlValues.append(StringUtils.quotaMark( beanMap.get(key))).append(",");
                            } else if ("java.util.Date".equals(list.get(i).getPropertyType().getName())) {//时间类型
                                SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                sqlValues.append(StringUtils.quotaMark(fmt.format(((Date) beanMap.get(key))))).append(",");
                            } else {
                                sqlValues.append(beanMap.get(key)).append(",");
                            }
                            break;
                        }
                    }
                }
            }
            StringBuilder sql = new StringBuilder(sqlColumns.substring(0, sqlColumns.length() - 1) + ") VALUES ");
            sql.append(sqlValues.substring(0, sqlValues.length() - 1));
            sql.append(")");
            MyEntityWrapper<T> wrapper = new MyEntityWrapper<>(tableName);
            wrapper.setSqlInsert(sql);
            return retBool(baseMapper.insertTN(entity, wrapper));
        }
        return false;
    }

    public boolean insertBatch(List<T> entityList, String tableName) {
        return insertBatch(entityList, 30, tableName);
    }

    public boolean insertOrUpdateBatch(List<T> entityList, String tableName) {
        return insertOrUpdateBatch(entityList, 30, tableName);
    }

    public boolean insertOrUpdateBatch(List<T> entityList, int batchSize, String tableName) {
        if (CollectionUtils.isEmpty(entityList)) {
            throw new IllegalArgumentException("Error: entityList must not be empty");
        }
        try {
            SqlSession batchSqlSession = sqlSessionBatch();
            int size = entityList.size();
            for (int i = 0; i < size; i++) {
                insertOrUpdate(entityList.get(i), tableName);
                if (i % batchSize == 0) {
                    batchSqlSession.flushStatements();
                }
            }
            batchSqlSession.flushStatements();
        } catch (Exception e) {
            logger.warn("Error: Cannot execute insertOrUpdateBatch Method. Cause:" + e);
            return false;
        }
        return true;
    }

    /**
     * 批量插入
     *
     * @param entityList
     * @param batchSize
     * @return
     */
    public boolean insertBatch(List<T> entityList, int batchSize, String tableName) {
        if (CollectionUtils.isEmpty(entityList)) {
            throw new IllegalArgumentException("Error: entityList must not be empty");
        }
        SqlSession batchSqlSession = sqlSessionBatch();
        try {
            int size = entityList.size();
            for (int i = 0; i < size; i++) {
                this.insert(entityList.get(i), tableName);
                if (i % batchSize == 0) {
                    batchSqlSession.flushStatements();
                }
            }
            batchSqlSession.flushStatements();
        } catch (Exception e) {
            logger.warn("Error: Cannot execute insertBatch Method. Cause:" + e);
            return false;
        }
        return true;

    }

    public boolean deleteById(Serializable id, String tableName) {
        return retBool(baseMapper.deleteByIdTN(id, tableName));
    }

    public boolean deleteByMap(Map<String, Object> columnMap, String tableName) {
        if (MapUtils.isEmpty(columnMap)) {
            throw new MybatisPlusException("deleteByMap columnMap is empty.");
        }
        return retBool(baseMapper.deleteByMapTN(columnMap, tableName));
    }

    public boolean delete(MyEntityWrapper<T> wrapper,String tableName) {
        wrapper.tableName(tableName);
        return retBool(baseMapper.deleteTN(wrapper));
    }

    public boolean deleteBatchIds(List<? extends Serializable> idList, String tableName) {

        return retBool(baseMapper.deleteBatchIdsTN(idList, tableName));
    }

    public boolean updateById(T entity, String tableName) {
        if (entity != null) {
            Class<?> cl = entity.getClass();
            BeanMap beanMap = BeanMap.create(entity);
            TableInfo table = TableInfoHelper.getTableInfo(cl);
            List<TableFieldInfo> list = table.getFieldList();
            MyEntityWrapper<T> wrapper = new MyEntityWrapper<>(tableName);
            wrapper.setSqlUpdate(sqlUpdateStr(list, beanMap, table));
            for (Object key : beanMap.keySet()) {
                if (table.getKeyProperty().equals(key)) {//主键
                    if (org.springframework.util.StringUtils.isEmpty(beanMap.get(key))) {
                        return false;
                    } else {
                        wrapper.eq(table.getKeyProperty(), beanMap.get(key));
                        break;
                    }
                }
            }
            return retBool(baseMapper.updateTN(wrapper));
        }
        return false;
    }

    public boolean update(T entity, MyEntityWrapper<T> wrapper) {
        if (entity != null) {
            Class<?> cl = entity.getClass();
            BeanMap beanMap = BeanMap.create(entity);
            TableInfo table = TableInfoHelper.getTableInfo(cl);
            List<TableFieldInfo> list = table.getFieldList();
            wrapper.setSqlUpdate(sqlUpdateStr(list, beanMap, table));
            return retBool(baseMapper.updateTN(wrapper));
        }
        return false;
    }

    /**
     * 更新操作时sql字符串拼接
     *
     * @param list
     * @param beanMap
     * @param table
     * @return
     */
    public StringBuilder sqlUpdateStr(List<TableFieldInfo> list, BeanMap beanMap, TableInfo table) {
        StringBuilder sql = new StringBuilder("SET ");
        for (int i = 0; i < list.size(); i++) {
            for (Object key : beanMap.keySet()) {
                if (list.get(i).getProperty().equals(key) && !table.getKeyProperty().equals(key)) {//字段对应实体属性 不包含主键
                    if (!org.springframework.util.StringUtils.isEmpty(beanMap.get(key))) {
                        sql.append(list.get(i).getColumn()).append("=");
                        if ("java.lang.String".equals(list.get(i).getPropertyType().getName())) {//字符串
                            sql.append(StringUtils.quotaMark( beanMap.get(key))).append(",");
                        } else if ("java.util.Date".equals(list.get(i).getPropertyType().getName())) {//时间类型
                            sql.append(StringUtils.quotaMark(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(((Date) beanMap.get(key))))).append(",");
                        } else {
                            sql.append(beanMap.get(key)).append(",");
                        }
                        break;
                    }
                }
            }
        }
        sql = new StringBuilder(sql.substring(0, sql.length() - 1));
        return sql;
    }

    public boolean updateBatchById(List<T> entityList, String tableName) {//待优化前期建议不要用
        if (CollectionUtils.isEmpty(entityList)) {
            throw new IllegalArgumentException("Error: entityList must not be empty");
        }
        SqlSession batchSqlSession = sqlSessionBatch();
        try {
            int size = entityList.size();
            for (int i = 0; i < size; i++) {
                this.updateById(entityList.get(i), tableName);
                if (i % 30 == 0) {
                    batchSqlSession.flushStatements();
                }
            }
            batchSqlSession.flushStatements();
        } catch (Exception e) {
            logger.warn("Error: Cannot execute insertBatch Method. Cause:" + e);
            return false;
        }
        return true;
    }

    public T selectById(Serializable id, String tableName) {
        return baseMapper.selectByIdTN(id, tableName);
    }

    public List<T> selectBatchIds(List<? extends Serializable> idList, String tableName) {
        return baseMapper.selectBatchIdsTN(idList, tableName);
    }

    public List<T> selectByMap(Map<String, Object> columnMap, String tableName) {
        return baseMapper.selectByMapTN(columnMap, tableName);
    }

    public T selectOne(MyEntityWrapper<T> wrapper,String tableName) {
        wrapper.tableName(tableName);
        sqlSelectStr(wrapper);
        return SqlHelper.getObject(baseMapper.selectListTN(wrapper));
    }

    public Map<String, Object> selectMap(MyEntityWrapper<T> wrapper) {
        sqlSelectStr(wrapper);
        return SqlHelper.getObject(baseMapper.selectMapsTN(wrapper));
    }

    public Object selectObj(MyEntityWrapper<T> wrapper) {
        sqlSelectStr(wrapper);
        return SqlHelper.getObject(baseMapper.selectObjsTN(wrapper));
    }

    public int selectCount(MyEntityWrapper<T> wrapper) {
        if(StringUtils.isEmpty(wrapper.getSqlSelect())){
            wrapper.setSqlSelect("count(1)");
        }
        return SqlHelper.retCount(baseMapper.selectCountTN(wrapper));
    }

    /**
     * 查询数量 不能group by
     *
     * @param wrapper
     * @return
     */
    public BigDecimal selectNumber(MyEntityWrapper<T> wrapper) {
        return baseMapper.selectNumber(wrapper);
    }

    public List<T> selectList(MyEntityWrapper<T> wrapper) {
        sqlSelectStr(wrapper);
        return baseMapper.selectListTN(wrapper);
    }

    public List<Map<String, Object>> selectMaps(MyEntityWrapper<T> wrapper) {
        sqlSelectStr(wrapper);
        return baseMapper.selectMapsTN(wrapper);
    }

    public List<Object> selectObjs(MyEntityWrapper<T> wrapper) {
        sqlSelectStr(wrapper);
        return baseMapper.selectObjsTN(wrapper);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    public Page<Map<String, Object>> selectMapsPage(Page page, MyEntityWrapper<T> wrapper) {
        if (null != wrapper) {
            wrapper.orderBy(page.getOrderByField(), page.isAsc());
        }
        sqlSelectStr(wrapper);
        page.setRecords(baseMapper.selectMapsPageTN(page, wrapper));
        return page;
    }

    public Page<T> selectPage(Page<T> page, MyEntityWrapper<T> wrapper) {
        if (null != wrapper) {
            wrapper.orderBy(page.getOrderByField(), page.isAsc());
        }
        sqlSelectStr(wrapper);
        page.setRecords(baseMapper.selectPageTN(page, wrapper));
        return page;
    }

    /**
     * 更新操作时sql字符串拼接
     * @return
     */
    private void sqlSelectStr(MyEntityWrapper<T> wrapper) {
        if(org.springframework.util.StringUtils.isEmpty(wrapper.getSqlSelect())){
            TableInfo table= TableInfoHelper.getTableInfo(currentModleClass());
            StringBuilder sql = new StringBuilder("");
            sql.append(table.getKeyColumn()+" AS "+table.getKeyProperty()+",");
            List<TableFieldInfo> list=table.getFieldList();
            for (int i = 0; i < list.size(); i++) {
                sql.append(list.get(i).getColumn()+" AS "+list.get(i).getProperty()+",");
            }
            sql = new StringBuilder(sql.substring(0, sql.length() - 1));
            wrapper.setSqlSelect(sql.toString());
        }
    }

    public static void main(String[] args) {
        String ss=StringUtils.quotaMark("ssssssss");
        System.err.println(ss);
    }
}
