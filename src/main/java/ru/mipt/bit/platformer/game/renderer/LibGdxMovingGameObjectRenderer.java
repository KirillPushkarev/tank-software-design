package ru.mipt.bit.platformer.game.renderer;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import ru.mipt.bit.platformer.game.CoordinatesCalculator;
import ru.mipt.bit.platformer.game.GameObject;
import ru.mipt.bit.platformer.game.MovingGameObject;

import java.util.List;

public class LibGdxMovingGameObjectRenderer implements Renderer {
    private final List<MovingGameObject> movingGameObjects;
    private final CoordinatesCalculator coordinatesCalculator;
    private final LibGdxTextureRenderer libGdxTextureRenderer;

    public LibGdxMovingGameObjectRenderer(TiledMapTileLayer groundLayer, List<MovingGameObject> movingGameObjects, TextureRegion region, Batch batch, CoordinatesCalculator coordinatesCalculator) {
        libGdxTextureRenderer = new LibGdxTextureRenderer(region, batch);
        this.movingGameObjects = movingGameObjects;
        this.coordinatesCalculator = coordinatesCalculator;

        for (GameObject gameObject : movingGameObjects) {
            this.coordinatesCalculator.moveRectangleAtTileCenter(groundLayer, gameObject.getRectangle(), gameObject.getGridCoordinates());
        }
    }

    @Override
    public void render() {
        for (MovingGameObject movingGameObject : movingGameObjects) {
            if (movingGameObject.getDestinationGridCoordinates() != null) {
                coordinatesCalculator.moveGameObjectBetweenTileCenters(movingGameObject, movingGameObject.getDestinationGridCoordinates(), movingGameObject.getMovementProgress());
            }

            libGdxTextureRenderer.drawTextureRegionUnscaled(movingGameObject.getRectangle(), movingGameObject.getRotation());
        }
    }
}
