package com.dessert.common.entity.ums;

import lombok.Data;

import java.io.Serializable;

@Data
public class LoginUser implements Serializable {
    private Long id;

    private String name;

    private String mail;

    private String phone;

}
