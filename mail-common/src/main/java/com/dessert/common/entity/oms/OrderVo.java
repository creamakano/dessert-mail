package com.dessert.common.entity.oms;

import com.baomidou.mybatisplus.annotation.*;
import com.dessert.common.entity.common.BaseVo;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@TableName("oms_order")
@Data
public class OrderVo extends BaseVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;

    /**
     * 编号
     */
    private String orderNum;

    /**
     * 商品描述
     */
    private String description;

    /**
     * user_id
     */
    private Long userId;

    /**
     * 消费金额
     */
    private Double total;

    /**
     * 0 未支付 1 已支付
     */
    private Integer status;

    /**
     * 下单时间
     */
    private Date date;

    /**
     * 收货人
     */
    private String name;

    /**
     * 收货号码
     */
    private String phone;

    /**
     * 收货地址
     */
    private String address;

    /**
     * 1 已评价 0 未评价 2 部分评价
     */
    private Integer isComment;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date createTime;

    /**
     * 更新人
     */
    private String updateUser;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date updateTime;

    /**
     * 是否删除 0 否 1 删除
     */
    @TableLogic(value = "0",delval = "1")
    private int isDeleted;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;
}