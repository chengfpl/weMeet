package com.tencent.weili.dao;

import com.tencent.weili.entity.User;

import java.util.List;

public interface UserDAO {

    int insertUser(User user);

    int updateUser(User user);

    User selectUserByOpenId(String openId);

    User selectUserById(Integer id);

    List<User> selectAllUserByActivityId(Integer activityId);

}
