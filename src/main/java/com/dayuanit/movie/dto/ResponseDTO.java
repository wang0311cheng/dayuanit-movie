package com.dayuanit.movie.dto;

public class ResponseDTO {

    public static final int success_code = 1000;
    public static final int fail_code = 9999;
    public static final int reLogin_code = 6666;

    private int code = success_code;
    private String message;
    private Object data;

    public ResponseDTO(){

    }

    public ResponseDTO(int code, String message){
        this.code = code;
        this.message = message;
    }

    public ResponseDTO(Object data){
        this.data = data;
    }

    public static ResponseDTO success(){
        return new ResponseDTO();
    }

    public static ResponseDTO success(Object data){
        return new ResponseDTO(data);
    }

    public static ResponseDTO fail(String message){
        return new ResponseDTO(fail_code,message);
    }

    public static ResponseDTO reLogin(int code, String message){
        return new ResponseDTO(reLogin_code,"请登录");
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
