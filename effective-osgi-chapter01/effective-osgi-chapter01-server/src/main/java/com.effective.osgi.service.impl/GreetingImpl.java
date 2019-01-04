package com.effective.osgi.service.impl;

import com.effective.osgi.service.api.Greeting;

public class GreetingImpl implements Greeting {

    final String name;

    GreetingImpl(String name) {
        this.name = name;
    }

    @Override
    public void sayHello() {
        System.out.println("Hello, " + name + "!");
    }
}
