package io.github.ponderyao.ddd.io;

import io.github.ponderyao.ddd.common.constant.ResponseStatus;

/**
 * Result：结果型输出对象
 *
 * @author PonderYao
 * @since 1.0.0
 */
public class Result<T> extends DTO {

    private static final long serialVersionUID = 1787010952172815649L;
    
    private int code;
    
    private String errCode;
    
    private String errMsg;
    
    private T data;

    public Result(int code, String errCode, String errMsg, T data) {
        this.code = code;
        this.errCode = errCode;
        this.errMsg = errMsg;
        this.data = data;
    }
    
    public void setCode(int code) {
        this.code = code;
    }
    
    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }
    
    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }
    
    public void setData(T data) {
        this.data = data;
    }
    
    public int getCode() {
        return this.code;
    }
    
    public String getErrCode() {
        return this.errCode;
    }
    
    public String getErrMsg() {
        return this.errMsg;    
    }
    
    public T getData() {
        return this.data;
    }
    
    public static <T> Result<T> success() {
        return Result.success(null);
    }
    
    public static <T> Result<T> success(T data) {
        return new Result<>(ResponseStatus.SUCCESS, null, null, data);
    }
    
    public static <T> Result<T> fail(String errMsg) {
        return Result.fail(null, errMsg);
    }
    
    public static <T> Result<T> fail(String errCode, String errMsg) {
        return new Result<>(ResponseStatus.ERROR, errCode, errMsg, null);
    }
}
