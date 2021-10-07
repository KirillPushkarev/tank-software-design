package ru.mipt.bit.platformer.game.renderer;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import ru.mipt.bit.platformer.game.CoordinatesCalculator;
import ru.mipt.bit.platformer.game.GameObject;

public class LibGdxGameObjectRenderer extends AbstractLibGdxRenderer {
    private final GameObject gameObject;

    public LibGdxGameObjectRenderer(TiledMapTileLayer groundLayer, GameObject gameObject, TextureRegion region, Batch batch, CoordinatesCalculator coordinatesCalculator) {
        super(region, batch);
        this.gameObject = gameObject;

        coordinatesCalculator.moveRectangleAtTileCenter(groundLayer, gameObject.getRectangle(), gameObject.getGridCoordinates());
    }

    @Override
    public void render() {
        drawTextureRegionUnscaled(gameObject.getRectangle(), gameObject.getRotation());
    }
}
