package com.example.demo.testing;

import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;

public class FluxSubscriberAlt<Emp> extends BaseSubscriber<Emp> {
    @Override
    protected void hookOnSubscribe(Subscription subscription) {
        System.out.println(String.format("subscribed %s", subscription));
        request(1);     //to request next element
    }

    @Override
    protected void hookOnNext(Emp value) {
        System.out.println(String.format("next one %s", value));
        request(1);
    }

    @Override
    protected void hookOnComplete() {
        System.out.println("completed");
    }

    @Override
    protected void hookOnError(Throwable throwable) {
        System.out.println(String.format("Error occured %s", throwable.getMessage()));
    }
}
