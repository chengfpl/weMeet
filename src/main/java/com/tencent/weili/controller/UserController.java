package com.tencent.weili.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.util.ArrayList;
import java.util.HashMap;
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
        activity.setCount(0);
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

    @GetMapping(value = "/select/activity")
    public Result<Activity> selectActivity(@RequestParam(required = true, value = "activityId") Integer activityId) {
        Activity activity = userService.selectActivity(activityId);
        Result<Activity> result = new Result<Activity>(true, activity);
        return result;
    }

    @PostMapping(value = "/create/participation")
    public Result<Integer> insertParticipation(
            @RequestParam(required = true, value = "openId") String openId,
            @RequestParam(required = true, value = "activityId") Integer activityId,
            @RequestParam(required = true, value = "time") String time) {
        int tag = userService.insertParticipation(openId, activityId, time);
        return new Result<Integer>(true, tag);
    }

    /*
     * OK
     */
    @GetMapping(value = "/participation/day")
    public Result<List<Map.Entry<String, List<User>>>> getParticipationInDay(@RequestParam(required = true, value = "activityId") Integer activityId) {
        Map<String, List<User>> map = userService.getParticipationInDay(activityId);
        return new Result<List<Map.Entry<String, List<User>>>>(true, Util.sort(map));
    }

    @GetMapping(value = "/participation/partofday")
    public Result<List<Map.Entry<String, List<User>>>> getParticipationInPartDay(@RequestParam(required = true, value = "activityId") Integer activityId) {
        Map<String, List<User>> map = userService.getParticipationInPartDay(activityId);
        return new Result<List<Map.Entry<String, List<User>>>>(true, Util.sort(map));
    }

    /*
     * OK
     */
    @GetMapping(value = "/participation/hour")
    public Result<List<Map.Entry<String, List<User>>>> getParticipationInHour(@RequestParam(required = true, value = "activityId") Integer activityId) {
        Map<String, List<User>> map = userService.getParticipationInHour(activityId);
        return new Result<List<Map.Entry<String, List<User>>>>(true, Util.sort(map));
    }

    @GetMapping(value = "/participation/interval")
    public Result<List<Map.Entry<String, List<User>>>> getParticipationInInterval(@RequestParam(required = true, value = "activityId") Integer activityId) {
        Map<String, List<User>> map = userService.getParticipationInInterval(activityId);
        return new Result<List<Map.Entry<String, List<User>>>>(true, Util.sort(map));
    }

    /*
     * for temporary test
     */
    @GetMapping(value = "/temporary/participation/day")
    public Result<Map<String, List<User>>> getTemporaryParticipationInDay(@RequestParam(required = true, value = "activityId") Integer activityId) {
        Map<String, List<User>> map = new HashMap<>();
        List<User> list1 = new ArrayList<>();
        System.out.println("debug : -------------->");
        list1.add(userService.selectUser("001"));
        list1.add(userService.selectUser("002"));
        List<User> list2 = new ArrayList<>();
        list2.add(userService.selectUser("002"));
        list2.add(userService.selectUser("003"));
        System.out.println(list1);
        System.out.println(list2);
        String date1 = "2018-05-02 00:00:00_2018-05-02 24:00:00";
        String date2 = "2018-05-02 00:00:00_2018-05-03 24:00:00";
        map.put(date1, list1);
        map.put(date2, list2);
        return new Result<Map<String, List<User>>>(true, map);
    }

    @GetMapping(value = "/temporary/participation/interval")
    public Result<List<Map.Entry<String, List<User>>>> getTemporaryParticipationInInterval(@RequestParam(required = true, value = "activityId") Integer activityId) {

        List<User> list1 = new ArrayList<>();
        list1.add(userService.selectUser("001"));

        List<User> list2 = new ArrayList<>();
        list2.add(userService.selectUser("002"));
        list2.add(userService.selectUser("003"));

        List<User> list3 = new ArrayList<>();
        list3.add(userService.selectUser("001"));
        list3.add(userService.selectUser("002"));
        list3.add(userService.selectUser("003"));

        String date1 = "2018-05-02 09:00:00_2018-05-02 11:00:00";
        String date2 = "2018-05-03 00:00:00_2018-05-03 02:00:00";
        String date3 = "2018-05-04 00:00:00_2018-05-04 02:00:00";

        Map<String, List<User>> map = new HashMap<>();
        map.put(date1, list1);
        map.put(date2, list2);
        map.put(date3, list3);
        return new Result<List<Map.Entry<String, List<User>>>>(true, Util.sort(map));
    }

}
