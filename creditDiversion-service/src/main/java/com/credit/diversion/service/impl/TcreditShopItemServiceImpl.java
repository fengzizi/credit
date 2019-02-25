package com.credit.diversion.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.credit.diversion.dao.TcreditShopItemMapper;
import com.credit.diversion.model.TcreditShopItem;
import com.credit.diversion.service.TcreditShopItemService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @TcreditShopItemServiceImpl
 * @贷超甲方产品信息ServiceImpl
 * @version : Ver 1.0
 */
@Service
public class TcreditShopItemServiceImpl extends ServiceImpl<TcreditShopItemMapper, TcreditShopItem> implements TcreditShopItemService {

    @Autowired
    private TcreditShopItemMapper tcreditShopItemMapper;

    /**
     * 列表查询
     * @param page
     * @param tcreditShopItem
     * @return
     */
    public Page<TcreditShopItem> selectListAll(Page<TcreditShopItem> page,TcreditShopItem tcreditShopItem,String [] categoryIds){
        return page.setRecords(tcreditShopItemMapper.selectListAll(page, tcreditShopItem,categoryIds));
    }
}
