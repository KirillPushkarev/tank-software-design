package ru.mipt.bit.platformer.game.executor;

import ru.mipt.bit.platformer.game.entity.GameObject;

public interface ActionExecutor {
    void executeCommands(GameObject gameObject, float deltaTime);
}
