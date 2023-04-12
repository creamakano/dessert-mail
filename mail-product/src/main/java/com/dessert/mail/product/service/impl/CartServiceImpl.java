package com.dessert.mail.product.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.dessert.common.entity.common.Result;
import com.dessert.common.entity.pms.Cart;
import com.dessert.common.entity.pms.CartVo;
import com.dessert.common.entity.pms.Product;
import com.dessert.common.entity.pms.SettlementVo;
import com.dessert.mail.product.mapper.CartMapper;
import com.dessert.mail.product.service.CartService;
import com.dessert.mail.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl extends ServiceImpl<CartMapper, Cart> implements CartService {

    @Autowired
    private ProductService productService;

    @Override
    public Result getList(Long userId) {
        List<CartVo> list = baseMapper.getList(userId);
        return Result.success(list);
    }

    @Override
    public Result<List<CartVo>> getListByIds(List<Long> ids) {
        List<CartVo> list = baseMapper.getListByIds(ids);
        return Result.success(list);
    }

    @Override
    public Result getPage(CartVo vo) {
        Page<CartVo> page = new Page<>(vo.getPageNo(),vo.getPageSize());
        IPage<CartVo> iPage = baseMapper.getPage(page,vo);
        return Result.success(iPage);
    }

    @Override
    public Result settlement(List<Long> ids) {
        List<CartVo> cartList = baseMapper.getListByIds(ids);
        Double total = cartList.stream().reduce(0.0, (x, y) -> x + (y.getPrice() * y.getDiscount() * y.getNum()), Double::sum);
        SettlementVo vo = new SettlementVo();
        vo.setCartList(cartList);
        vo.setTotalPrice(total);
        return Result.success(vo);
    }

    @Override
    public Result<List<CartVo>> cartSubmitByIds(List<Long> ids) {
        List<CartVo> list = baseMapper.getListByIds(ids);
        //更新库存
        List<Product> productList = new ArrayList<>();
        for(CartVo vo : list){
            Product product = new Product();
            product.setId(vo.getProductId());
            product.setStorage(vo.getStorage()-vo.getNum());
            productList.add(product);
        }
        productService.updateBatchById(productList);
        this.removeByIds(ids);
        return Result.success(list);
    }

    @Override
    public Result checkStorage(CartVo vo) {
        List<CartVo> cartList = baseMapper.checkStorage(vo.getIds());
        if(ObjectUtils.isNotEmpty(cartList)){
            String msg = "以下商品库存不足，请重新选择：<br/>";
            msg += cartList.stream().map(CartVo::getProductName).collect(Collectors.joining(" , " ));
            return Result.error(msg);
        }else {
            return Result.success();
        }
    }
}
