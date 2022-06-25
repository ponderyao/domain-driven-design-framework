package io.github.ponderyao.ddd.io;

import io.github.ponderyao.ddd.common.constant.ResponseStatus;

/**
 * Result：结果型输出对象
 *
 * @author PonderYao
 * @since 1.0.0
 */
public class Result extends DTO {

    private static final long serialVersionUID = 1787010952172815649L;
    
    public static final String DEFAULT_MSG = "操作成功";
    
    private int code;
    
    private String msg;
    
    private DTO data;

    public Result(int code, String msg, DTO data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
    
    public void setCode(int code) {
        this.code = code;
    }
    
    public void setMsg(String msg) {
        this.msg = msg;
    }
    
    public void setData(DTO data) {
        this.data = data;
    }
    
    public int getCode() {
        return this.code;
    }
    
    public String getMsg() {
        return this.msg;    
    }
    
    public DTO getData() {
        return this.data;
    }
    
    public static Result success() {
        return Result.success(null);
    }
    
    public static Result success(DTO data) {
        return new Result(ResponseStatus.SUCCESS, DEFAULT_MSG, data);
    }
    
    public static Result error(String msg) {
        return new Result(ResponseStatus.ERROR, msg, null);
    }
    
}
