package cn.john.log.task;

import cn.john.log.config.LogAop;
import cn.john.log.service.ITFaSysLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Conditional;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * description: Task
 * date: 2020/11/19 10:11
 * version: 1.0
 * @author John Yan
 */
@Component
@Slf4j
@Conditional(value = {LogCondition.class})
public class LogTask {

    private final ITFaSysLogService logService;

    public LogTask(ITFaSysLogService logService) {
        this.logService = logService;
    }


    @Scheduled(cron = "0 0/5 * * * ?  ")
    public void backUp(){
        log.info("==================日志定时执行开始=========================");
        logService.batchSaveLog(LogAop.getList());
        log.info("==================日志定时执行结束=========================");
    }
}
