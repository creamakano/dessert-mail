package com.dessert.mail.order.utils;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class OrderUtil {
    public static synchronized String getOrderNum(){
        DateTimeFormatter ofPattern = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
        String localDate = LocalDateTime.now().format(ofPattern);
        //3位随机数
        String randomNumeric = RandomStringUtils.randomNumeric(3);
        String orderNum = localDate + randomNumeric;
        return orderNum;
    }
}
