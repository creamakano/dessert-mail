package com.dessert.common.entity.ums;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.dessert.common.entity.common.BaseVo;
import lombok.Data;

import java.io.Serializable;

@Data
public class AddressInfoVo extends BaseVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 收货地址
     */
    private String address;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 收货人
     */
    private String name;

    /**
     * 是否默认
     */
    private int isDefault;

    /**
     * 是否删除
     */
    @TableLogic(value = "0",delval = "1")
    private int isDeleted;

    public AddressInfoVo() {}
}
