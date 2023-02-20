package com.dessert.mail.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dessert.common.entity.common.Result;
import com.dessert.common.entity.ums.AddressInfo;
import com.dessert.common.entity.ums.AddressInfoVo;
import com.dessert.common.entity.ums.Collection;
import com.dessert.common.entity.ums.User;
import com.dessert.mail.user.mapper.AddressMapper;
import com.dessert.mail.user.mapper.CollectionMapper;
import com.dessert.mail.user.mapper.UserMapper;
import com.dessert.mail.user.service.AddressService;
import com.dessert.mail.user.service.CollectionService;
import com.dessert.mail.user.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class AddressServiceImpl extends ServiceImpl<CollectionMapper, Collection> implements CollectionService {

}
