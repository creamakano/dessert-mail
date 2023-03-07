package com.dessert.common.entity.ums;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("sys_dict")
@Data
public class SysDict {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String name;
    private String label;
    private int value;
    @TableLogic(value = "0",delval = "1")
    private Integer isDeleted;
}
