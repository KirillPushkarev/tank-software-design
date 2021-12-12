package ru.mipt.bit.platformer.game;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import ru.mipt.bit.platformer.game.entity.GameObject;

public class CoordinatesCalculator {
    private final TiledMapTileLayer tileLayer;
    private final Interpolation interpolation;

    public CoordinatesCalculator(TiledMapTileLayer tileLayer, Interpolation interpolation) {
        this.tileLayer = tileLayer;
        this.interpolation = interpolation;
    }

    public void moveRectangleAtTileCenter(TiledMapTileLayer tileLayer, Rectangle rectangle, GridPoint2 tileCoordinates) {
        Vector2 tileCenter = calculateTileCenter(tileLayer, tileCoordinates);
        rectangle.setCenter(tileCenter);
    }

    public void moveGameObjectBetweenTileCenters(GameObject gameObject, GridPoint2 toTileCoordinates, float progress) {
        Rectangle rectangle = gameObject.getRectangle();
        moveRectangleAtTileCenter(tileLayer, rectangle, gameObject.getCoordinates());
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

    private Vector2 calculateTileCenter(TiledMapTileLayer tileLayer, GridPoint2 tileCoordinates) {
        int tileWidth = tileLayer.getTileWidth();
        int tileHeight = tileLayer.getTileHeight();
        int tileBottomLeftCornerX = tileCoordinates.x * tileWidth;
        int tileBottomLeftCornerY = tileCoordinates.y * tileHeight;

        return new Rectangle()
                .setX(tileBottomLeftCornerX)
                .setY(tileBottomLeftCornerY)
                .setWidth(tileWidth)
                .setHeight(tileHeight)
                .getCenter(new Vector2());
    }
}
