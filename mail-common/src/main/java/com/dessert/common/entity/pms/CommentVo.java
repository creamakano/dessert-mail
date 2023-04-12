package com.dessert.common.entity.pms;

import com.baomidou.mybatisplus.annotation.*;
import com.dessert.common.entity.common.BaseVo;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
public class CommentVo extends BaseVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;

    /**
     * 商品id
     */
    private Long productId;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 订单详情id
     */
    private Long orderDetailId;

    /**
     * 描述
     */
    private String content;

    /**
     * 评价时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date date;

    /**
     * 是否删除
     */
    @TableLogic(value = "0",delval = "1")
    private int isDeleted;

    private String userName;

    private String productName;

}
