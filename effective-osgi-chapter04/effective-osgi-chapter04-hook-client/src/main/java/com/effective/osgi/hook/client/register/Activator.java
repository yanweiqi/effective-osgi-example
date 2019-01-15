package com.effective.osgi.hook.client.register;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import java.util.Collection;

public class Activator implements BundleActivator {
    private void invoke(BundleContext bundleContext, String from) throws Exception {
        String filter = "(from=" + from + ")";
        Collection<ServiceReference<Runnable>> list = bundleContext.getServiceReferences(Runnable.class, filter);
        System.out.println(from + " size: " + list.size());
    }

    public void start(BundleContext bundleContext) throws Exception {
        invoke(bundleContext, "APP");
        invoke(bundleContext, "PC");
    }

    public void stop(BundleContext bundleContext) throws Exception {

    }
}
