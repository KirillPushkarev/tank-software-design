package ru.mipt.bit.platformer.game.executor;

import ru.mipt.bit.platformer.game.command.Command;
import ru.mipt.bit.platformer.game.command.MoveCommand;
import ru.mipt.bit.platformer.game.entity.Direction;
import ru.mipt.bit.platformer.game.entity.GameObject;
import ru.mipt.bit.platformer.game.executor.direction_strategy.DirectionStrategy;

public class CommonActionExecutor implements ActionExecutor {
    private final DirectionStrategy directionStrategy;

    public CommonActionExecutor(DirectionStrategy directionStrategy) {
        this.directionStrategy = directionStrategy;
    }

    public void executeCommands(GameObject gameObject, float deltaTime) {
        move(gameObject, deltaTime);
    }

    private void move(GameObject gameObject, float deltaTime) {
        Direction direction = directionStrategy.getDirection(deltaTime, gameObject);
        Command moveCommand = new MoveCommand(gameObject, direction, deltaTime);
        moveCommand.execute();
    }
}
