package ru.mipt.bit.platformer.game;

import com.badlogic.gdx.math.GridPoint2;

public class Player {
    private final MovingGameObject movingGameObject;

    public Player(GridPoint2 initialCoordinates, int width, int height, ProgressCalculator progressCalculator, ColliderManager colliderManager) {
        movingGameObject = new MovingGameObject(initialCoordinates, width, height, progressCalculator, colliderManager);
    }

    public MovingGameObject getMovingGameObject() {
        return movingGameObject;
    }

    public void move(Direction direction, float deltaTime) {
        movingGameObject.move(direction, deltaTime);
    }
}
