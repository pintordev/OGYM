package com.ogym.project.test;


import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@AllArgsConstructor
public class RsData<T> {
    private String resultCode;
    private String msg;
    private T data;
}
