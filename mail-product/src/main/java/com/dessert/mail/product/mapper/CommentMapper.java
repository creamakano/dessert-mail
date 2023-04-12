package com.dessert.mail.product.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dessert.common.entity.pms.Comment;
import com.dessert.common.entity.pms.CommentVo;
import org.apache.ibatis.annotations.Param;

public interface CommentMapper extends BaseMapper<Comment> {
    IPage<Comment> getPage(@Param("page") Page<Comment> page, @Param("vo") CommentVo vo);

    IPage<Comment> getAllPage(@Param("page") Page<Comment> page, @Param("vo") CommentVo vo);
}
