package com.effective.osgi.http.register;

import com.effective.osgi.http.api.RequestHandler;
import com.effective.osgi.http.server.HttpServer;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.ServiceTracker;



public class Activator implements BundleActivator {
	
	HttpServer server ;
	
	private ServiceTracker<?, ?> tracker;
	
	public void start(BundleContext context) {
		server = new HttpServer();
		new Thread(() -> {
			try {
				server.start();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}).start();
		
		HttpServiceTrackerCustomizer hstc = new HttpServiceTrackerCustomizer(server, context);
		tracker = new ServiceTracker<>(context, RequestHandler.class.getName(), hstc);
		tracker.open();
	}

	public void stop(BundleContext context) throws Exception {
		server.close();
		tracker.close();
	}
}