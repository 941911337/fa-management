package cn.john.utils;

import java.util.concurrent.Semaphore;

/**
 * description: AdjustableSemaphore
 * date: 2020/11/12 14:45
 * author: yzw
 * version: 1.0
 */
public class AdjustableSemaphore {

    private final ResizeableSemaphore  semaphore = new ResizeableSemaphore();

    private int maxPermits = 0;

    public AdjustableSemaphore(){

    }

    public synchronized void setMaxPermits(int newMax){
        if(newMax < 1){
            throw new IllegalArgumentException("Semaphore size must be at least 1,"
                    + " was " + newMax);
        }

        int delta = newMax - this.maxPermits;

        if(delta == 0){
            return ;
        }else if(delta > 0){
            this.semaphore.release(delta);
        }else {
            delta *= -1;
            this.semaphore.reducePermits(delta);
        }

        this.maxPermits = newMax;

    }

    public int availablePermits(){
        return this.semaphore.availablePermits();
    }

    public void release(){
        this.semaphore.release();
    }

    public boolean tryAcquire(){
        return this.semaphore.tryAcquire();
    }




    private static final class ResizeableSemaphore extends Semaphore {

        ResizeableSemaphore(){
            super(0);
        }

        @Override
        protected void reducePermits(int reduction){
            super.reducePermits(reduction);
        }

    }


}
