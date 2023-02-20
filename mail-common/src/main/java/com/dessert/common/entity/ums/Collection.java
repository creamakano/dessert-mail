package com.dessert.common.entity.ums;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Collection implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    /**
     * id
     */
    private Long id;

    /**
     * user_id
     */
    private Long userId;

    /**
     * product_id
     */
    private Long productId;

    /**
     * 收藏时间
     */
    private Date collectionTime;

    /**
     * is_deleted
     */
    private int isDeleted;

    public Collection() {}
}