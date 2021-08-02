package cn.john.log.config;

import cn.hutool.json.JSONUtil;
import cn.john.log.annotation.SysOperateLog;
import cn.john.log.annotation.SysOperateMethodLog;
import cn.john.log.model.SysLogEntity;
import cn.john.log.task.LogCondition;
import cn.john.model.TAccount;
import cn.john.util.SysUtil;
import cn.john.utils.NetworkUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Conditional;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @Author John Yan
 * @Description LogAop
 * @Date 2021/8/1
 **/
@Aspect
@Component
@Slf4j
@Order
@Conditional(value = {LogCondition.class})
public class LogAop  {

    /**
     * 日志队列
     */
    private static final LinkedBlockingQueue<SysLogEntity> LOG_QUEUE = new LinkedBlockingQueue();
    /**
     * 日志对象
     */
    private static final SysLogEntity INIT_LOG = new SysLogEntity();


    /**
     * 拦截controller里面的日志
     */
    @Pointcut("@annotation(cn.john.log.annotation.SysOperateMethodLog)")
    public void faSysLogAspect() {
    }

    /**
     * 异常后置处理
     * @param joinPoint
     * @param ex
     */
    @AfterThrowing(value = "faSysLogAspect()", throwing = "ex")
    public void afterThrowing(JoinPoint joinPoint, Exception ex) {
        log(joinPoint, false, ex.getMessage());
    }

    /**
     * 正常返回后置处理
     * @param joinPoint
     */
    @AfterReturning("faSysLogAspect()")
    public void afterMethod(JoinPoint joinPoint) {
        log(joinPoint, true, null);
    }

    /**
     * 日志处理
     * @param joinPoint
     * @param success
     * @param exceptionMessage
     */
    private void log(JoinPoint joinPoint, boolean success, String exceptionMessage) {
        //拿到当前操作的Controller
        Object clazz = joinPoint.getTarget();
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        //拿到是操作哪个模块 需要在拦截类加注解[@SysOperateLog]
        if (!clazz.getClass().isAnnotationPresent(SysOperateLog.class)) {
            return;
        }
        SysOperateLog operateLog = clazz.getClass().getAnnotation(SysOperateLog.class);
        //拿到需要记录日志的功能名称 需要在方法上加注解[@SystemOperateMethodLog]
        Method method = methodSignature.getMethod();
        SysOperateMethodLog operateMethodLog = method.getAnnotation(SysOperateMethodLog.class);
        //如果方法没有此注解 不记录日志
        if (operateMethodLog == null) {
            return;
        }
        TAccount loginUser = SysUtil.getUser();
        if (null == loginUser) {
            loginUser = new TAccount();
            loginUser.setUserName("登录失败");
            loginUser.setId(0L);
            success = false;
        }
        Map<String,Object> param = new HashMap<>(8);
        String[] strings = methodSignature.getParameterNames();
        Object[] paramArray = joinPoint.getArgs();
        for (int i = 0; i < paramArray.length; i++) {
            Object arg = paramArray[i];
            if( !( arg instanceof BindingResult)){
                 param.put(strings[i],arg);
            }
        }
        boolean finalSuccess = success;
        SysLogEntity sysLogEntity = null;
        try {
            sysLogEntity = INIT_LOG.clone();
        } catch (CloneNotSupportedException e) {
           log.error(e.getMessage());
            sysLogEntity = new SysLogEntity();
        }
        sysLogEntity.setCreatedBy(loginUser.getId().toString());
        sysLogEntity.setCreatedTime(LocalDateTime.now());
        sysLogEntity.setAccount(loginUser.getAccount());
        SysLogEntity finalSysLogEntity = sysLogEntity;
        logSave(operateLog, operateMethodLog, finalSuccess,param, finalSysLogEntity);
    }

    /**
     * 日志模块 方法解析
     */
    public void logSave(SysOperateLog operateLog, SysOperateMethodLog operateMethodLog,
                                boolean result,  Map<String,Object> param,SysLogEntity sysLogEntity) {
        sysLogEntity.setModule(operateLog.module());
        sysLogEntity.setIp(NetworkUtil.getIp());
        sysLogEntity.setIpAddress(NetworkUtil.getIpAddress(sysLogEntity.getIp()));
        sysLogEntity.setResultStatus(result);
        sysLogEntity.setFunction(operateMethodLog.method());
        sysLogEntity.setParam(JSONUtil.toJsonStr(param));
        sysLogEntity.setIsDel(false);
        addLog(sysLogEntity);
    }

    /**
     * 日志塞入队列
     * @param sysLogEntity 日志
     */
    private static void addLog(SysLogEntity sysLogEntity){
        LOG_QUEUE.offer(sysLogEntity);
    }

    /**
     * 获取队列中最先入队的100条日志
     * @return 集合
     */
    public static List<SysLogEntity> getList(){
        List<SysLogEntity> result = new ArrayList<>(100);
        for (int i = 0; i < 100; i++) {
            if(!LOG_QUEUE.isEmpty()){
                result.add(LOG_QUEUE.poll());
            }else{
                break;
            }
        }
        return result;
    }



}
