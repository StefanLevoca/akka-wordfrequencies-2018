package com.github.novotnyr.akka;

import akka.actor.AbstractActor;
import akka.actor.Props;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class SentenceCountActor extends AbstractActor {
    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .build();
    }

    public static Props props() {
        return Props.create(SentenceCountActor.class);
    }

    public Map<String, Integer> calculateFrequencies(String sentence) {
        Map<String, Integer> freqs = new HashMap<String, Integer>();

        Scanner scanner = new Scanner(sentence);
        while(scanner.hasNext()) {
            String word = scanner.next();

            int frequency = 1;
            if(freqs.containsKey(word)) {
                frequency += freqs.get(word);
            }
            freqs.put(word, frequency);
        }
        return freqs;
    }
}