package ru.mipt.bit.platformer.game;

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

    public boolean hasCollisionInDirection(GridPoint2 otherCoordinates, Direction direction) {
        return getGridCoordinates().equals(direction.getNextCoordinates(otherCoordinates));
    }
}
