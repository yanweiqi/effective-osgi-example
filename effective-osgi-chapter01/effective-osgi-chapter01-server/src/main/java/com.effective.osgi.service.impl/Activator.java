package com.effective.osgi.service.impl;


import com.effective.osgi.service.api.Greeting;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator {

    public void start(BundleContext ctx) {
        System.out.println("注册实现类GreetingImpl");
        ctx.registerService(Greeting.class.getName(), new GreetingImpl("yanweiqi"), null);
    }

    public void stop(BundleContext ctx) {
    }
}
