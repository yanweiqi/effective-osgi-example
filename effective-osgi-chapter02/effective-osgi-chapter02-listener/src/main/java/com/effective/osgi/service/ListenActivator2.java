package com.effective.osgi.service;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class ListenActivator2 implements BundleActivator {

    @Override
    public void start(BundleContext bundleContext) throws Exception {
        System.out.println("启动serviceListener2.只监听mail139的bundle");
        String filter = "(vendor=mail139)";
        bundleContext.addServiceListener(serviceEvent -> {
            System.out.println("监听到：" + serviceEvent.getSource() + "\t" + serviceEvent.getType());
        },filter);
    }

    @Override
    public void stop(BundleContext bundleContext) throws Exception {

    }
}
