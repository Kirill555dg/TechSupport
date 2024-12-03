package com.mirkin_k_l.coursework.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyError {
    private Integer status;
    private String error;
}
