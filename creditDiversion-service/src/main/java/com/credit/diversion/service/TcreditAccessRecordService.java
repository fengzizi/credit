package com.credit.diversion.service;

import com.baomidou.mybatisplus.service.IService;
import com.credit.diversion.model.TcreditAccessRecord;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @TcreditAccessRecordService
 * @Service
 * @version : Ver 1.0
 */
public interface TcreditAccessRecordService extends IService<TcreditAccessRecord>{

    /**
     * 统计
     * @param id
     * @return
     */
    List<Map<String,Object>> selectSumGroup(Long id, Date date, int day);
}
