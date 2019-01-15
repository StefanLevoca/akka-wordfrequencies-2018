package com.github.novotnyr.akka;

import akka.actor.AbstractActor;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class MasterActor extends AbstractActor {
    private LoggingAdapter logger = Logging.getLogger(getContext().system(), this);

	@Override
	public Receive createReceive() {
		return receiveBuilder()
				.build();
	}

	public static Props props() {
		return Props.create(MasterActor.class);
	}
}