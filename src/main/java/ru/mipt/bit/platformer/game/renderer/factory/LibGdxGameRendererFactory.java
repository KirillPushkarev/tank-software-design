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
import ru.mipt.bit.platformer.game.renderer.LibGdxGameObjectRenderer;
import ru.mipt.bit.platformer.game.renderer.LibGdxGameRenderer;
import ru.mipt.bit.platformer.game.renderer.LibGdxMovingGameObjectRenderer;
import ru.mipt.bit.platformer.game.renderer.Renderer;
import ru.mipt.bit.platformer.util.MapUtils;

import static ru.mipt.bit.platformer.util.GdxGameUtils.createSingleLayerMapRenderer;

public class LibGdxGameRendererFactory implements GameRendererFactory {
    public Renderer createGameRenderer(TiledMap levelTiledMap,
                                       Level level,
                                       CoordinatesCalculator coordinatesCalculator,
                                       Texture tankTexture,
                                       Texture treeTexture) {
        Batch batch = new SpriteBatch();

        MapRenderer levelRenderer = createSingleLayerMapRenderer(levelTiledMap, batch);
        TiledMapTileLayer groundLayer = MapUtils.getSingleLayer(levelTiledMap);

        var movingGameObjects = level.getTanks();
        movingGameObjects.add(level.getPlayer());
        Renderer tankRenderer = new LibGdxMovingGameObjectRenderer(
                groundLayer,
                movingGameObjects,
                new TextureRegion(tankTexture),
                batch,
                coordinatesCalculator
        );
        Renderer treeRenderer = new LibGdxGameObjectRenderer(groundLayer,
                level.getObstacles(),
                new TextureRegion(treeTexture),
                batch,
                coordinatesCalculator);

        return new LibGdxGameRenderer(levelRenderer, batch, tankRenderer, treeRenderer);
    }
}
