package com.ak.poc.spring.cache.hazelcast.configuration;

import com.hazelcast.cluster.MembershipEvent;
import com.hazelcast.cluster.MembershipListener;

public class MyMemberShipListener implements MembershipListener{

	@Override
	public void memberAdded(MembershipEvent membershipEvent) {
		System.out.println("^^^^^^^member evicted -> "+membershipEvent);	
	}

	@Override
	public void memberRemoved(MembershipEvent membershipEvent) {
		System.out.println("^^^^^^^member evicted -> "+membershipEvent);	
	}

}
