package com.effective.osgi.service;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class ListenActivator implements BundleActivator {

    @Override
    public void start(BundleContext bundleContext) throws Exception {
        System.out.println("启动serviceListener.");
        bundleContext.addServiceListener(serviceEvent -> {
            System.out.println("监听到："+serviceEvent.getSource() + "\t" +serviceEvent.getType());
        });
    }

    @Override
    public void stop(BundleContext bundleContext) throws Exception {

    }
}
