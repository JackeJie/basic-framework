package com.example.framework.common.mvc;

import org.springframework.core.convert.converter.Converter;

import java.time.LocalDateTime;

/**
 * @author wangjie
 * @version 1.0
 * @ClassName LocalDateTimeFormat
 * @date 2020/12/2918:56
 * @description: TODO
 */
public class LocalDateTimeFormat implements Converter<String,LocalDateTime> {

    @Override
    public LocalDateTime convert(String s) {
        if (s == null){
            return null;
        }




        return null;
    }
}
