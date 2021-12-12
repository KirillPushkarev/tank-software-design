package ru.mipt.bit.platformer.game.renderer;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import ru.mipt.bit.platformer.game.CoordinatesCalculator;
import ru.mipt.bit.platformer.game.common.Event;
import ru.mipt.bit.platformer.game.common.EventType;
import ru.mipt.bit.platformer.game.common.Subscriber;
import ru.mipt.bit.platformer.game.entity.Bullet;

import java.util.ArrayList;
import java.util.List;

public class LibGdxBulletRenderer implements Renderer, Subscriber {
    private final List<Bullet> gameObjects = new ArrayList<>();
    private final CoordinatesCalculator coordinatesCalculator;
    private final LibGdxTextureRenderer libGdxTextureRenderer;
    private final TiledMapTileLayer groundLayer;

    public LibGdxBulletRenderer(TiledMapTileLayer groundLayer,
                                TextureRegion region,
                                Batch batch,
                                CoordinatesCalculator coordinatesCalculator) {
        this.groundLayer = groundLayer;
        libGdxTextureRenderer = new LibGdxTextureRenderer(region, batch);
        this.coordinatesCalculator = coordinatesCalculator;
    }

    @Override
    public void render() {
        for (Bullet bullet : gameObjects) {
            if (bullet.getDestinationCoordinates() != null) {
                coordinatesCalculator.moveGameObjectBetweenTileCenters(bullet,
                        bullet.getDestinationCoordinates(),
                        bullet.getMovementProgress());
            }

            libGdxTextureRenderer.drawTextureRegionUnscaled(bullet.getRectangle(), bullet.getRotation());
        }
    }

    @Override
    public void notify(Event event) {
        var gameObject = event.getGameObject();
        if (!(gameObject instanceof Bullet)) {
            return;
        }

        var bullet = (Bullet) event.getGameObject();
        if (event.getEventType() == EventType.ObjectAdded) {
            gameObjects.add(bullet);
            this.coordinatesCalculator.moveRectangleAtTileCenter(groundLayer, gameObject.getRectangle(), gameObject.getCoordinates());
        } else if (event.getEventType() == EventType.ObjectRemoved) {
            gameObjects.remove(bullet);
        }
    }
}
