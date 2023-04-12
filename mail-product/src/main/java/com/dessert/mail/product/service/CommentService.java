package com.dessert.mail.product.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.dessert.common.entity.common.Result;
import com.dessert.common.entity.pms.Cart;
import com.dessert.common.entity.pms.CartVo;
import com.dessert.common.entity.pms.Comment;
import com.dessert.common.entity.pms.CommentVo;

import java.util.List;

public interface CommentService extends IService<Comment> {
    Result insert(Comment comment);

    Result getPage(CommentVo vo);

    Result getAllPage(CommentVo vo);
}
