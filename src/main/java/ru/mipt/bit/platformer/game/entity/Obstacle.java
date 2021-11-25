package ru.mipt.bit.platformer.game.entity;

import com.badlogic.gdx.math.GridPoint2;

public class Obstacle extends GameObject {
    public Obstacle(GridPoint2 gridCoordinates, int width, int height) {
        super(gridCoordinates, width, height);
    }
}
