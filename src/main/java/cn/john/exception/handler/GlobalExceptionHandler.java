package cn.john.exception.handler;

import cn.john.dto.JsonMessage;
import cn.john.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

/**
 * @Author yanzhengwei
 * @Description GlobalExceptionHandler
 * @Date 2021/7/15
 **/
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(BusinessException.class)
    public JsonMessage globalException(HttpServletResponse response, BusinessException ex){
        log.info("GlobalExceptionHandler...");
        log.info("错误代码："  + response.getStatus());
        JsonMessage result = JsonMessage.systemError(ex.getMessage());
        return result;
    }
}
