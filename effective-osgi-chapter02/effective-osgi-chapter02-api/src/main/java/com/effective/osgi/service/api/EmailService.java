package com.effective.osgi.service.api;

public interface EmailService {

    /**
     * 发送邮件
     * @param dest
     * @param title
     * @param content
     */
    void send(String dest, String title, String content);
}
