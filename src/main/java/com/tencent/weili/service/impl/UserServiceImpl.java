package com.tencent.weili.service.impl;

import com.tencent.weili.dao.ActivityDAO;
import com.tencent.weili.dao.UserDAO;
import com.tencent.weili.entity.Activity;
import com.tencent.weili.entity.User;
import com.tencent.weili.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private ActivityDAO activityDAO;

    @Override
    public User insertUser(String openId, String nickname, String avatar) throws RuntimeException {
        if (openId == null || nickname == null || avatar == null) {
            throw new RuntimeException("用户信息不能有null项");
        }
        User user = new User();
        user.setOpenId(openId);
        user.setNickname(nickname);
        user.setAvatar(avatar);
        if (userDAO.selectUserByOpenId(openId) == null) {
            userDAO.insertUser(user);
        } else {
            userDAO.updateUser(user);
        }
        return user;
    }

    @Override
    public int insertActivity(Activity activity) throws RuntimeException {
        if (activity.getName() == null
                || activity.getDescription() == null
                || activity.getCreator() == null
                || activity.getLocation() == null
                || activity.getTimeType() == null
                || activity.getStartTime() == null
                || activity.getEndTime() == null
                || activity.getDeadline() == null) {
            throw new RuntimeException("活动信息中存在null项");
        }
        activityDAO.insertActivity(activity);
        return activity.getId();
    }

}
