package com.maybank.apiservice.util;

import com.maybank.apiservice.constant.AppStatus;
import com.maybank.apiservice.dto.ResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseUtil {
    public static <T> ResponseEntity<ResponseDTO<T>> success(T object) {
        return new ResponseEntity<>(new ResponseDTO<>(AppStatus.SUCCESS.getCode(), AppStatus.SUCCESS.getMsg(), object), HttpStatus.OK);
    }

    public static <T> ResponseEntity<ResponseDTO<T>> success() {
        return new ResponseEntity<>(new ResponseDTO<>(AppStatus.SUCCESS.getCode(), AppStatus.SUCCESS.getMsg(), null), HttpStatus.OK);
    }
}
