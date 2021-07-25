package cn.john.utils;

import cn.hutool.json.JSONUtil;
import cn.john.common.Constants;
import cn.john.dto.ClassVo;
import cn.john.dto.JsonMessage;
import cn.john.exception.ParamException;
import cn.john.model.TAccount;
import cn.john.model.TDict;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @Author John Yan
 * @Description SysUtil
 * @Date 2021/7/16
 **/
public abstract class SysUtil {

    private static  final ThreadLocal<Long> CURRENT_LOGIN_USER = new ThreadLocal<>();

    public static void putUser(TAccount account){
        CURRENT_LOGIN_USER.set(account.getId());
    }


    private static final ThreadLocal<Boolean> IS_TASK = new ThreadLocal<>();


    public static TAccount getUser(){
        if(IS_TASK.get() != null &&  IS_TASK.get() ){
            return TAccount.createAccount().initId(-1L);
        }
        Long userId = CURRENT_LOGIN_USER.get();
        return (TAccount) RedisUtil.hGet(Constants.LOGIN_CACHE_NAMESPACE+userId,"account");
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
                throw new ParamException(errors);
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
     */
    public static void markTask(){
        IS_TASK.set(true);
    }


    /**
     * 清除ThreadLocal
     */
    public static void removeTask(){
        IS_TASK.remove();
    }


    /**
     * 清除ThreadLocal
     */
    public static void removeThreadUser(){
        CURRENT_LOGIN_USER.remove();
    }


    /**
     * 获取字典值
     * @param key key
     * @return 字典
     */
    public static Map<String,String> getDict(String key){
        List<TDict> dict = JSONUtil.toList(JSONUtil.toJsonStr(RedisUtil.hGet("dictMap",key)),TDict.class);
        return dict.stream().collect(Collectors.toMap(TDict::getValue, TDict::getLabel));
    }

    /**
     * 获取资产分类
     * @return 资产名称id对应关系
     */
    public static Map<Long,String> getClassDict(){
        List<ClassVo> classVoList = (List<ClassVo>)  RedisUtil.get("class");
        return classVoList.stream().collect(Collectors.toMap(ClassVo::getId, ClassVo::getClassName));
    }



}
