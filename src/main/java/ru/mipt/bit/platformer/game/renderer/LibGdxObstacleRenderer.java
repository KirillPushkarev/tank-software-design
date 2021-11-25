package ru.mipt.bit.platformer.game.renderer;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import ru.mipt.bit.platformer.game.CoordinatesCalculator;
import ru.mipt.bit.platformer.game.common.Event;
import ru.mipt.bit.platformer.game.common.EventType;
import ru.mipt.bit.platformer.game.common.Subscriber;
import ru.mipt.bit.platformer.game.entity.GameObject;
import ru.mipt.bit.platformer.game.entity.Obstacle;

import java.util.ArrayList;
import java.util.List;

public class LibGdxObstacleRenderer implements Renderer, Subscriber {
    private final List<Obstacle> gameObjects = new ArrayList<>();
    private final LibGdxTextureRenderer libGdxTextureRenderer;
    private final TiledMapTileLayer groundLayer;
    private final CoordinatesCalculator coordinatesCalculator;

    public LibGdxObstacleRenderer(TiledMapTileLayer groundLayer,
                                  TextureRegion region,
                                  Batch batch,
                                  CoordinatesCalculator coordinatesCalculator) {
        this.groundLayer = groundLayer;
        this.coordinatesCalculator = coordinatesCalculator;
        libGdxTextureRenderer = new LibGdxTextureRenderer(region, batch);
    }

    @Override
    public void render() {
        for (GameObject gameObject : gameObjects) {
            libGdxTextureRenderer.drawTextureRegionUnscaled(gameObject.getRectangle(), gameObject.getRotation());
        }
    }

    @Override
    public void notify(Event event) {
        var gameObject = event.getGameObject();
        if (!(gameObject instanceof Obstacle)) {
            return;
        }

        var obstacle = (Obstacle) event.getGameObject();
        if (event.getEventType() == EventType.ObjectAdded) {
            gameObjects.add(obstacle);
            coordinatesCalculator.moveRectangleAtTileCenter(groundLayer,
                    gameObject.getRectangle(),
                    gameObject.getCoordinates());
        } else if (event.getEventType() == EventType.ObjectRemoved) {
            gameObjects.remove(obstacle);
        }
    }
}
