package ru.mipt.bit.platformer.game;

import ru.mipt.bit.platformer.game.command.Command;
import ru.mipt.bit.platformer.game.command.MoveCommand;
import ru.mipt.bit.platformer.game.entity.MovingGameObject;
import ru.mipt.bit.platformer.game.entity.Tank;

import java.util.Random;

public class TankMovementScheduler {
    private static final Random randomGenerator = new Random();
    private static final double DIRECTION_CHANGE_PROBABILITY = 0.2;

    public void scheduleMovement(Tank tank, float deltaTime) {
        Direction tankDirection;

        MovingGameObject tankMovingGameObject = tank.getMovingGameObject();
        if (tankMovingGameObject.isMoving()) {
            tankDirection = tankMovingGameObject.getLastDirection();
        } else {
            if (shouldChangeDirection()) {
                tankDirection = Direction.getRandomDirection();
            } else {
                tankDirection = tankMovingGameObject.getLastDirection();
            }
        }

        Command tankMoveCommand = new MoveCommand(tankMovingGameObject, tankDirection, deltaTime);
        tankMoveCommand.execute();
    }

    private boolean shouldChangeDirection() {
        return randomGenerator.nextDouble() < DIRECTION_CHANGE_PROBABILITY;
    }
}
