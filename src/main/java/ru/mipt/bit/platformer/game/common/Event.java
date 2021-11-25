package ru.mipt.bit.platformer.game.common;

import ru.mipt.bit.platformer.game.entity.GameObject;

public class Event {
    private final EventType eventType;
    private final GameObject gameObject;

    public Event(EventType eventType, GameObject gameObject) {
        this.eventType = eventType;
        this.gameObject = gameObject;
    }

    public EventType getEventType() {
        return eventType;
    }

    public GameObject getGameObject() {
        return gameObject;
    }
}
