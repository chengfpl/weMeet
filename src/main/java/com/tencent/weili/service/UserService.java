package com.tencent.weili.service;

import com.tencent.weili.dto.Result;
import com.tencent.weili.entity.Activity;
import com.tencent.weili.entity.Participation;
import com.tencent.weili.entity.User;

import java.util.List;
import java.util.Map;

public interface UserService {

    User insertUser(String openId, String nickname, String avatar) throws RuntimeException;

    int insertActivity(Activity activity) throws RuntimeException;

    int insertParticipation(String openId, Integer activityId, String time) throws RuntimeException;

    public Map<String, List<User>> getParticipationInDay(Integer activityId);

    public Map<String, List<User>> getParticipationInPartDay(Integer activityId);

    public Map<String, List<User>> getParticipationInHour(Integer activityId);

    public Map<String, List<User>> getParticipationInInterval(Integer activityId);

    List<Activity> selectAllActivity(String openId);

}
