package com.credit.diversion.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.credit.diversion.model.TcreditShopItem;

/**
 * @TcreditShopItemService
 * @贷超甲方产品信息Service
 * @version : Ver 1.0
 */
public interface TcreditShopItemService extends IService<TcreditShopItem>{

    /**
     * 列表查询
     * @param page
     * @param tcreditShopItem
     * @return
     */
    Page<TcreditShopItem> selectListAll(Page<TcreditShopItem> page, TcreditShopItem tcreditShopItem, String [] categoryIds);
}
