package com.dessert.common.entity.ums;


import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@TableName("ums_user")
public class User implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String name;

    private String mail;

    private String phone;

    private String password;
    @TableLogic(value = "0",delval = "1")
    private Integer isDeleted;

    @TableField(exist = false)
    private String newPassword;
}
