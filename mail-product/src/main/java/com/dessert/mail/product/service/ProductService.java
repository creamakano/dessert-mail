package com.dessert.mail.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dessert.common.entity.common.Result;
import com.dessert.common.entity.pms.Product;
import com.dessert.common.entity.pms.ProductVo;


public interface ProductService extends IService<Product> {
    Result getPage(ProductVo vo);

    Result allPage(ProductVo vo);

    Result updateProduct(Product product);

    Result insert(Product product);

    Result delete(Long id);
}
