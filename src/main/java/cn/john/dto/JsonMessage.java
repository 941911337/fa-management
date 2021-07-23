package cn.john.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

import static cn.john.common.BaseMessage.*;

/**
 * @Author yanzhengwei
 * @Description JsonMessage
 * @Date 2021/7/15
 **/
@Data
@AllArgsConstructor
public class JsonMessage<T> implements Serializable {

    private String msg;

    private int code;

    private T data;



    public static <T> JsonMessage<T> success(Object data) {
        return new JsonMessage<T>(SUCCESS.getMessage(), SUCCESS.getCode(), (T) data);
    }

    public static <T> JsonMessage<T> systemError(String msg) {
        return new JsonMessage<T>(msg, SYSTEM_ERROR.getCode(), null);
    }

    public static <T> JsonMessage<T> noLoginError(String msg) {
        return new JsonMessage<T>(msg, NO_LOGIN.getCode(), null);
    }

    public static <T> JsonMessage<T> paramError(String msg) {
        return new JsonMessage<T>(msg, PARAM_ERROR.getCode(), null);
    }


}
