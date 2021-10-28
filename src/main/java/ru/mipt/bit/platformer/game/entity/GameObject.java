package ru.mipt.bit.platformer.game.entity;

import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;

import static ru.mipt.bit.platformer.util.GdxGameUtils.createBoundingRectangle;

public class GameObject {
    protected final GridPoint2 gridCoordinates;
    protected float rotation = 0f;
    protected final Rectangle rectangle;

    public GameObject(GridPoint2 gridCoordinates, int width, int height) {
        this.gridCoordinates = gridCoordinates;
        this.rectangle = createBoundingRectangle(width, height);
    }

    public GridPoint2 getGridCoordinates() {
        return gridCoordinates;
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
        return gridCoordinates.equals(position);
    }

    public Direction getLastDirection() {
        return Direction.NONE;
    }

    public boolean isMoving() {
        return false;
    }

    public void move(Direction direction, float deltaTime) {
    }
}
