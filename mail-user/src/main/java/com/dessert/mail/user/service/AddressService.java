package com.dessert.mail.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dessert.common.entity.common.Result;
import com.dessert.common.entity.ums.AddressInfo;
import com.dessert.common.entity.ums.AddressInfoVo;
import com.dessert.common.entity.ums.User;

public interface AddressService extends IService<AddressInfo> {
    Result getPage(AddressInfoVo vo);

    Result setDefault(AddressInfo addressInfo);

    Result delete(Long id, Long userId);

    Result insert(AddressInfo addressInfo);
}
