package com.dessert.common.entity.ums;


import com.baomidou.mybatisplus.annotation.*;
import com.dessert.common.entity.common.BaseVo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
public class UserVo extends BaseVo implements Serializable {

    private Long id;

    private String name;

    private String mail;

    private String phone;

    private String password;

    private Integer auth;

    @TableLogic(value = "0",delval = "1")
    private Integer isDeleted;

}
