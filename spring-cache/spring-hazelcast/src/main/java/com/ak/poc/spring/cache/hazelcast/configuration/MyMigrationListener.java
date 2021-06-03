package com.ak.poc.spring.cache.hazelcast.configuration;

import com.hazelcast.partition.MigrationListener;
import com.hazelcast.partition.MigrationState;
import com.hazelcast.partition.PartitionLostEvent;
import com.hazelcast.partition.PartitionLostListener;
import com.hazelcast.partition.ReplicaMigrationEvent;

public class MyMigrationListener implements MigrationListener, PartitionLostListener {
	@Override
	public void migrationStarted(MigrationState state) {
		System.out.println("^^^^^^^^^Migration Started: " + state);
	}

	@Override
	public void migrationFinished(MigrationState state) {
		System.out.println("^^^^^^^^^Migration Finished: " + state);
	}

	@Override
	public void replicaMigrationCompleted(ReplicaMigrationEvent event) {
		System.out.println("^^^^^^^^^Replica Migration Completed: " + event);
	}

	@Override
	public void replicaMigrationFailed(ReplicaMigrationEvent event) {
		System.out.println("^^^^^^^^^Replica Migration Failed: " + event);
	}

	@Override
	public void partitionLost(PartitionLostEvent event) {
		System.out.println("^^^^^^^^^partitionLost: " + event);
	}
}