package com.effective.osgi.service.mail163;


import com.effective.osgi.service.api.EmailService;

public class EmailServiceImpl implements EmailService {
	public void send(String dest, String title, String content) {
		System.out.println("mail163 email send. dest=" + dest + ",title=" + title + ",content=" + content);
	}

}
