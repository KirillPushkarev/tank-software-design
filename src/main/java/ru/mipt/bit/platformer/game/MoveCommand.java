package ru.mipt.bit.platformer.game;

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
