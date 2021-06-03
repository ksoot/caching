package com.ak.poc.spring.cache.hazelcast.configuration;

import com.hazelcast.client.Client;
import com.hazelcast.client.ClientListener;

public class MyClientListener implements ClientListener{

	@Override
	public void clientConnected(Client client) {
        System.out.println("^^^^^^ clientConnected -> "+client.getName());
        }

	@Override
	public void clientDisconnected(Client client) {
        System.out.println("^^^^^^ clientDisconnected -> "+client.getName());
        }

}
