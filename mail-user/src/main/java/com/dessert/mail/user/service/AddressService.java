package com.dessert.mail.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dessert.common.entity.common.Result;
import com.dessert.common.entity.ums.AddressInfo;
import com.dessert.common.entity.ums.AddressInfoVo;
import com.dessert.common.entity.ums.User;

public interface AddressService extends IService<AddressInfo> {
    Result getPage(AddressInfoVo vo);
}
