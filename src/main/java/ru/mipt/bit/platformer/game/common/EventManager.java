package ru.mipt.bit.platformer.game.common;

import java.util.ArrayList;
import java.util.List;

public class EventManager implements Publisher {
    private final List<Subscriber> subscribers = new ArrayList<>();

    public void subscribe(Subscriber subscriber) {
        subscribers.add(subscriber);
    }

    public void notifySubscriber(Subscriber subscriber, Event event) {
        subscriber.notify(event);
    }

    public void notifySubscribers(Event event) {
        for (Subscriber subscriber : subscribers) {
            subscriber.notify(event);
        }
    }
}
