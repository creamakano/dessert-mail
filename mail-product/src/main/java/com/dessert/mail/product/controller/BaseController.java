package com.dessert.mail.product.controller;

import com.dessert.common.entity.ums.LoginUser;
import com.dessert.common.entity.ums.User;

import javax.servlet.http.HttpSession;

public class BaseController {
    public LoginUser getSessionUser(HttpSession session){
        return (LoginUser) session.getAttribute("LoginUser");
    }
    public Long getLoginUserId(HttpSession session){
        LoginUser loginUser = (LoginUser) session.getAttribute("LoginUser");
        if(loginUser==null){
            return null;
        }
        return loginUser.getId();
    }
}
