package ru.mipt.bit.platformer.game.executor.direction_strategy;

import ru.mipt.bit.platformer.game.entity.Action;
import ru.mipt.bit.platformer.game.entity.ActionType;
import ru.mipt.bit.platformer.game.entity.Direction;
import ru.mipt.bit.platformer.game.entity.GameObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class StandardBotActionGenerationStrategy implements ActionGenerator {
    private static final Random randomGenerator = new Random();
    private static final double DIRECTION_CHANGE_PROBABILITY = 0.2;

    @Override
    public List<Action> getActions(GameObject gameObject, float deltaTime) {
        List<Action> actions = new ArrayList<>();
        actions.add(new Action(ActionType.MOVE, getDirection(gameObject, deltaTime)));

        return actions;
    }

    private Direction getDirection(GameObject gameObject, float deltaTime) {
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
