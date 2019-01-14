package com.effective.osgi.http.web;


import com.effective.osgi.http.api.RequestHandler;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

import java.util.Dictionary;
import java.util.Hashtable;



public class Activator implements BundleActivator {
	
	private ServiceRegistration<RequestHandler> serviceRegistration;
	
	public void start(BundleContext bundleContext) throws Exception {

		System.out.println("requestHandler 被加载");
		Dictionary<String, String> properties = new Hashtable<>();
		properties.put("http.url", "/home");
		
		serviceRegistration = bundleContext.registerService(RequestHandler.class, () -> "<h1>home page</h1>", properties);
	}

	public void stop(BundleContext bundleContext) throws Exception {
		serviceRegistration.unregister();
	}
}
