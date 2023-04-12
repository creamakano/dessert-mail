package com.dessert.common.entity.pms;

import lombok.Data;
import java.util.List;

@Data
public class SettlementVo {

    private List<CartVo> cartList;

    private Double totalPrice;
}
