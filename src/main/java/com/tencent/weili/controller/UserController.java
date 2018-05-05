package com.tencent.weili.controller;

import com.tencent.weili.dto.Result;
import com.tencent.weili.entity.Activity;
import com.tencent.weili.entity.URLInfo;
import com.tencent.weili.entity.User;
import com.tencent.weili.service.UserService;
import com.tencent.weili.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private URLInfo urlInfo;

    @Autowired
    private UserService userService;

    /*
     * 接受用户res.code返回openId
     */

    @GetMapping(value = "/login")
    public String login(@RequestParam(required = true, value = "code") String code) {
        urlInfo.setJsCode(code);
        String url = urlInfo.toString();
        return Util.getUserWXInfo(url);
    }

    /*
     * 接受用户信息并添加到数据库当中
     */
    @PostMapping(value = "/add")
    public Result<User> add(@RequestParam(required = true, value = "openId") String openId,
                    @RequestParam(required = true, value = "nickname") String nickname,
                    @RequestParam(required = true, value = "avatar") String avatar) {
        User user = userService.insertUser(openId, nickname, avatar);
        Result<User> result = new Result<User>(true, user);
        return result;
    }

    /*
     * 用户创建活动并加入到数据库当中
     */
    @PostMapping(value = "/create/activity")
    public  Result<Integer> insertActivity(HttpServletRequest request) throws Exception {
        Activity activity = new Activity();
        activity.setName(request.getParameter("name"));
        activity.setDescription(request.getParameter("description"));
        activity.setLocation(request.getParameter("location"));
        activity.setCreator(request.getParameter("openId"));
        activity.setTimeType(Integer.parseInt(request.getParameter("timeType")));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        activity.setStartTime(simpleDateFormat.parse(request.getParameter("startTime")));
        activity.setEndTime(simpleDateFormat.parse(request.getParameter("endTime")));
        activity.setDeadline(simpleDateFormat.parse(request.getParameter("deadline")));
        int activityId = userService.insertActivity(activity);
        Result<Integer> result = new Result<Integer>(true, activityId);
        return result;
    }

    @GetMapping(value = "/activity")
    public Result<List<Activity>> selectAllActivity(@RequestParam(required = true, value = "openId") String openId) {
        List<Activity> list = userService.selectAllActivity(openId);
        Result<List<Activity>> result = new Result<List<Activity>>(true, list);
        return result;
    }

    @PostMapping(value = "/create/participation")
    public Result<Integer> insertParticipation(
            @RequestParam(required = true, value = "openId") String openId,
            @RequestParam(required = true, value = "acitvityId") Integer activityId,
            @RequestParam(required = true, value = "time") String time) {
        int tag = userService.insertParticipation(openId, activityId, time);
        return new Result<Integer>(true, tag);
    }

    @GetMapping(value = "/participation/day")
    public Result<Map<String, List<User>>> getParticipationInDay(@RequestParam(required = true, value = "acitvityId") Integer activityId) {
        Map<String, List<User>> map = userService.getParticipationInDay(activityId);
        return new Result<Map<String, List<User>>>(true, map);
    }

    @GetMapping(value = "/participation/partofday")
    public Result<Map<String, List<User>>> getParticipationInPartDay() {
        return null;
    }

    @GetMapping(value = "/participation/hour")
    public Result<Map<String, List<User>>> getParticipationInHour() {
        return null;
    }

    @GetMapping(value = "/participation/interval")
    public Result<Map<String, List<User>>> getParticipationInInterval() {
        return null;
    }

}
