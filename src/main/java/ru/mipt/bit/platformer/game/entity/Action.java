package ru.mipt.bit.platformer.game.entity;

public class Action {
    private final ActionType actionType;
    private final Direction direction;

    public Action(ActionType actionType, Direction direction) {
        this.actionType = actionType;
        this.direction = direction;
    }

    public Action(ActionType actionType) {
        this.actionType = actionType;
        this.direction = null;
    }

    public ActionType getActionType() {
        return actionType;
    }

    public Direction getDirection() {
        return direction;
    }
}
