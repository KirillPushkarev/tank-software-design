package ru.mipt.bit.platformer.game.renderer;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import ru.mipt.bit.platformer.game.CoordinatesCalculator;
import ru.mipt.bit.platformer.game.Level;
import ru.mipt.bit.platformer.util.MapUtils;

import static ru.mipt.bit.platformer.util.GdxGameUtils.createSingleLayerMapRenderer;

public class LibGdxGameRendererFactory implements GameRendererFactory {
    public Renderer createGameRenderer(TiledMap levelTiledMap, Level level, CoordinatesCalculator coordinatesCalculator, Texture playerTexture, Texture treeTexture) {
        Batch batch = new SpriteBatch();

        MapRenderer levelRenderer = createSingleLayerMapRenderer(levelTiledMap, batch);
        TiledMapTileLayer groundLayer = MapUtils.getSingleLayer(levelTiledMap);
        Renderer playerRenderer = new LibGdxPlayerRenderer(groundLayer, level.getPlayer(), new TextureRegion(playerTexture), batch, coordinatesCalculator);
        Renderer treeRenderer = new LibGdxGameObjectBatchRenderer(groundLayer, level.getObstacles(), new TextureRegion(treeTexture), batch, coordinatesCalculator);

        return new LibGdxGameRenderer(levelRenderer, batch, playerRenderer, treeRenderer);
    }
}
