package com.tencent.weili.dao;

import com.tencent.weili.entity.Activity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ActivityDAOTest {

    @Autowired
    private ActivityDAO activityDAO;

    @Test
    public void insertActivity() throws Exception {
        Activity activity = new Activity();
        activity.setCount(0);
        activity.setCreator("chen");
        activity.setName("lang_ren_sha");
        activity.setStartTime(new Date());
        activity.setEndTime(new Date());
        activity.setDeadline(new Date());
        activity.setLocation("zhongguancun");
        activity.setDescription("feng_pei_lang_ren_sha");
        activity.setTimeType(1);
        int result = activityDAO.insertActivity(activity);
        assertEquals(1, result);
    }

}