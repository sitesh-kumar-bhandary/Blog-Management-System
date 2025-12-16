package com.siteshkumar.bms.Error;

import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import lombok.Data;

@Data
public class ApiError {
    
    private LocalDateTime timeStamp;
    private String error;
    private HttpStatus statusCode;

    public ApiError(String error, HttpStatus statusCode){
        timeStamp = LocalDateTime.now();
        this.error = error;
        this.statusCode = statusCode;
    }
}
