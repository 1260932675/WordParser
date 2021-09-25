package nju.wordparser.entity;/*
 * @ClassName ResponseData
 * @Description TODO
 * @Author ling
 * @Date 2021/9/25 11:38
 * @Version 1.0
 */

import lombok.Data;

import java.io.Serializable;

@Data
public class ResponseData implements Serializable {
    int code;//返回状态，0为正常
    String msg;//success
    Object data;

    public ResponseData() {
        this.code = 0;
        this.msg = "success";
        this.data = null;
    }

    public ResponseData(Object data) {
        this.code = 0;
        this.msg = "success";
        this.data = data;
    }

    public ResponseData(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
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
