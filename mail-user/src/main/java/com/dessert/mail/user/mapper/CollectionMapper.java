package com.dessert.mail.user.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dessert.common.entity.pms.Product;
import com.dessert.common.entity.ums.Collection;
import com.dessert.common.entity.ums.CollectionVo;
import org.apache.ibatis.annotations.Param;

public interface CollectionMapper extends BaseMapper<Collection> {
    IPage<Product> getPage(@Param("page") Page<Product> page, @Param("vo") CollectionVo vo);
}
