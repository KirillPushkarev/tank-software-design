package ru.mipt.bit.platformer.game.executor;

import ru.mipt.bit.platformer.game.entity.GameObject;

public interface ActionExecutor {
    void executeFor(GameObject gameObject, float deltaTime);
}
