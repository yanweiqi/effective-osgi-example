package com.effective.osgi.hook.core.register;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.framework.hooks.service.FindHook;

import java.util.Collection;

public class Activator implements BundleActivator {

    private ServiceRegistration<FindHook> serviceRegistration;

    public void start(BundleContext bundleContext) throws Exception {
        serviceRegistration = bundleContext.registerService(FindHook.class, new MyFindHook(), null);
    }

    public void stop(BundleContext bundleContext) throws Exception {
        serviceRegistration.unregister();
    }
}

class MyFindHook implements FindHook {

    public void find(BundleContext context,
                     String name,
                     String filter,
                     boolean allServices,
                     Collection<ServiceReference<?>> references) {
        System.out.println("service hook is invoke ");
        System.out.println(name);
        System.out.println(filter);
        System.out.println(allServices);
        for (ServiceReference<?> sf : references) {
            if ("APP".equals(sf.getProperty("from"))) {
                references.remove(sf);
            }
        }
    }
}

