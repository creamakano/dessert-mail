package com.dessert.mail.order.controller;

import com.dessert.common.entity.ums.LoginUser;

import javax.servlet.http.HttpSession;

public class BaseController {
    public LoginUser getSessionUser(HttpSession session){
        return (LoginUser) session.getAttribute("LoginUser");
    }
    public Long getLoginUserId(HttpSession session){
        LoginUser loginUser = (LoginUser) session.getAttribute("LoginUser");
        return loginUser.getId();
    }
}
