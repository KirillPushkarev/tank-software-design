package ru.mipt.bit.platformer.game;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Rectangle;

import static ru.mipt.bit.platformer.util.GdxGameUtils.moveRectangleAtTileCenter;

public class TileMovement {

    private final TiledMapTileLayer tileLayer;
    private final Interpolation interpolation;

    public TileMovement(TiledMapTileLayer tileLayer, Interpolation interpolation) {
        this.tileLayer = tileLayer;
        this.interpolation = interpolation;
    }

    public void moveGameObjectBetweenTileCenters(GameObject gameObject, GridPoint2 toTileCoordinates, float progress) {
        Rectangle rectangle = gameObject.getRectangle();
        moveRectangleAtTileCenter(tileLayer, rectangle, gameObject.getGridCoordinates());
        float fromTileBottomLeftX = rectangle.x;
        float fromTileBottomLeftY = rectangle.y;

        moveRectangleAtTileCenter(tileLayer, rectangle, toTileCoordinates);
        float toTileBottomLeftX = rectangle.x;
        float toTileBottomLeftY = rectangle.y;

        float intermediateBottomLeftX = interpolation.apply(fromTileBottomLeftX, toTileBottomLeftX, progress);
        float intermediateBottomLeftY = interpolation.apply(fromTileBottomLeftY, toTileBottomLeftY, progress);

        rectangle
                .setX(intermediateBottomLeftX)
                .setY(intermediateBottomLeftY);
    }
}
