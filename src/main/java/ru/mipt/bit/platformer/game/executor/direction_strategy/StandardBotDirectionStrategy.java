package ru.mipt.bit.platformer.game.executor.direction_strategy;

import ru.mipt.bit.platformer.game.entity.Direction;
import ru.mipt.bit.platformer.game.entity.GameObject;

import java.util.Random;

public class StandardBotDirectionStrategy implements DirectionStrategy {
    private static final Random randomGenerator = new Random();
    private static final double DIRECTION_CHANGE_PROBABILITY = 0.2;

    public Direction getDirection(float deltaTime, GameObject gameObject) {
        Direction direction;
        if (gameObject.isMoving()) {
            direction = gameObject.getLastDirection();
        } else {
            direction = getNewDirection(gameObject);
        }

        return direction;
    }

    private Direction getNewDirection(GameObject gameObject) {
        Direction direction;
        if (shouldChangeDirection()) {
            direction = Direction.getRandomDirection();
        } else {
            direction = gameObject.getLastDirection();
        }

        return direction;
    }

    private boolean shouldChangeDirection() {
        return randomGenerator.nextDouble() < DIRECTION_CHANGE_PROBABILITY;
    }
}
