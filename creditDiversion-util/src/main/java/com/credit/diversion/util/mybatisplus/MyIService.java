package com.credit.diversion.util.mybatisplus;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Created by fengzi on 2017/12/27.
 */
public interface MyIService<T> extends IService<T>{

    /**
     * 复制表
     * @param oldTableName
     * @param newTableName
     * @return
     */
    void copyTable(String oldTableName,String newTableName);

    /**
     * <p>
     * 插入一条记录
     * </p>
     *
     * @param entity
     *            实体对象
     * @return boolean
     */
    boolean insert(T entity, String tableName);

    /**
     * <p>
     * 插入（批量），该方法不适合 Oracle
     * </p>
     *
     * @param entityList
     *            实体对象列表
     * @return boolean
     */
    boolean insertBatch(List<T> entityList, String tableName);

    /**
     * <p>
     * 插入（批量）
     * </p>
     *
     * @param entityList
     *            实体对象列表
     * @param batchSize
     *
     * @return boolean
     */
    boolean insertBatch(List<T> entityList, int batchSize, String tableName);

    /**
     * <p>
     * 批量修改插入
     * </p>
     *
     * @param entityList
     *            实体对象列表
     * @return boolean
     */
    boolean insertOrUpdateBatch(List<T> entityList, String tableName);

    /**
     * <p>
     * 批量修改插入
     * </p>
     *
     * @param entityList
     *            实体对象列表
     * @param batchSize
     *
     * @return boolean
     */
    boolean insertOrUpdateBatch(List<T> entityList, int batchSize, String tableName);

    /**
     * <p>
     * 根据 ID 删除
     * </p>
     *
     * @param id
     *            主键ID
     * @return boolean
     */
    boolean deleteById(Serializable id, String tableName);

    /**
     * <p>
     * 根据 columnMap 条件，删除记录
     * </p>
     *
     * @param columnMap
     *            表字段 map 对象
     * @return boolean
     */
    boolean deleteByMap(Map<String, Object> columnMap, String tableName);

    /**
     * <p>
     * 根据 entity 条件，删除记录
     * </p>
     *
     * @param myEntityWrapper
     *            实体包装类 {@link MyEntityWrapper}
     * @return boolean
     */
    boolean delete(MyEntityWrapper<T> myEntityWrapper,String tableName);

    /**
     * <p>
     * 删除（根据ID 批量删除）
     * </p>
     *
     * @param idList
     *            主键ID列表
     * @return boolean
     */
    boolean deleteBatchIds(List<? extends Serializable> idList, String tableName);

    /**
     * <p>
     * 根据 ID 修改
     * </p>
     *
     * @param entity
     *            实体对象
     * @return boolean
     */
    boolean updateById(T entity, String tableName);

    /**
     * <p>
     * 根据 whereEntity 条件，更新记录
     * </p>
     *
     * @param entity
     *            实体对象
     * @param myEntityWrapper
     *            实体包装类 {@link MyEntityWrapper}
     * @return boolean
     */
    boolean update(T entity, MyEntityWrapper<T> myEntityWrapper);

    /**
     * <p>
     * 根据ID 批量更新
     * </p>
     *
     * @param entityList
     *            实体对象列表
     * @return boolean
     */
    boolean updateBatchById(List<T> entityList, String tableName);

    /**
     * <p>
     * TableId 注解存在更新记录，否插入一条记录
     * </p>
     *
     * @param entity
     *            实体对象
     * @return boolean
     */
    boolean insertOrUpdate(T entity, String tableName);

    /**
     * <p>
     * 根据 ID 查询
     * </p>
     *
     * @param id
     *            主键ID
     * @return T
     */
    T selectById(Serializable id, String tableName);

    /**
     * <p>
     * 查询（根据ID 批量查询）
     * </p>
     *
     * @param idList
     *            主键ID列表
     * @return List<T>
     */
    List<T> selectBatchIds(List<? extends Serializable> idList, String tableName);

    /**
     * <p>
     * 查询（根据 columnMap 条件）
     * </p>
     *
     * @param columnMap
     *            表字段 map 对象
     * @return List<T>
     */
    List<T> selectByMap(Map<String, Object> columnMap, String tableName);

    /**
     * <p>
     * 根据 myEntityWrapper，查询一条记录
     * </p>
     *
     * @param myEntityWrapper
     *            实体对象
     * @return T
     */
    T selectOne(MyEntityWrapper<T> myEntityWrapper, String tableName);

    /**
     * <p>
     * 根据 myEntityWrapper，查询一条记录
     * </p>
     *
     * @param myEntityWrapper
     *            {@link MyEntityWrapper}
     * @return Map<String,Object>
     */
    Map<String, Object> selectMap(MyEntityWrapper<T> myEntityWrapper);

    /**
     * <p>
     * 根据 myEntityWrapper，查询一条记录
     * </p>
     *
     * @param myEntityWrapper
     *            {@link MyEntityWrapper}
     * @return Object
     */
    Object selectObj(MyEntityWrapper<T> myEntityWrapper);

    /**
     * <p>
     * 根据 myEntityWrapper 条件，查询总记录数
     * </p>
     *
     * @param myEntityWrapper
     *            实体对象
     * @return int
     */
    int selectCount(MyEntityWrapper<T> myEntityWrapper);

    /**
     * 查询数量 不能group by
     * @param wrapper
     * @return
     */
    public BigDecimal selectNumber(MyEntityWrapper<T> wrapper);

    /**
     * <p>
     * 查询列表
     * </p>
     *
     * @param myEntityWrapper
     *            实体包装类 {@link MyEntityWrapper}
     * @return
     */
    List<T> selectList(MyEntityWrapper<T> myEntityWrapper);

    /**
     * <p>
     * 查询列表
     * </p>
     *
     * @param myEntityWrapper
     *            {@link MyEntityWrapper}
     * @return
     */
    List<Map<String, Object>> selectMaps(MyEntityWrapper<T> myEntityWrapper);

    /**
     * <p>
     * 根据 myEntityWrapper 条件，查询全部记录
     * </p>
     *
     * @param myEntityWrapper
     *            实体对象封装操作类（可以为 null）
     * @return List<Object>
     */
    List<Object> selectObjs(MyEntityWrapper<T> myEntityWrapper);

    /**
     * <p>
     * 翻页查询
     * </p>
     *
     * @param page
     *            翻页对象
     * @param myEntityWrapper
     *            {@link MyEntityWrapper}
     * @return
     */
    @SuppressWarnings("rawtypes")
    Page<Map<String, Object>> selectMapsPage(Page page, MyEntityWrapper<T> myEntityWrapper);

    /**
     * <p>
     * 翻页查询
     * </p>
     *
     * @param page
     *            翻页对象
     * @param myEntityWrapper
     *            实体包装类 {@link MyEntityWrapper}
     * @return
     */
    Page<T> selectPage(Page<T> page, MyEntityWrapper<T> myEntityWrapper);
}
