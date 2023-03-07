package com.dessert.mail.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dessert.common.entity.common.Result;
import com.dessert.common.entity.ums.User;
import com.dessert.common.entity.ums.UserVo;

public interface UserService extends IService<User> {
    Result updatePassword(User user);

    Result getPage(UserVo vo);
}
