package com.dessert.mail.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dessert.common.entity.common.Result;
import com.dessert.common.entity.pms.ProductType;
import com.dessert.common.entity.pms.ProductTypeVo;


public interface ProductTypeService extends IService<ProductType> {
    Result getPage(ProductTypeVo vo);

}
