package com.effective.osgi.service.mail139;

import com.effective.osgi.service.api.EmailService;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.concurrent.TimeUnit;

/**
 * 163实现邮件发送
 *
 * 使用Dictionary参数，加入自己的属性，便于客户端使用时进行属性过滤
 */
public class Activator implements BundleActivator {

    private ServiceRegistration<EmailService> serviceRegistration;

    @Override
    public void start(BundleContext context) throws Exception {
        Dictionary<String, Object> properties = new Hashtable();
        properties.put("vendor", "mail139");
        properties.put("service.type","444");
        serviceRegistration = context.registerService(EmailService.class, new EmailServiceImpl(), properties);

        new Thread(()->{
            try {
                TimeUnit.SECONDS.sleep(5);
                properties.put("service.type","111");
                serviceRegistration.setProperties(properties);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(()->{
            try {
                TimeUnit.SECONDS.sleep(8);
                properties.put("vendor", "mail138");
                serviceRegistration.setProperties(properties);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }).start();

    }

    @Override
    public void stop(BundleContext bundleContext) throws Exception {

    }
}
