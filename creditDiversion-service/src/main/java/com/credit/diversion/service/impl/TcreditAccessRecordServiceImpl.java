package com.credit.diversion.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.credit.diversion.dao.TcreditAccessRecordMapper;
import com.credit.diversion.model.TcreditAccessRecord;
import com.credit.diversion.service.TcreditAccessRecordService;
import com.credit.diversion.util.DateUtils;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @TcreditAccessRecordServiceImpl
 * @ServiceImpl
 * @version : Ver 1.0
 */
@Service
public class TcreditAccessRecordServiceImpl extends ServiceImpl<TcreditAccessRecordMapper, TcreditAccessRecord> implements TcreditAccessRecordService {

    @Autowired
    private TcreditAccessRecordMapper tcreditAccessRecordMapper;

    /**
     * 统计
     * @param id 信贷产品id
     * @return
     */
    public List<Map<String,Object>> selectSumGroup(Long id,Date date,int day){
        Wrapper<TcreditAccessRecord> creditWrapper = new EntityWrapper<TcreditAccessRecord>();
        creditWrapper.setSqlSelect("DATE_FORMAT(create_time,'%m-%d') days," +
                "count(distinct user_id) as creditCount," +
                "sum(if(is_register = 1,1,0)) as creditCountRate");
        creditWrapper.ge("create_time",DateUtils.getDateBeforeZero(date,day));
        creditWrapper.groupBy("days");
        if(id!=null&&!id.equals(0L)){
            creditWrapper.eq("credit_id",id);
        }
        return tcreditAccessRecordMapper.selectMaps(creditWrapper);
    }
}
