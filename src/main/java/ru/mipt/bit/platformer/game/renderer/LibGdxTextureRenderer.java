package ru.mipt.bit.platformer.game.renderer;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class LibGdxTextureRenderer {
    private final TextureRegion region;
    private final Batch batch;

    public LibGdxTextureRenderer(TextureRegion region, Batch batch) {
        this.region = region;
        this.batch = batch;
    }

    protected void drawTextureRegionUnscaled(Rectangle rectangle, float rotation) {
        int regionWidth = region.getRegionWidth();
        int regionHeight = region.getRegionHeight();
        float regionOriginX = regionWidth / 2f;
        float regionOriginY = regionHeight / 2f;

        batch.draw(region, rectangle.x, rectangle.y, regionOriginX, regionOriginY, regionWidth, regionHeight, 1f, 1f, rotation);
    }
}
