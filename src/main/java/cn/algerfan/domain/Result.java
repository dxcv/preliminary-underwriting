package cn.algerfan.domain;

import cn.algerfan.enums.ResultCodeEnum;

/**
 * 结果集
 * @author AlgerFan
 */
public class Result<T> {

    //状态码
    private int code;
    //状态信息
    private String msg;
    //返回数据
    private Object data;

    public Result () {}

    public Result (ResultCodeEnum resultCodeEnum) {
        this.code = resultCodeEnum.getCode();
        this.msg = resultCodeEnum.getMsg();
    }

    public Result (ResultCodeEnum resultCodeEnum , Object data) {
        this(resultCodeEnum);
        this.data = data;
    }

    public Result(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public  Result(int code,String msg){
        this.code = code;
        this.msg  = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}