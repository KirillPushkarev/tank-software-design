package ru.mipt.bit.platformer.game.command;

import ru.mipt.bit.platformer.game.Direction;
import ru.mipt.bit.platformer.game.entity.MovingGameObject;

public class MoveCommand implements Command {
    private final MovingGameObject movingGameObject;
    private final Direction direction;
    private final float deltaTime;

    public MoveCommand(MovingGameObject movingGameObject, Direction direction, float deltaTime) {
        this.movingGameObject = movingGameObject;
        this.direction = direction;
        this.deltaTime = deltaTime;
    }

    @Override
    public void execute() {
        movingGameObject.move(direction, deltaTime);
    }
}
