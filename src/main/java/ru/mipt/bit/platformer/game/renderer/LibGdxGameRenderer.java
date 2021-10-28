package ru.mipt.bit.platformer.game.renderer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapRenderer;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;

public class LibGdxGameRenderer implements Renderer {
    private final MapRenderer mapRenderer;
    private final Batch batch;
    private final Renderer tankRenderer;
    private final Renderer treeRenderer;

    public LibGdxGameRenderer(MapRenderer levelRenderer, Batch batch, Renderer tankRenderer, Renderer treeRenderer) {
        this.mapRenderer = levelRenderer;
        this.batch = batch;
        this.tankRenderer = tankRenderer;
        this.treeRenderer = treeRenderer;
    }

    @Override
    public void render() {
        // clear the screen
        Gdx.gl.glClearColor(0f, 0f, 0.2f, 1f);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);

        // render each tile of the level
        mapRenderer.render();

        // start recording all drawing commands
        batch.begin();

        tankRenderer.render();
        treeRenderer.render();

        // submit all drawing requests
        batch.end();
    }
}
