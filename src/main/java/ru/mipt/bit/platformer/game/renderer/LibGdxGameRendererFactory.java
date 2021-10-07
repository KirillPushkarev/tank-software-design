package ru.mipt.bit.platformer.game.renderer;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import ru.mipt.bit.platformer.game.CoordinatesCalculator;
import ru.mipt.bit.platformer.game.GameObject;
import ru.mipt.bit.platformer.game.Player;
import ru.mipt.bit.platformer.util.MapUtils;

import static ru.mipt.bit.platformer.util.GdxGameUtils.createSingleLayerMapRenderer;

public class LibGdxGameRendererFactory implements GameRendererFactory {
    public Renderer createGameRenderer(TiledMap levelMap, Player player, GameObject tree, CoordinatesCalculator coordinatesCalculator, Texture playerTexture, Texture treeTexture) {
        Batch batch = new SpriteBatch();

        MapRenderer levelRenderer = createSingleLayerMapRenderer(levelMap, batch);
        TiledMapTileLayer groundLayer = MapUtils.getSingleLayer(levelMap);
        Renderer playerRenderer = new LibGdxPlayerRenderer(groundLayer, player, new TextureRegion(playerTexture), batch, coordinatesCalculator);
        Renderer treeRenderer = new LibGdxGameObjectRenderer(groundLayer, tree, new TextureRegion(treeTexture), batch, coordinatesCalculator);

        return new LibGdxGameRenderer(levelRenderer, batch, playerRenderer, treeRenderer);
    }
}
