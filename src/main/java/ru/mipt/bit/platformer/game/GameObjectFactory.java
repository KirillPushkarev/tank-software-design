package ru.mipt.bit.platformer.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;

import static ru.mipt.bit.platformer.util.GdxGameUtils.createBoundingRectangle;

public class GameObjectFactory {
    public GameObject createGameObject(Texture texture, GridPoint2 initialCoordinates) {
        // TextureRegion represents Texture portion, there may be many TextureRegion instances of the same Texture
        TextureRegion treeObstacleGraphics = new TextureRegion(texture);
        Rectangle treeObstacleRectangle = createBoundingRectangle(treeObstacleGraphics);
        return new GameObject(new GridPoint2(1, 3), treeObstacleGraphics, treeObstacleRectangle);
    }
}
