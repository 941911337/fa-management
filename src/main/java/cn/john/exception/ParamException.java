package cn.john.exception;

import lombok.Data;

/**
 * @Author John Yan
 * @Description ParamException
 * @Date 2021/7/15
 **/
@Data
public class ParamException extends RuntimeException{


    public ParamException(String message) {
        super(message);
    }

    public ParamException(String message, Throwable cause) {
        super(message,cause);
    }

}
