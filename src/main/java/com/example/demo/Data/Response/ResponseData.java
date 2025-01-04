package com.example.demo.Data.Response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class ResponseData {
    public HttpStatus Code = HttpStatus.OK;
    public String Message = "Success";
    public boolean IsSuccess;

    public ResponseData(HttpStatus Code, String Message, boolean IsSuccess) {
        this.Code = Code;
        this.Message = Message;
        this.IsSuccess = IsSuccess;
    }

    public ResponseData(String message) {
        this.Message = message;
    }

    public ResponseData(HttpStatus code, String message) {
        this.Code = code;
        this.Message = message;
    }

    public HttpStatus getCode() {
        return Code;
    }

    public void setCode(HttpStatus code) {
        Code = code;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public boolean isSuccess() {
        return IsSuccess;
    }

    public void setIsSuccess(boolean isSuccess) {
        this.IsSuccess = isSuccess;
    }
}
