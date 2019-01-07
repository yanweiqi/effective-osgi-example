package com.effective.osgi;

import org.apache.felix.framework.Felix;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.Constants;

import java.util.HashMap;
import java.util.Map;

public class Main {
    static Felix felix;

    public static void main(String[] args) {

        Bundle api = null;
        Bundle server163 = null;
        Bundle server139 = null;
        Bundle client = null;
        try {

            final Map configMap = new HashMap();
            configMap.put(Constants.FRAMEWORK_STORAGE_CLEAN, "onFirstInit");
            felix = new Felix(configMap);
            felix.init();

            final BundleContext context = felix.getBundleContext();

            api = context.installBundle("file:effective-api.jar");
            server163 = context.installBundle("file:effective-server-163.jar");
            server139 = context.installBundle("file:effective-server-139.jar");
            client = context.installBundle("file:effective-mail-client.jar");

            felix.start();

            api.start();
            server163.start();
            server139.start();
            client.start();
        } catch (Exception ex) {
            System.err.println("Error starting program: " + ex);
            ex.printStackTrace();
            System.exit(0);
        } finally {
            try {
                api.stop();
                server163.stop();
                server139.stop();
                client.stop();
                felix.stop();
            } catch (BundleException e) {
                e.printStackTrace();
            }
        }
    }
}
