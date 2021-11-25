package ru.mipt.bit.platformer.game.entity;

public class Action {
    private final CommandType actionType;
    private final Direction direction;

    public Action(CommandType actionType, Direction direction) {
        this.actionType = actionType;
        this.direction = direction;
    }

    public CommandType getActionType() {
        return actionType;
    }

    public Direction getDirection() {
        return direction;
    }
}
