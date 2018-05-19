package com.tencent.weili.service.impl;

import com.tencent.weili.dao.ActivityDAO;
import com.tencent.weili.entity.Activity;
import com.tencent.weili.entity.User;
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
        activity.setTimeType(2);
        activity.setDescription("hello");
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
        userService.insertParticipation("001", 9,
                "2018-05-02 07:00:00_2018-05-02 10:00:00;2018-05-04 09:00:00_2018-05-04 13:00:00;2021-10-08 06:00:00_2021-10-08 09:00:00");
        userService.insertParticipation("002", 9,
                "2018-05-03 07:00:00_2018-05-03 09:00:00;2018-05-02 09:00:00_2018-05-02 13:00:00;2021-10-08 07:00:00_2021-10-08 10:00:00");
        userService.insertParticipation("003", 9,
                "2018-05-02 07:00:00_2018-05-02 11:00:00;2018-05-03 09:00:00_2018-05-02 13:00:00;2021-10-08 06:00:00_2021-10-08 11:00:00");
    }

    /*
     * OK
     */
    @Test
    public void selectActivity() throws Exception {
        Activity activity = userService.selectActivity(9);
        System.out.println(activity);
    }

    @Test
    public void selectUser() throws Exception {
        User user = userService.selectUser("001");
        System.out.println(user);
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