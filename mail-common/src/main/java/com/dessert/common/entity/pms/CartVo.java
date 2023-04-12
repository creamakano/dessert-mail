package com.dessert.common.entity.pms;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.dessert.common.entity.common.BaseVo;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class CartVo extends BaseVo {

    private Long id;

    private Long userId;

    private Long productId;

    private Integer num;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    // @TableField(fill = FieldFill.INSERT)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LocalDateTime createTime;

    /** 创建者 */
    // @TableField(fill = FieldFill.INSERT)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String createUser;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    // @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LocalDateTime updateTime;

    /** 更新者 */
    // @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String updateUser;
    @TableLogic(value = "0",delval = "1")
    private Integer isDeleted;

    private String productName;

    private Double price;

    private Double discount;
    private String picture;

    private List<Long> ids;

    private Integer status;

    private Integer storage;
}
