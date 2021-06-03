package com.ak.poc.spring.cache.hazelcast.configuration;

import com.hazelcast.core.DistributedObject;
import com.hazelcast.core.DistributedObjectEvent;
import com.hazelcast.core.DistributedObjectListener;

public class MyDistributedObjectListener implements DistributedObjectListener{

	@Override
	public void distributedObjectCreated(DistributedObjectEvent event) {
		DistributedObject instance = event.getDistributedObject();
        System.out.println("^^^^^^^^^Created  distributed object " + instance.getName() + ", service=" + instance.getServiceName());
	}

	@Override
	public void distributedObjectDestroyed(DistributedObjectEvent event) {
		DistributedObject instance = event.getDistributedObject();
        System.out.println("^^^^^^^^^^destroyed distributed object " + instance.getName() + ", service=" + instance.getServiceName());
	}

}
