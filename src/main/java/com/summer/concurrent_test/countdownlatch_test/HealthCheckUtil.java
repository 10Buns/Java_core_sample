package com.summer.concurrent_test.countdownlatch_test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @Package com.summer.concurrent_test.countdownlatch_test
 * @Author: summer
 * @Date: 2019-03-14 11:20
 */
public class HealthCheckUtil {

    private static List<BaseHealthChecker> services;
    private static CountDownLatch countDownLatch;

    private final static HealthCheckUtil INSTANCE = new HealthCheckUtil();

    public static HealthCheckUtil getInstance(){
        return INSTANCE;
    }

    public static boolean checkExternalServices() throws InterruptedException {
        countDownLatch = new CountDownLatch(3);
        services = new ArrayList<>();
        services.add(new NetworkHealthChecker(countDownLatch));
        services.add(new CacheHealthChecker(countDownLatch));
        services.add(new DatabaseHealthChecker(countDownLatch));

        Executor executor = Executors.newFixedThreadPool(services.size());
        for (BaseHealthChecker s : services){
            executor.execute(s);
        }

        countDownLatch.await();

        for (BaseHealthChecker s : services){
            if (!s.isServiceUp()){
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        boolean result =false;
        try {
            result = checkExternalServices();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("服务检查完成, 状态:"+ result);

    }

}