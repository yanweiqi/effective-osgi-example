package com.effective.osgi.service.impl;


import com.effective.osgi.service.api.Greeting;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator {

    public void start(BundleContext ctx) {
        System.out.println("注册实现类GreetingImpl");

        /**
         * 服务的注册
         * 参数1(String) Greeting.class.getName()表示接口,String[]表示接口数组
         * 参数2(Object)、Object表示实现类
         * 参数3(Dictionary)、表示服务属性
         */
        ctx.registerService(Greeting.class.getName(), new GreetingImpl("yanweiqi"), null);
    }

    public void stop(BundleContext ctx) {
    }
}
