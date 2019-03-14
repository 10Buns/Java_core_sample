package com.summer.concurrent_test.countdownlatch_test;

import java.util.concurrent.CountDownLatch;

/**
 * @Package com.summer.concurrent_test.countdownlatch_test
 * @Author: summer
 * @Date: 2019-03-14 11:11
 */
public class DatabaseHealthChecker extends BaseHealthChecker {

    public DatabaseHealthChecker(CountDownLatch countDownLatch){
        super("database service checker", countDownLatch);
    }

    @Override
    public void verifyService() {
        System.out.println("checking " + this.getServiceName());
        try {
            Thread.sleep(7000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(this.getServiceName() + " is up!");
    }
}
