package com.credit.diversion.dao;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.credit.diversion.model.Tmessage;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @TmessageMapper
 * @消息Mapper
 * @version : Ver 1.0
 */
@Repository
public interface TmessageMapper extends BaseMapper<Tmessage>{

    /**
     * 查询列表 关联用户登录信息登录号
     * @param page
     * @param tmessage
     * @return
     */
    List<Tmessage>  selectListOfLoginName(@Param("page") Pagination page,@Param("m") Tmessage tmessage);

}
