package com.dessert.mail.user.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.dessert.common.entity.common.Result;
import com.dessert.common.entity.ums.User;
import com.dessert.common.entity.ums.UserVo;
import com.dessert.mail.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("user")
public class UserController extends BaseController{

    @Autowired
    private UserService userService;
    @PutMapping("/update")
    public Result update(@RequestBody User user){
        User originalUser = userService.getById(user.getId());
        userService.updateById(user);
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getAuth,2);
        List<User> list = userService.list(wrapper);
        if(ObjectUtils.isEmpty(list)){
            userService.updateById(originalUser);
            return Result.error("必须存在一位管理员用户");
        }
        return Result.success();
    }

    @PutMapping("/updatePassword")
    public Result updatePassword(@RequestBody User user, HttpSession session){
        user.setId(this.getLoginUserId(session));
        return userService.updatePassword(user);
    }

    @GetMapping("/page")
    public Result page(UserVo vo){
        return userService.getPage(vo);
    }

    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable("id")Long id,HttpSession session){
        Long userId = getLoginUserId(session);
        if(userId.equals(id)){
            return Result.error("不能删除自己");
        }
        userService.removeById(id);
        return Result.success();
    }
}
