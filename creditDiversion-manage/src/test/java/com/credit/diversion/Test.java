package com.credit.diversion;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.credit.diversion.dao.TuserFeedbackMapper;
import com.credit.diversion.model.TuserFeedback;
import com.credit.diversion.model.TuserLoginInfo;
import com.credit.diversion.model.TuserPersonInfo;
import com.credit.diversion.service.TuserLoginInfoService;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;
import java.util.Map;

@RunWith(JUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:/config/applicationContext.xml",
        "classpath*:/config/mybatis-config.xml",
        "classpath*:/config/spring-mvc.xml"})
public class Test {

    @Autowired
    private TuserFeedbackMapper tuserFeedbackMapper;

    @Autowired
    private TuserLoginInfoService tuserLoginInfoService;

    @org.junit.Test
    public void selectList() {
        TuserFeedback feedback = new TuserFeedback();
        feedback.setUid("15104285061889240000");
        List<TuserFeedback> list = tuserFeedbackMapper.selectListOfUser(new Page<TuserFeedback>(0,10), feedback, new TuserPersonInfo());
        System.err.println(JSON.toJSONString(list));
    }

    @org.junit.Test
    public void selectSum(){
        Wrapper<TuserLoginInfo> wrapper = new EntityWrapper<TuserLoginInfo>();
        wrapper.setSqlSelect("count(id) as countAll," +
                "sum(if(to_days(created) = to_days(now()),1,0)) as countNowRegister," +
                "sum(if(to_days(last_login_time) = to_days(now()),1,0)) as countNowLogin");
        List<Map<String, Object>> maps = tuserLoginInfoService.selectMaps(wrapper);

        System.err.println(JSON.toJSONString(maps));
    }
}
