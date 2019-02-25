/**
 * Copyright (c) 2011-2020, hubin (jobob@qq.com).
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

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import org.apache.ibatis.annotations.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * Mapper 继承该接口后，无需编写 mapper.xml 文件，即可获得CRUD功能
 * </p>
 * <p>
 * 这个 Mapper 支持 id 泛型
 * </p>
 *
 * @author hubin
 * @Date 2016-01-23
 */
public interface MyBaseMapper<T> extends BaseMapper<T> {

    /**
     * 复制表
     *
     * @param oldTableName
     * @param newTableName
     * @return
     */
    @Select("CREATE TABLE ${nt} LIKE ${ot}")
    void copyTable(@Param("ot") String oldTableName, @Param("nt") String newTableName);

    /**
     * 删除表
     *
     * @param tableName
     * @return
     */
    @Update("DROP TABLE IF EXISTS ${t}")
    int dropTable(@Param("t") String tableName);

    /**
     * <p>
     * 插入一条记录
     * </p>
     *
     * @param entity
     * @return int
     */
    Integer insertTN(@Param("e") T entity, @Param("ew") MyEntityWrapper<T> myEntityWrapper);

    /**
     * <p>
     * 根据 ID 删除
     * </p>
     *
     * @param id 主键ID
     * @return int
     */
    @Delete("DELETE FROM ${tableName} WHERE id = #{id}")
    Integer deleteByIdTN(@Param("id") Serializable id, @Param("tableName") String tableName);

    /**
     * <p>
     * 根据 columnMap 条件，删除记录
     * </p>
     *
     * @param columnMap 表字段 map 对象
     * @return int
     */
    Integer deleteByMapTN(@Param("cm") Map<String, Object> columnMap, @Param("tableName") String tableName);

    /**
     * <p>
     * 根据 entity 条件，删除记录
     * </p>
     *
     * @param myEntityWrapper 实体对象封装操作类
     * @return int
     */
    @Delete("DELETE FROM ${ew.tableName} ${ew.sqlSegment}")
    Integer deleteTN(@Param("ew") MyEntityWrapper<T> myEntityWrapper);

    /**
     * <p>
     * 删除（根据ID 批量删除）
     * </p>
     *
     * @param idList 主键ID列表
     * @return int
     */
    Integer deleteBatchIdsTN(@Param("idList") List<? extends Serializable> idList, @Param("tableName") String tableName);

    /**
     * <p>
     * 根据 whereEntity 条件，更新记录
     * </p>
     *
     * @param myEntityWrapper 实体对象封装操作类（可以为 null）
     * @return
     */
    @Update("UPDATE ${ew.tableName} ${ew.sqlUpdate} ${ew.sqlSegment}")
    Integer updateTN(@Param("ew") MyEntityWrapper<T> myEntityWrapper);

    /**
     * <p>
     * 根据 ID 查询
     * </p>
     *
     * @param id 主键ID
     * @return T
     */
    T selectByIdTN(@Param("id") Serializable id, @Param("tableName") String tableName);

    /**
     * <p>
     * 查询（根据ID 批量查询）
     * </p>
     *
     * @param idList 主键ID列表
     * @return List<T>
     */
    List<T> selectBatchIdsTN(@Param("idList") List<? extends Serializable> idList, @Param("tableName") String tableName);

    /**
     * <p>
     * 查询（根据 columnMap 条件）
     * </p>
     *
     * @param columnMap 表字段 map 对象
     * @return List<T>
     */
    List<T> selectByMapTN(@Param("cm") Map<String, Object> columnMap, @Param("tableName") String tableName);

    /**
     * <p>
     * 根据 myEntityWrapper 条件，查询总记录数
     * </p>
     *
     * @param myEntityWrapper 实体对象
     * @return int
     */
    @Select("SELECT ${ew.sqlSelect} FROM ${ew.tableName} ${ew.sqlSegment}")
    Integer selectCountTN(@Param("ew") MyEntityWrapper<T> myEntityWrapper);

    /**
     * 查询数量 不能group by
     *
     * @param myEntityWrapper
     * @return
     */
    @Select("SELECT ${ew.sqlSelect} FROM ${ew.tableName} ${ew.sqlSegment}")
    BigDecimal selectNumber(@Param("ew") MyEntityWrapper<T> myEntityWrapper);

    /**
     * <p>
     * 根据 entity 条件，查询全部记录
     * </p>
     *
     * @param myEntityWrapper 实体对象封装操作类（可以为 null）
     * @return List<T>
     */
    @Select("SELECT ${ew.sqlSelect} FROM ${ew.tableName} ${ew.sqlSegment}")
    List<T> selectListTN(@Param("ew") MyEntityWrapper<T> myEntityWrapper);

    /**
     * <p>
     * 根据 myEntityWrapper 条件，查询全部记录
     * </p>
     *
     * @param myEntityWrapper 实体对象封装操作类（可以为 null）
     * @return List<T>
     */
    @Select("SELECT ${ew.sqlSelect} FROM ${ew.tableName} ${ew.sqlSegment}")
    List<Map<String, Object>> selectMapsTN(@Param("ew") MyEntityWrapper<T> myEntityWrapper);

    /**
     * <p>
     * 根据 myEntityWrapper 条件，查询全部记录
     * </p>
     *
     * @param myEntityWrapper 实体对象封装操作类（可以为 null）
     * @return List<Object>
     */
    List<Object> selectObjsTN(@Param("ew") MyEntityWrapper<T> myEntityWrapper);

    /**
     * <p>
     * 根据 entity 条件，查询全部记录（并翻页）
     * </p>
     * <p>
     * 分页查询条件（可以为 RowBounds.DEFAULT）
     *
     * @param myEntityWrapper 实体对象封装操作类
     * @return List<T>
     */
    @Select("SELECT ${ew.sqlSelect} FROM ${ew.tableName} ${ew.sqlSegment}")
    List<T> selectPageTN(@Param("page") Pagination page, @Param("ew") MyEntityWrapper<T> myEntityWrapper);

    /**
     * <p>
     * 根据 myEntityWrapper 条件，查询全部记录（并翻页）
     * </p>
     *
     * @param page            分页查询条件（可以为 RowBounds.DEFAULT）
     * @param myEntityWrapper 实体对象封装操作类
     * @return List<Map<String, Object>>
     */
    @Select("SELECT ${ew.sqlSelect} FROM ${ew.tableName} ${ew.sqlSegment}")
    List<Map<String, Object>> selectMapsPageTN(@Param("page") Pagination page, @Param("ew") MyEntityWrapper<T> myEntityWrapper);

}
