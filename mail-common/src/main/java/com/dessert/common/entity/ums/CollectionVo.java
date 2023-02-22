package com.dessert.common.entity.ums;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.dessert.common.entity.common.BaseVo;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class CollectionVo extends BaseVo implements Serializable {

    private static final long serialVersionUID = 1L;

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
    @TableLogic(value = "0",delval = "1")
    private int isDeleted;

    public CollectionVo() {}
}