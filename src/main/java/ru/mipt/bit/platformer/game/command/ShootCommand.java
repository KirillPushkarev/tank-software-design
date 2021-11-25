package ru.mipt.bit.platformer.game.command;

import ru.mipt.bit.platformer.game.entity.GameObject;

public class ShootCommand implements Command {
    private final GameObject gameObject;

    public ShootCommand(GameObject gameObject) {
        this.gameObject = gameObject;
    }

    @Override
    public void execute() {
        gameObject.shoot();
    }
}
