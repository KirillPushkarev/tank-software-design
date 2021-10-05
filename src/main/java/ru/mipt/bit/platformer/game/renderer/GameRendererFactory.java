package ru.mipt.bit.platformer.game.renderer;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import ru.mipt.bit.platformer.game.CoordinatesCalculator;
import ru.mipt.bit.platformer.game.GameObject;
import ru.mipt.bit.platformer.game.Player;

public interface GameRendererFactory {
    Renderer createGameRenderer(TiledMap levelMap, Player player, GameObject tree, CoordinatesCalculator coordinatesCalculator, Texture playerTexture, Texture treeTexture);
}
