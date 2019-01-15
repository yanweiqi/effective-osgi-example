package com.effective.osgi.ds;

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

    static Bundle client = null;
    static Bundle server = null;
    //static Bundle scr = null;

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

            //scr    = context.installBundle("file:org.apache.felix.scr.jar");
            server = context.installBundle("file:effective-ds-client.jar");
            client = context.installBundle("file:effective-ds-server.jar");
            felix.start();

            //scr.start();
            client.start();
            server.start();
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
            server.stop();
            System.out.println("server bundle unregister");
            TimeUnit.SECONDS.sleep(2);

            client.stop();
            System.out.println("client bundle unregister");
            TimeUnit.SECONDS.sleep(2);

            //scr.stop();
            felix.stop();
            System.out.println("felix bundle unregister");

            countDownLatch.countDown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
