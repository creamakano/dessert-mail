package com.dessert.mail.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dessert.common.entity.common.Result;
import com.dessert.common.entity.ums.Collection;
import com.dessert.common.entity.ums.CollectionVo;

public interface CollectionService extends IService<Collection> {
    Result insert(Collection collection);

    Result deleteByProductId(Long productId, Long loginUserId);

    Result getPage(CollectionVo vo);
}
