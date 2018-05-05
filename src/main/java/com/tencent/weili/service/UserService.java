package com.tencent.weili.service;

import com.tencent.weili.entity.Activity;
import com.tencent.weili.entity.User;

public interface UserService {

    User insertUser(String openId, String nickname, String avatar) throws RuntimeException;

    int insertActivity(Activity activity) throws RuntimeException;

}
