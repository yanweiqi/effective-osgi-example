package com.effective.osgi.ds.client;

import java.util.Map;

public class MyClient {

    public void bind(Runnable run, Map<?, ?> map) {
        System.out.println("=====bind=====");
        System.out.println(map);
        run.run();
    }

    public void unbind(Runnable run, Map<?, ?> map) {
        System.out.println("=====unbind=====");
        System.out.println(map);
        run.run();
    }
}
