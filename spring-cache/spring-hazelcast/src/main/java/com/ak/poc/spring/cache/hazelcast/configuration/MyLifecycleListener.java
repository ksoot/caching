package com.ak.poc.spring.cache.hazelcast.configuration;

import com.hazelcast.core.LifecycleEvent;
import com.hazelcast.core.LifecycleListener;

public class MyLifecycleListener  implements LifecycleListener {
    @Override
    public void stateChanged(LifecycleEvent event) {
        System.out.println("^^^^^^ state changed -> "+event.getState());
    }
}
