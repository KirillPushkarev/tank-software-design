package ru.mipt.bit.platformer.game.renderer.factory;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import ru.mipt.bit.platformer.game.CoordinatesCalculator;
import ru.mipt.bit.platformer.game.entity.Level;
import ru.mipt.bit.platformer.game.renderer.Renderer;

public interface GameRendererFactory {
    Renderer createGameRenderer(TiledMap levelTiledMap, Level level, CoordinatesCalculator coordinatesCalculator, Texture playerTexture, Texture treeTexture, Texture bulletTexture);
}
