package com.dessert.mail.user.controller;

import com.dessert.common.entity.ums.LoginUser;

import javax.servlet.http.HttpSession;

public class BaseController {
    public LoginUser getSessionUser(HttpSession session){
        return (LoginUser) session.getAttribute("LoginUser");
    }
    public Long getLoginUserId(HttpSession session){
        try {
            LoginUser loginUser = (LoginUser) session.getAttribute("LoginUser");
            return loginUser.getId();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
