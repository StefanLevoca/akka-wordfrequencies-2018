package com.github.novotnyr.akka;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.routing.RoundRobinPool;

import java.util.HashMap;
import java.util.Map;

public class MasterActor extends AbstractActor {
    private LoggingAdapter logger = Logging.getLogger(getContext().system(), this);

	private ActorRef sentenceCounter = getContext().actorOf(SentenceCountActor.props()
			.withRouter(new RoundRobinPool(3)));

	private Map<String, Integer> allFrequencies = new HashMap<>();

	private int remainingSentences;

	public MasterActor(int remainingSentences) {
		this.remainingSentences = remainingSentences;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Receive createReceive() {
		return receiveBuilder()
				.match(String.class, sentence -> sentenceCounter.tell(sentence, getSelf()))
				.match(Map.class, frequencies -> {
					allFrequencies = MapUtils.aggregate(frequencies, allFrequencies);
					remainingSentences--;
					if (remainingSentences == 0) {
						logger.info(allFrequencies.toString());
						getContext().system().terminate();
					}
				})
				.build();
	}

	public static Props props(int numberOfSentences) {
		return Props.create(MasterActor.class, numberOfSentences);
	}
}