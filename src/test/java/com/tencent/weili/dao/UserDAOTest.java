package com.tencent.weili.dao;

import com.tencent.weili.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserDAOTest {

    @Autowired
    private UserDAO userDAO;

    /*
     * OK
     */
    @Test
    public void insertUser() throws Exception {
        User user = new User();
        user.setOpenId("001klj4l");
        user.setNickname("wukldjf");
        user.setAvatar("www.tencent.com");
        int result = userDAO.insertUser(user);
        System.out.println(user.getId());
        assertEquals(1, result);
    }

    /*
     * OK
     */
    @Test
    public void selectUserByOpenId() throws Exception {
        User user = userDAO.selectUserByOpenId("001");
        System.out.println(user);
    }

    /*
     * OK
     */
    @Test
    public void selectAllUserByActivityId() throws Exception {
        List<User> list = userDAO.selectAllUserByActivityId(3);
        for (User user : list) {
            System.out.println(user);
            System.out.println(user.getActivityList());
        }
    }

    /*
     * OK
     */
    @Test
    public void updateUser() throws Exception {
        User user = new User();
        user.setOpenId("001");
        user.setNickname("chen");
        user.setAvatar("www.fff");
        int result = userDAO.updateUser(user);
        assertEquals(1, result);
    }

    /*
     * OK
     */
    @Test
    public void selectUserById() throws Exception {
        User user = userDAO.selectUserById(5);
        System.out.println(user);
    }

}