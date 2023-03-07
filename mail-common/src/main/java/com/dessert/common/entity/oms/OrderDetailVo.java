package com.dessert.common.entity.oms;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.dessert.common.entity.common.BaseVo;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class OrderDetailVo extends BaseVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;

    /**
     * 订单id
     */
    private Long orderId;

    /**
     * 商品id
     */
    private Long productId;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * 商品价格
     */
    private Double productPrice;

    /**
     * 商品图片
     */
    private String productPicture;

    /**
     * 购买数量
     */
    private Integer num;

    /**
     * 购买数量
     */
    private Integer isComment;

    /**
     * 是否删除 0 否 1 删除
     */
    @TableLogic(value = "0",delval = "1")
    private int isDeleted;

    /**
     * detailIdList
     */
    private List<Long> idList;
}