package ru.mipt.bit.platformer.game;

import com.badlogic.gdx.math.GridPoint2;

public class Player {
    private static final float TIME_OF_PASSING_ONE_TILE = 0.3f;
    private final MovingGameObject movingGameObject;

    public Player(GridPoint2 initialCoordinates, int width, int height, ProgressCalculator progressCalculator, ColliderManager colliderManager) {
        movingGameObject = new MovingGameObject(initialCoordinates, width, height, TIME_OF_PASSING_ONE_TILE, progressCalculator, colliderManager);
    }

    public MovingGameObject getMovingGameObject() {
        return movingGameObject;
    }

    public void move(Direction direction, float deltaTime) {
        movingGameObject.move(direction, deltaTime);
    }
}
