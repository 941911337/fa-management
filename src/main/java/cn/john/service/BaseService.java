package cn.john.service;

import cn.john.utils.SysUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.Date;

/**
 * @Author yanzhengwei
 * @Description BaseService
 * @Date 2021/7/16
 **/
@Slf4j
public class BaseService<T> {

    @Autowired
    protected T baseMapper;

    /**
     * 更新实体类的创建基本信息
     *
     * @param t
     */
    public <T> void createInfo(T t) {
        try {
            Method setCreateTime = t.getClass().getMethod("setCreatedTime", Date.class);
            setCreateTime.invoke(t, new Date());
        } catch (Exception e) {
            log.info("创建时间更新失败:", e);
        }

        try {
            Method getIsDel = t.getClass().getMethod("getIsDel");
            Object invoke = getIsDel.invoke(t);
            if (StringUtils.isEmpty(invoke)) {
                Method setIsDel = t.getClass().getMethod("setIsDel", Integer.class);
                setIsDel.invoke(t, 0);
            }
        } catch (Exception e) {
            log.info("删除标识更新失败:", e);
        }


        try {
            Method setCreatedBy = t.getClass().getMethod("setCreatedBy", String.class);
            setCreatedBy.invoke(t, SysUtil.getUser().getId().toString());
        } catch (Exception e) {
            log.info("创建人更新失败:", e);
        }

        //新建的同时，把更新信息补充上
        updateInfo(t);

    }

    /**
     * 更新实体类的更新基本信息
     *
     * @param t
     */
    public <T> void updateInfo(T t) {

        try {
            Method setUpdateTime = t.getClass().getMethod("setUpdatedTime", Date.class);
            setUpdateTime.invoke(t, new Date());
        } catch (Exception e) {
            log.info("更新时间更新失败:", e);
        }

        try {
            Method setUpdateUserName = t.getClass().getMethod("setUpdatedBy", String.class);
            setUpdateUserName.invoke(t, SysUtil.getUser().getId().toString());
        } catch (Exception e) {
            log.info("更新人更新失败:", e);
        }

    }
}
