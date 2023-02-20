package com.dessert.mail.product.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.dessert.common.entity.pms.ProductType;
import com.dessert.mail.product.mapper.ProductTypeMapper;
import com.dessert.mail.product.service.ProductTypeService;
import org.springframework.stereotype.Service;

@Service
public class ProductTypeServiceImpl extends ServiceImpl<ProductTypeMapper, ProductType> implements ProductTypeService {



}
