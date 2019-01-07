package com.effective.osgi.service;


import com.effective.osgi.service.api.EmailService;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;

/**
 * 一、查找多个服务，有两个方法查找服务过滤
 * 1.bundleContext.getServiceReferences(Clazz clazz, String filter)
 * 2.bundleContext.getServiceReferences(String clazz, String filter)
 * <p>
 * <p>
 * 第二个参数接受标准的LDAP过滤字符串。
 * <p>
 * 示例
 * 属性匹配：(vendor=Apache)、(count>3)
 * 通配符：(vendor=Apache*)
 * 判断某个属性是否存在：(vendor=)
 * 条件非：(!(vendor=Apache))
 * 条件与：(&(objectClass=com.edu.osgi.user.IUserService)(type=1))
 * 条件或：(|(type=1)(type=2))
 */
public class MailClient implements BundleActivator {

    public void start(BundleContext context) throws InvalidSyntaxException {
        ServiceReference<?>[] refs = context.getAllServiceReferences(EmailService.class.getName(), null);
        if (refs != null) {
            System.out.println("总共获取到" + refs.length + "个服务");
            for (ServiceReference<?> ref : refs) {
                EmailService emailService = (EmailService) context.getService(ref);
                emailService.send("123@example.com", "OSGi", "hello OSGi");
            }
        }
        System.out.println("------分割线------");

        getService(context, "mail139");

    }

    private void getService(BundleContext context, String vendor) throws InvalidSyntaxException {
        String filter = "(vendor=" + vendor + ")";

        ServiceReference<?>[] list = context.getServiceReferences(EmailService.class.getName(), filter);
        if (list != null) {
            for (ServiceReference<?> ref : list) {
                EmailService emailService = (EmailService) context.getService(ref);
                System.out.println(emailService);
                emailService.send("test@example.com", "OSGi", "hello OSGi");
            }
        }
    }

    public void stop(BundleContext ctx) {
    }
}
