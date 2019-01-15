package com.effective.osgi.hook.service.register;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

import java.util.Dictionary;
import java.util.Hashtable;

public class Activator implements BundleActivator {
    private ServiceRegistration<Runnable> serviceRegistration1;
    private ServiceRegistration<Runnable> serviceRegistration2;

    public void start(BundleContext context) throws Exception {
        Dictionary<String, String> properties = new Hashtable<>();
        properties.put("from", "APP");

        serviceRegistration1 = context.registerService(Runnable.class, () -> {
            System.out.println("from app");
        }, properties);

        Dictionary<String, String> properties2 = new Hashtable<>();
        properties2.put("from", "PC");

        serviceRegistration2 = context.registerService(Runnable.class, () -> {
            System.out.println("from pc");
        }, properties2);
    }

    public void stop(BundleContext context) throws Exception {
        serviceRegistration1.unregister();
        serviceRegistration2.unregister();
    }
}
