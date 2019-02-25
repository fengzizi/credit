package com.credit.diversion.util.mybatisplus;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.toolkit.StringUtils;
import lombok.Data;

/**
 * Created by fengzi on 2017/12/27.
 */
@Data
public class MyEntityWrapper<T> extends EntityWrapper<T> {

    protected String tableName;

    protected String sqlInsert;

    protected String sqlUpdate;

    public MyEntityWrapper(){
        super.isWhere(true);
    }

    public MyEntityWrapper(String tableName){
        this.tableName=tableName;
        super.isWhere(true);
    }
    public Wrapper<T> tableName(String tableName){
        if (StringUtils.isNotEmpty(tableName)) {
            this.tableName=tableName;
        }
        return this;
    }

    public Wrapper<T> setSqlInsert(String sqlInsert){
        if (StringUtils.isNotEmpty(sqlInsert)) {
            this.sqlInsert=sqlInsert;
        }
        return this;
    }

    public Wrapper<T> setSqlInsert(StringBuilder sqlInsert){
        if (StringUtils.isNotEmpty(sqlInsert)) {
            this.sqlInsert=sqlInsert.toString();
        }
        return this;
    }

    public Wrapper<T> setSqlInsert(StringBuffer sqlInsert){
        if (StringUtils.isNotEmpty(sqlInsert)) {
            this.sqlInsert=sqlInsert.toString();
        }
        return this;
    }

    public Wrapper<T> setSqlUpdate(String sqlUpdate){
        if (StringUtils.isNotEmpty(sqlUpdate)) {
            this.sqlUpdate=sqlUpdate;
        }
        return this;
    }

    public Wrapper<T> setSqlUpdate(StringBuilder sqlUpdate){
        if (StringUtils.isNotEmpty(sqlUpdate)) {
            this.sqlUpdate=sqlUpdate.toString();
        }
        return this;
    }

    public Wrapper<T> setSqlUpdate(StringBuffer sqlUpdate){
        if (StringUtils.isNotEmpty(sqlUpdate)) {
            this.sqlUpdate=sqlUpdate.toString();
        }
        return this;
    }
}
