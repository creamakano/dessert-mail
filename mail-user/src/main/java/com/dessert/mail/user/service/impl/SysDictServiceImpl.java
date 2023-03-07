package com.dessert.mail.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dessert.common.entity.common.Result;
import com.dessert.common.entity.ums.SysDict;
import com.dessert.common.entity.ums.User;
import com.dessert.mail.user.mapper.SysDictMapper;
import com.dessert.mail.user.mapper.UserMapper;
import com.dessert.mail.user.service.SysDictService;
import com.dessert.mail.user.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class SysDictServiceImpl extends ServiceImpl<SysDictMapper, SysDict> implements SysDictService {

}
