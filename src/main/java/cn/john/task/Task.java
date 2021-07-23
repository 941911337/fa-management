package cn.john.task;

import cn.john.model.Enclosure;
import cn.john.oss.OssClient;
import cn.john.oss.OssInterface;
import cn.john.service.EnclosureService;
import cn.john.utils.SysUtil;
import com.alibaba.nacos.shaded.org.checkerframework.checker.units.qual.C;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Conditional;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * description: Task
 * date: 2020/11/19 10:11
 * version: 1.0
 * @author John Yan
 */
@Component
@Slf4j
@Conditional(value = {TaskCondition.class})
public class Task {

    @Autowired
    private EnclosureService enclosureService;


    @Scheduled(cron = "0 0/5 * * * ?  ")
    public void backUp(){
        log.info("==================定时执行开始=========================");
        SysUtil.markTask();
        List<Enclosure> needList = enclosureService.getNeedBackUp();
        for (Enclosure enclosure : needList) {
            if(OssClient.download(enclosure.getPath())){
                enclosureService.markDownLoad(enclosure.getId());
            }
        }
        SysUtil.removeTask();
        log.info("==================定时执行结束=========================");
    }
}
