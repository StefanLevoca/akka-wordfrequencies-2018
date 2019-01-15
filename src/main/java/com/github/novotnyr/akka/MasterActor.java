package com.github.novotnyr.akka;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.routing.RoundRobinPool;

import java.util.Map;

public class MasterActor extends AbstractActor {
    private LoggingAdapter logger = Logging.getLogger(getContext().system(), this);

	private ActorRef sentenceCounter = getContext().actorOf(SentenceCountActor.props()
			.withRouter(new RoundRobinPool(3)));

	@Override
	public Receive createReceive() {
		return receiveBuilder()
				.match(String.class, sentence -> sentenceCounter.tell(sentence, getSelf()))
				.match(Map.class, frequencies -> logger.info(frequencies.toString()))
				.build();
	}

	public static Props props() {
		return Props.create(MasterActor.class);
	}
}