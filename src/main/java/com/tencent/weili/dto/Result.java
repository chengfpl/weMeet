package com.tencent.weili.dto;

/*
 * 这个类封装返回给前端的数据，以json格式
 */
public class Result<T> {

    private boolean success;

    private T data;

    private String errMsg;

    public Result(boolean success, T data) {
        this.success = success;
        this.data = data;
    }

    public Result(boolean success, String errMsg) {
        this.success = success;
        this.errMsg = errMsg;
    }

    public Result(boolean success, T data, String errMsg) {
        this.success = success;
        this.data = data;
        this.errMsg = errMsg;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

}
