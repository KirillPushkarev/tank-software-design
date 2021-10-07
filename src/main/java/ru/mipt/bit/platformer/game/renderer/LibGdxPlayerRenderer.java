package ru.mipt.bit.platformer.game.renderer;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import ru.mipt.bit.platformer.game.CoordinatesCalculator;
import ru.mipt.bit.platformer.game.Player;

public class LibGdxPlayerRenderer extends AbstractLibGdxRenderer {
    private final Player player;
    private final CoordinatesCalculator coordinatesCalculator;

    public LibGdxPlayerRenderer(TiledMapTileLayer groundLayer, Player player, TextureRegion region, Batch batch, CoordinatesCalculator coordinatesCalculator) {
        super(region, batch);
        this.player = player;
        this.coordinatesCalculator = coordinatesCalculator;

        this.coordinatesCalculator.moveRectangleAtTileCenter(groundLayer, player.getRectangle(), player.getGridCoordinates());
    }

    @Override
    public void render() {
        if (player.getPlayerDestinationGridCoordinates() != null) {
            coordinatesCalculator.moveGameObjectBetweenTileCenters(player, player.getPlayerDestinationGridCoordinates(), player.getPlayerMovementProgress());
        }

        drawTextureRegionUnscaled(player.getRectangle(), player.getRotation());
    }
}
