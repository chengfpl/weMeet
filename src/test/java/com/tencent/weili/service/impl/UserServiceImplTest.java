package com.tencent.weili.service.impl;

import com.tencent.weili.entity.Activity;
import com.tencent.weili.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceImplTest {

    @Autowired
    private UserService userService;

    /*
     * OK
     */
    @Test
    public void insertUser() throws Exception {
        userService.insertUser("001", "chen", "002");
        userService.insertUser("004", "hello", "tent");
    }

    /*
     * OK
     */
    @Test
    public void insertActivity() throws Exception {
        Activity activity = new Activity();
        activity.setTimeType(1);
        activity.setDescription("world");
        activity.setLocation("bao fu si");
        activity.setStartTime(new Date());
        activity.setEndTime(new Date());
        activity.setDeadline(new Date());
        activity.setName("lalala");
        activity.setCreator("001");
        activity.setCount(0);
        int activityId = userService.insertActivity(activity);
        System.out.println(activityId);
    }

    /*
     * OK
     */
    @Test
    public void insertParticipation() throws Exception {
        userService.insertParticipation("001", 9, "4-5");
    }

    @Test
    public void getParticipationInDay() throws Exception {

    }

    @Test
    public void getParticipationInPartDay() throws Exception {

    }

    @Test
    public void getParticipationInHour() throws Exception {

    }

    @Test
    public void getParticipationInInterval() throws Exception {

    }

}