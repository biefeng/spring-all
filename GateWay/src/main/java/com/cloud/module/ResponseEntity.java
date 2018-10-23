package com.cloud.module;

/**
 * @Author : BieFeNg
 * @DATE : 2018/9/6 23:00
 */
public class ResponseEntity {

    private String message;
    private boolean success;
    private Object data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
