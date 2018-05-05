package com.tencent.weili.service.impl;

import com.tencent.weili.dao.ActivityDAO;
import com.tencent.weili.dao.ParticipationDAO;
import com.tencent.weili.dao.UserDAO;
import com.tencent.weili.dto.Result;
import com.tencent.weili.entity.Activity;
import com.tencent.weili.entity.Participation;
import com.tencent.weili.entity.User;
import com.tencent.weili.service.UserService;
import com.tencent.weili.util.Algorithm;
import com.tencent.weili.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private ActivityDAO activityDAO;

    @Autowired
    private ParticipationDAO participationDAO;

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

    @Transactional
    @Override
    public int insertParticipation(String openId, Integer activityId, String time) throws RuntimeException {
        if (openId == null
                || activityId == null
                || time == null) {
            throw new RuntimeException("活动参与不能有null项");
        }
        if (!Util.checkTime(time)) {
            throw new RuntimeException("时间格式应该为 yyyy-MM-dd HH:mm:ss");
        }
        Participation participation = participationDAO.selectParticipationByOpenIdAndActivityId(openId, activityId);
        int result = 0;
        if (participation == null) {
            //活动参与人数加1
            Activity activity = activityDAO.selectActivityById(activityId);
            int count = activity.getCount() + 1;
            activityDAO.updateActivityCount(activityId, count);
            //插入某个用户的活动参与状况
            participation = new Participation();
            participation.setCreatorId(activity.getCreator());
            participation.setType(activity.getTimeType());
            participation.setTime(time);
            participation.setUserId(openId);
            participation.setActivityId(activityId);
            participation.setFlag(true);
            result = participationDAO.insertParticipation(participation);
        } else {
            result = participationDAO.updateParticipationByOpenIdAndActivityId(openId, activityId, time);
        }
        return result;
    }

    @Override
    public Map<String, List<User>> getParticipationInDay(Integer activityId) {
        List<Participation> list = participationDAO.selectAllParticipationByActivityId(activityId);
        if (list == null)
            return null;

        HashMap<Integer, String> map = new HashMap<>();
        for (Participation participation : list) {
            map.put(participation.getUser().getId(), participation.getTime());
        }
        Map<String, ArrayList<Integer>> res = Algorithm.get_stat_by_one_day(map);
        Map<String, List<User>> result = new HashMap<>();
        for (String key : res.keySet()) {
            List<Integer> userIdList = res.get(key);
            List<User> userList = new ArrayList<>();
            for (Integer id : userIdList) {
                userList.add(userDAO.selectUserById(id));
            }
            result.put(key, userList);
        }
        return result;
    }

    @Override
    public Map<String, List<User>> getParticipationInPartDay(Integer activityId) {
        List<Participation> list = participationDAO.selectAllParticipationByActivityId(activityId);
        if (list == null)
            return null;

        HashMap<Integer, String> map = new HashMap<>();
        for (Participation participation : list) {
            map.put(participation.getUser().getId(), participation.getTime());
        }
        Map<String, ArrayList<Integer>> res = Algorithm.get_stat_by_half_day(map);
        Map<String, List<User>> result = new HashMap<>();
        res.keySet().forEach(key -> {
            List<Integer> userIdList = res.get(key);
            List<User> userList = new ArrayList<>();
            for (Integer id : userIdList) {
                userList.add(userDAO.selectUserById(id));
            }
            result.put(key, userList);
        });
        return result;
    }

    @Override
    public Map<String, List<User>> getParticipationInHour(Integer activityId) {
        List<Participation> list = participationDAO.selectAllParticipationByActivityId(activityId);
        if (list == null)
            return null;

        HashMap<Integer, String> map = new HashMap<>();
        for (Participation participation : list) {
            map.put(participation.getUser().getId(), participation.getTime());
        }
        Map<String, ArrayList<Integer>> res = Algorithm.get_stat_by_one_hour(map);
        Map<String, List<User>> result = new HashMap<>();
        for (String key : res.keySet()) {
            List<Integer> userIdList = res.get(key);
            List<User> userList = new ArrayList<>();
            for (Integer id : userIdList) {
                userList.add(userDAO.selectUserById(id));
            }
            result.put(Util.transform(key), userList);
        }
        return result;
    }

    @Override
    public Map<String, List<User>> getParticipationInInterval(Integer activityId) {
        List<Participation> list = participationDAO.selectAllParticipationByActivityId(activityId);
        if (list == null)
            return null;

        int type = activityDAO.selectActivityById(activityId).getTimeType();
        HashMap<Integer, String> map = new HashMap<>();
        for (Participation participation : list) {
            map.put(participation.getUser().getId(), participation.getTime());
        }
        Map<String, ArrayList<Integer>> res = Algorithm.get_stat_by_interval(map, type);
        Map<String, List<User>> result = new HashMap<>();
        res.keySet().forEach(key -> {
            List<Integer> userIdList = res.get(key);
            List<User> userList = new ArrayList<>();
            for (Integer id : userIdList) {
                userList.add(userDAO.selectUserById(id));
            }
            result.put(key, userList);
        });
        return result;
    }

    @Override
    public List<Activity> selectAllActivity(String openId) {
        List<Activity> activities = activityDAO.selectAllActivitiesByUserId(openId);
        return activities;
    }

}
