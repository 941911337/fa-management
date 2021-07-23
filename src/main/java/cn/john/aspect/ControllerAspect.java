package cn.john.aspect;

import cn.john.utils.SysUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @Author yanzhengwei
 * @Description ControllerAspect
 * @Date 2021/7/23
 **/
@Aspect
@Component
@Slf4j
public class ControllerAspect {

    @Pointcut("execution(public * cn.john.controller.*.*(..))")
    public void controller(){
    }


    /**
     * 后置最终通知,final增强，不管是抛出异常或者正常退出都会执行
     */
    @After("controller()")
    public void after(JoinPoint jp){
        log.info("调用removeThreadUser");
        SysUtil.removeThreadUser();
    }

}
