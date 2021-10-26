package ru.mipt.bit.platformer.game;

import ru.mipt.bit.platformer.Tank;

import java.util.Random;

public class TankMovementScheduler {
    private static final Random randomGenerator = new Random();
    private static final double DIRECTION_CHANGE_PROBABILITY = 0.2;

    public void scheduleMovement(Tank tank, float deltaTime) {
        Direction tankDirection;

        if (!tank.getMovingGameObject().isMoving()) {
            if (shouldChangeDirection()) {
                tankDirection = Direction.getRandomDirection();
            } else {
                tankDirection = tank.getMovingGameObject().getLastDirection();
            }

        } else {
            tankDirection = tank.getMovingGameObject().getLastDirection();
        }

        Command tankMoveCommand = new MoveCommand(tank.getMovingGameObject(), tankDirection, deltaTime);
        tankMoveCommand.execute();
    }

    private boolean shouldChangeDirection() {
        return randomGenerator.nextDouble() < DIRECTION_CHANGE_PROBABILITY;
    }
}
