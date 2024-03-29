package com.dessert.mail.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dessert.common.entity.common.Result;
import com.dessert.common.entity.pms.ProductType;
import com.dessert.common.entity.pms.ProductTypeVo;

import java.util.Map;


public interface ProductTypeService extends IService<ProductType> {
    Result getPage(ProductTypeVo vo);

    Result updateType(ProductType type);

    Result deleteById(Long id);

    Result insert(ProductType type);

    Result<Map<Long, String>> getProductTypeDict();
}
