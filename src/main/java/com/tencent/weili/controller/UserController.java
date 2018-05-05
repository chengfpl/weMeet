package com.tencent.weili.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tencent.weili.dto.Result;
import com.tencent.weili.entity.Activity;
import com.tencent.weili.entity.Participation;
import com.tencent.weili.entity.URLInfo;
import com.tencent.weili.entity.User;
import com.tencent.weili.service.UserService;
import com.tencent.weili.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
    @GetMapping(value = "/add")
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
    public  Result<Integer> insertActivity(HttpServletRequest request) {
        Activity activity = new Activity();
        //TODO get request info to init activity

        int activityId = userService.insertActivity(activity);
        Result<Integer> result = new Result<Integer>(true, activityId);
        return result;
    }

    @PostMapping(value = "/create/participation")
    public Result<Map<String, List<User>>> insertParticipation(HttpServletRequest request) {
        Participation participation = new Participation();
        //TODO get request info to init Participation
        return null;
    }

    @PostMapping(value = "/participation/day")
    public Result<Map<String, List<User>>> getParticipationInDay() {
        return null;
    }

    @PostMapping(value = "/participation/partofday")
    public Result<Map<String, List<User>>> getParticipationInPartDay() {
        return null;
    }

    @PostMapping(value = "/participation/hour")
    public Result<Map<String, List<User>>> getParticipationInHour() {
        return null;
    }

}
