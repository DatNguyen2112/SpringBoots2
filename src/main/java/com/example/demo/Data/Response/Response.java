package com.example.demo.Data.Response;

import org.springframework.http.HttpStatus;

public class Response<T> {
    public HttpStatus code;
    public String message;
    public boolean isSuccess;
    public T data;

    public Response(HttpStatus code, String message, boolean isSuccess, T data) {
        this.code = code;
        this.message = message;
        this.isSuccess = isSuccess;
        this.data = data;
    }

    public HttpStatus getCode() {
        return code;
    }

    public void setCode(HttpStatus code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
