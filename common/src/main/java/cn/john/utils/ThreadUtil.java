package cn.john.utils;

import java.util.concurrent.*;

/**
 * @Author John Yan
 * @Description ThreadUtil
 * @Date 2021/8/2
 **/
public abstract class ThreadUtil {


    public static final  ExecutorService IOThreadPool = new ThreadPoolExecutor(ioConcentratedPoolSize(0.9),
            ioConcentratedPoolSize(0.9),
            0, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(100)
            , Executors.defaultThreadFactory(),  new ThreadPoolExecutor.AbortPolicy());;



    /**
     * io 密集型 线程池数量初始化
     * @param blockingCoefficient 每个任务占用的IO占用的时间百分比
     * @return  线程数
     */
    public static int ioConcentratedPoolSize(double blockingCoefficient) {
        // 获取当前服务器处理器数量
        int availableProcessors = Runtime.getRuntime().availableProcessors();
        // Nthreads=Ncpu*Ucpu*（1+W/C）
        int poolSize = (int) (availableProcessors * (1+blockingCoefficient/(1-blockingCoefficient)));
        return poolSize;
    }



}
