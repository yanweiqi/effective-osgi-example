package com.effective.osgi.http.register;

import com.effective.osgi.http.api.RequestHandler;
import com.effective.osgi.http.server.HttpServer;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTrackerCustomizer;


public class HttpServiceTrackerCustomizer implements ServiceTrackerCustomizer<RequestHandler, RequestHandler> {

    private HttpServer server;
    private BundleContext context;

    public HttpServiceTrackerCustomizer(HttpServer server, BundleContext context) {
        this.server = server;
        this.context = context;
    }

    public RequestHandler addingService(ServiceReference<RequestHandler> reference) {
        Object url = reference.getProperty("http.url");
        if (url != null) {
            RequestHandler handler = context.getService(reference);
            String path = url.toString();
            if (server.exists(path)) {
                System.out.println("exists handler");
            } else {
                server.addHandler(path, handler);
                return handler;
            }
        }
        return null;
    }

    public void modifiedService(ServiceReference<RequestHandler> reference, RequestHandler service) {

    }

    public void removedService(ServiceReference<RequestHandler> reference, RequestHandler service) {
        Object url = reference.getProperty("http.url");
        if (url != null) {
            String path = url.toString();
            server.removeHandler(path);
        }
    }
}
