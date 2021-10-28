package ru.mipt.bit.platformer.game.executor.direction_strategy;

import ru.mipt.bit.platformer.game.entity.Action;
import ru.mipt.bit.platformer.game.entity.GameObject;

import java.util.List;

public interface ActionGenerator {
    List<Action> getActions(GameObject gameObject, float deltaTime);
}
