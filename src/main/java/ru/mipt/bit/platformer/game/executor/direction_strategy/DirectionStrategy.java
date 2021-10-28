package ru.mipt.bit.platformer.game.executor.direction_strategy;

import ru.mipt.bit.platformer.game.entity.Direction;
import ru.mipt.bit.platformer.game.entity.GameObject;

public interface DirectionStrategy {
    Direction getDirection(float deltaTime, GameObject gameObject);
}
