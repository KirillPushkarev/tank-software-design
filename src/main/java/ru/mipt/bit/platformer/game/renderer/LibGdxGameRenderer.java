package ru.mipt.bit.platformer.game.renderer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapRenderer;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;

public class LibGdxGameRenderer implements Renderer {
    private final MapRenderer levelRenderer;
    private final Batch batch;
    private final Renderer playerRenderer;
    private final Renderer treeRenderer;

    public LibGdxGameRenderer(MapRenderer levelRenderer, Batch batch, Renderer playerRenderer, Renderer treeRenderer) {
        this.levelRenderer = levelRenderer;
        this.batch = batch;
        this.playerRenderer = playerRenderer;
        this.treeRenderer = treeRenderer;
    }

    @Override
    public void render() {
        // clear the screen
        Gdx.gl.glClearColor(0f, 0f, 0.2f, 1f);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);

        // render each tile of the level
        levelRenderer.render();

        // start recording all drawing commands
        batch.begin();

        playerRenderer.render();
        treeRenderer.render();

        // submit all drawing requests
        batch.end();
    }
}
