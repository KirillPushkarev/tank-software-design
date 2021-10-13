package ru.mipt.bit.platformer.game.renderer;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import ru.mipt.bit.platformer.game.CoordinatesCalculator;
import ru.mipt.bit.platformer.game.GameObject;

import java.util.List;

public class LibGdxGameObjectBatchRenderer extends AbstractLibGdxRenderer {
    private final List<GameObject> gameObjects;

    public LibGdxGameObjectBatchRenderer(TiledMapTileLayer groundLayer, List<GameObject> gameObjects, TextureRegion region, Batch batch, CoordinatesCalculator coordinatesCalculator) {
        super(region, batch);
        this.gameObjects = gameObjects;

        for (GameObject gameObject : gameObjects) {
            coordinatesCalculator.moveRectangleAtTileCenter(groundLayer, gameObject.getRectangle(), gameObject.getGridCoordinates());
        }
    }

    @Override
    public void render() {
        for (GameObject gameObject : gameObjects) {
            drawTextureRegionUnscaled(gameObject.getRectangle(), gameObject.getRotation());
        }
    }
}
