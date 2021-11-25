package ru.mipt.bit.platformer.game.renderer.factory;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import ru.mipt.bit.platformer.game.CoordinatesCalculator;
import ru.mipt.bit.platformer.game.entity.Level;
import ru.mipt.bit.platformer.game.renderer.*;
import ru.mipt.bit.platformer.util.MapUtils;

import static ru.mipt.bit.platformer.util.GdxGameUtils.createSingleLayerMapRenderer;

public class LibGdxGameRendererFactory implements GameRendererFactory {
    public Renderer createGameRenderer(TiledMap levelTiledMap,
                                       Level level,
                                       CoordinatesCalculator coordinatesCalculator,
                                       Texture tankTexture,
                                       Texture treeTexture,
                                       Texture bulletTexture) {
        Batch batch = new SpriteBatch();

        MapRenderer levelRenderer = createSingleLayerMapRenderer(levelTiledMap, batch);
        TiledMapTileLayer groundLayer = MapUtils.getSingleLayer(levelTiledMap);

        LibGdxTankRenderer tankRenderer = new LibGdxTankRenderer(
                groundLayer,
                new TextureRegion(tankTexture),
                batch,
                coordinatesCalculator
        );
        level.addSubscriber(tankRenderer);
        LibGdxObstacleRenderer treeRenderer = new LibGdxObstacleRenderer(
                groundLayer,
                new TextureRegion(treeTexture),
                batch,
                coordinatesCalculator);
        level.addSubscriber(treeRenderer);
        LibGdxBulletRenderer bulletRenderer = new LibGdxBulletRenderer(
                groundLayer,
                new TextureRegion(bulletTexture),
                batch,
                coordinatesCalculator);
        level.addSubscriber(bulletRenderer);

        return new LibGdxGameRenderer(levelRenderer, batch, tankRenderer, treeRenderer, bulletRenderer);
    }
}
