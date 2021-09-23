package ru.mipt.bit.platformer.game;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;

public class GameObject {
    private final GridPoint2 gridCoordinates;
    private float rotation = 0f;

    private final TextureRegion graphics;
    private final Rectangle rectangle;

    public GameObject(GridPoint2 gridCoordinates, TextureRegion graphics, Rectangle rectangle) {
        this.gridCoordinates = gridCoordinates;
        this.graphics = graphics;
        this.rectangle = rectangle;
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

    public TextureRegion getGraphics() {
        return graphics;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public boolean hasCollisionInDirection(GridPoint2 otherCoordinates, Direction direction) {
        return getGridCoordinates().equals(direction.getNextCoordinates(otherCoordinates));
    }
}
