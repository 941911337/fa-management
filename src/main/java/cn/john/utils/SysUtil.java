package cn.john.utils;

import cn.john.common.Constants;
import cn.john.dto.JsonMessage;
import cn.john.model.Account;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @Author yanzhengwei
 * @Description SysUtil
 * @Date 2021/7/16
 **/
public abstract class SysUtil {

    private static  final ThreadLocal<Long> CURRENT_LOGIN_USER = new ThreadLocal<>();

    public static void putUser(Account account){
        CURRENT_LOGIN_USER.set(account.getId());
    }


    private static final ThreadLocal<Boolean> IS_TASK = new ThreadLocal<>();


    public static Account getUser(){
        if(IS_TASK.get() != null &&  IS_TASK.get() ){
            return Account.createAccount().initId(-1L);
        }
        Long userId = CURRENT_LOGIN_USER.get();
        return (Account) RedisUtil.hGet(Constants.LOGIN_CACHE_NAMESPACE+userId,"account");
    }

    public static void logout(){
        Long userId = CURRENT_LOGIN_USER.get();
        RedisUtil.del(Constants.LOGIN_CACHE_NAMESPACE+userId);
    }


    /**
     * 参数校验
     * @param result  BindingResult
     * @return 返回为Null 则没有参数错误
     */
    public static JsonMessage paramValid(BindingResult result){
        if (result.hasErrors()) {
            List<String> collect = result.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList());
            if(!CollectionUtils.isEmpty(collect)){
                String errors = org.apache.commons.lang3.StringUtils.join(collect.toArray(), ",");
                return JsonMessage.paramError(errors);
            }
        }
        return null;
    }

    /**
     * 生成uuid
     * @return 返回uuid
     */
    public static String getUuid(){
        String uuid = UUID.randomUUID().toString();
        return uuid.replaceAll("-","");
    }

    /**
     * 定时任务线程标记
     * @return
     */
    public static void markTask(){
        IS_TASK.set(true);
    }


    /**
     * 定时任务线程标记
     * @return
     */
    public static void removeTask(){
        IS_TASK.remove();
    }


    /**
     * 定时任务线程标记
     * @return
     */
    public static void removeThreadUser(){
        CURRENT_LOGIN_USER.remove();
    }
}
