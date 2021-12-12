package ru.mipt.bit.platformer.game.entity;

import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;

import static ru.mipt.bit.platformer.util.GdxGameUtils.createBoundingRectangle;

public class GameObject {
    protected final GridPoint2 coordinates;
    protected float rotation = 0f;
    protected final Rectangle rectangle;

    public GameObject(GridPoint2 gridCoordinates, int width, int height) {
        this.coordinates = gridCoordinates;
        this.rectangle = createBoundingRectangle(width, height);
    }

    public GridPoint2 getCoordinates() {
        return coordinates;
    }

    public float getRotation() {
        return rotation;
    }

    public void rotate(float rotation) {
        this.rotation = rotation;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public boolean holdsPosition(GridPoint2 position) {
        return coordinates.equals(position);
    }

    public Direction getLastDirection() {
        return Direction.NONE;
    }

    public boolean isMoving() {
        return false;
    }

    public void move(Direction direction) {
    }

    public void liveTimePeriod(float deltaTime) {
    }

    public void shoot() {
    }
}
