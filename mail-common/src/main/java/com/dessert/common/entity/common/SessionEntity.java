package com.dessert.common.entity.common;

import lombok.Data;

@Data
public class SessionEntity {
    private Long id;

    private String name;

    private String phone;

    private String mail;

    private String creationTime;
}
