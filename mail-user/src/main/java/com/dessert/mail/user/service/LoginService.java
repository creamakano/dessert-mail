package com.dessert.mail.user.service;



import com.baomidou.mybatisplus.extension.service.IService;
import com.dessert.common.entity.common.Result;
import com.dessert.common.entity.ums.User;

import javax.servlet.http.HttpSession;


public interface LoginService extends IService<User> {

    Result userLogin(HttpSession session, User user);

}
