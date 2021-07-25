package cn.john.exception;

import lombok.Data;

/**
 * @Author John Yan
 * @Description BusinessException
 * @Date 2021/7/15
 **/
@Data
public class BusinessException extends RuntimeException{


    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message,Throwable cause) {
        super(message,cause);
    }

}
