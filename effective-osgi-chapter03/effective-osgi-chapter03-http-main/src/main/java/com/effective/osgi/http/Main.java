package com.effective.osgi.http;

import org.apache.felix.framework.Felix;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class Main {

    static Felix felix;

    static CountDownLatch countDownLatch = new CountDownLatch(1);

    static Bundle api = null;
    static Bundle server = null;
    static Bundle web01 = null;
    static Bundle web02 = null;

    public static void main(String[] args) {
        start();
    }

    public static void start(){
        try {
            final Map configMap = new HashMap();
            configMap.put(Constants.FRAMEWORK_STORAGE_CLEAN, "onFirstInit");
            felix = new Felix(configMap);
            felix.init();
            final BundleContext context = felix.getBundleContext();
            api = context.installBundle("file:effective-http-api.jar");
            server = context.installBundle("file:effective-http-server.jar");
            web01 = context.installBundle("file:effective-http-web01.jar");
            //web02 = context.installBundle("file:effective-http-web02.jar");
            felix.start();

            api.start();
            server.start();
            web01.start();
            //web02.start();
            Runtime.getRuntime().addShutdownHook(new Thread(()-> stop()));
            countDownLatch.await();
        } catch (Exception ex) {
            System.err.println("Error starting program: " + ex);
            ex.printStackTrace();
            System.exit(0);
        }
    }

    public static void stop(){
        try {
            api.stop();
            System.out.println("api bundle unregister");
            TimeUnit.SECONDS.sleep(2);

            web01.stop();
            System.out.println("web01 bundle unregister");
            TimeUnit.SECONDS.sleep(2);

            //web02.stop();
            //System.out.println("web02 bundle unregister");

            server.stop();
            System.out.println("server bundle unregister");
            TimeUnit.SECONDS.sleep(2);
            felix.stop();
            System.out.println("felix bundle unregister");

            countDownLatch.countDown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
