package ru.mipt.bit.platformer.game.renderer;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import ru.mipt.bit.platformer.game.CoordinatesCalculator;
import ru.mipt.bit.platformer.game.GameObject;

import java.util.List;

public class LibGdxGameObjectRenderer implements Renderer {
    private final List<GameObject> gameObjects;
    private final LibGdxTextureRenderer libGdxTextureRenderer;

    public LibGdxGameObjectRenderer(TiledMapTileLayer groundLayer, List<GameObject> gameObjects, TextureRegion region, Batch batch, CoordinatesCalculator coordinatesCalculator) {
        libGdxTextureRenderer = new LibGdxTextureRenderer(region, batch);
        this.gameObjects = gameObjects;

        for (GameObject gameObject : gameObjects) {
            coordinatesCalculator.moveRectangleAtTileCenter(groundLayer, gameObject.getRectangle(), gameObject.getGridCoordinates());
        }
    }

    @Override
    public void render() {
        for (GameObject gameObject : gameObjects) {
            libGdxTextureRenderer.drawTextureRegionUnscaled(gameObject.getRectangle(), gameObject.getRotation());
        }
    }
}
