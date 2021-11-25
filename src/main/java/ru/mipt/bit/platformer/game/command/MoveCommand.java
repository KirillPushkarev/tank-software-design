package ru.mipt.bit.platformer.game.command;

import ru.mipt.bit.platformer.game.entity.Direction;
import ru.mipt.bit.platformer.game.entity.GameObject;

public class MoveCommand implements Command {
    private final GameObject gameObject;
    private final Direction direction;

    public MoveCommand(GameObject gameObject, Direction direction) {
        this.gameObject = gameObject;
        this.direction = direction;
    }

    @Override
    public void execute() {
        gameObject.move(direction);
    }
}
