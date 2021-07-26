package cn.john.task;

import cn.john.model.TEnclosure;
import cn.john.oss.OssClient;
import cn.john.service.ITEnclosureService;
import cn.john.util.SysUtil;
import lombok.extern.slf4j.Slf4j;
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

    private final ITEnclosureService enclosureService;

    public Task(ITEnclosureService enclosureService) {
        this.enclosureService = enclosureService;
    }


    @Scheduled(cron = "0 0/5 * * * ?  ")
    public void backUp(){
        log.info("==================定时执行开始=========================");
        SysUtil.markTask();
        List<TEnclosure> needList = enclosureService.getNeedBackUp();
        for (TEnclosure enclosure : needList) {
            if(OssClient.download(enclosure.getPath())){
                enclosureService.markDownLoad(enclosure.getId());
            }
        }
        SysUtil.removeTask();
        log.info("==================定时执行结束=========================");
    }
}
