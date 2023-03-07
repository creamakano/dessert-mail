package com.dessert.mail.product.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dessert.common.entity.common.Result;
import com.dessert.common.entity.pms.Cart;
import com.dessert.common.entity.pms.CartVo;
import com.dessert.common.entity.pms.Comment;
import com.dessert.common.entity.pms.CommentVo;
import com.dessert.mail.product.feign.OrderFeignClient;
import com.dessert.mail.product.mapper.CartMapper;
import com.dessert.mail.product.mapper.CommentMapper;
import com.dessert.mail.product.service.CartService;
import com.dessert.mail.product.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {
    @Autowired
    private OrderFeignClient orderFeignClient;

    @Override
    public Result insert(Comment comment) {
        if(ObjectUtils.isNull(comment.getContent(),comment.getOrderDetailId(),comment.getProductId())){
            return Result.parameterError();
        }
        comment.setDate(new Date());
        this.save(comment);
        orderFeignClient.updateCommentStatus(comment.getOrderDetailId());
        return Result.success();
    }

    @Override
    public Result getPage(CommentVo vo) {
        if (vo.getProductId() == null){
            return Result.parameterError();
        }
        Page<Comment> page = new Page<>(vo.getPageNo(), vo.getPageSize());
        IPage<Comment> iPage = baseMapper.getPage(page, vo);
        return Result.success(iPage);
    }
}
