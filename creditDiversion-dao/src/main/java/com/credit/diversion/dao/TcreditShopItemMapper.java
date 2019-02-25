package com.credit.diversion.dao;

import com.baomidou.mybatisplus.plugins.Page;
import com.credit.diversion.model.TcreditShopItem;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @TcreditShopItemMapper
 * @贷超甲方产品信息Mapper
 * @version : Ver 1.0
 */
@Repository
public interface TcreditShopItemMapper extends BaseMapper<TcreditShopItem>{

    /**
     * 关联查询
     * @param page
     * @param tcreditShopItem
     * @return
     */
    List<TcreditShopItem> selectListAll(@Param("page") Page<TcreditShopItem> page,@Param("c") TcreditShopItem tcreditShopItem,@Param("categoryIds") String [] categoryIds);

}
