package com.summer.concurrent_test.countdownlatch_test;

import java.util.concurrent.CountDownLatch;

/**
 * @Package com.summer.concurrent_test.countdownlatch_test
 * @Author: summer
 * @Date: 2019-03-14 11:03
 */
public abstract class BaseHealthChecker implements Runnable{

    private CountDownLatch countDownLatch;
    private String serviceName;
    private boolean serviceUp;

    public BaseHealthChecker(String serviceName, CountDownLatch countDownLatch){
        super();
        this.serviceName = serviceName;
        this.serviceUp = false;
        this.countDownLatch = countDownLatch;
    }


    @Override
    public void run() {
        try {
            verifyService();
            serviceUp = true;
        }catch (Throwable t){
            t.printStackTrace(System.err);
            serviceUp = false;
        }finally {
            if (null != countDownLatch){
                countDownLatch.countDown();
            }
        }
    }

    public String getServiceName() {
        return serviceName;
    }

    public boolean isServiceUp() {
        return serviceUp;
    }

    public abstract void verifyService();
}
