package com.effective.osgi.service;


import com.effective.osgi.service.api.Greeting;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

/**
 * 一、服务的查找
 * 1、通过bundleContext.getServiceReference()获取ServiceReference对象
 * 2、通过bundleContext.getService()获取真实的服务对象
 *
 * BundleContext.getServiceReference()要么返回null，要么返回一个服务。如果有多个服务匹配，也只会返回一个服务，找service.ranking属性
 * 最高的，如果注册是为指定该属性，则默认值为0找属性值最小的，就是先注册的服务
 *
 * 二、查找多个服务，有两个方法查找服务过滤
 * 1.bundleContext.getServiceReferences(Clazz clazz, String filter)
 * 2.bundleContext.getServiceReferences(String clazz, String filter)
 * 3.第二个参数接受标准的LDAP过滤字符串，这一节在chapter的实例中讲解，这里只是抛砖引玉
 *
 */
public class Client implements BundleActivator {

    public void start(BundleContext ctx) {
        ServiceReference ref = ctx.getServiceReference(Greeting.class.getName());

        ((Greeting) ctx.getService(ref)).sayHello();
    }

    public void stop(BundleContext ctx) {
    }
}
