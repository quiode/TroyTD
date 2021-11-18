package com.troytd.screens;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.utils.ScreenUtils;
import com.troytd.game.TroyTD;

public class PauseScreen implements Screen {

    private final TroyTD game;
    private final GameScreen gameScreen;
    private final GlyphLayout glyphLayout;
    private OrthographicCamera camera;
    private long screenSwitchDelta = -1;

    public PauseScreen(final TroyTD game, final GameScreen gameScreen) {
        this.game = game;
        this.gameScreen = gameScreen;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

        glyphLayout = new GlyphLayout(game.font, "Paused...");

        screenSwitchDelta = System.currentTimeMillis();
    }

    /**
     * Called when this screen becomes the current screen for a {@link Game}.
     */
    @Override
    public void show() {
        screenSwitchDelta = System.currentTimeMillis();
    }

    /**
     * Called when the screen should render itself.
     *
     * @param delta The time in seconds since the last render.
     */
    @Override
    public void render(float delta) {

        ScreenUtils.clear(0, 0, 0, 1);

        game.batch.begin();
        game.font.draw(game.batch, glyphLayout, 800f / 2 - glyphLayout.width / 2, 480f / 2);
        game.batch.end();

        if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.ESCAPE) && screenSwitchDelta < System.currentTimeMillis() - 100) {
            game.setScreen(gameScreen);
            gameScreen.resume();
            dispose();
        }

    }

    /**
     * @param width
     * @param height
     * @see ApplicationListener#resize(int, int)
     */
    @Override
    public void resize(int width, int height) {

    }

    /**
     * @see ApplicationListener#pause()
     */
    @Override
    public void pause() {
        hide();
    }

    /**
     * @see ApplicationListener#resume()
     */
    @Override
    public void resume() {
        show();
    }

    /**
     * Called when this screen is no longer the current screen for a {@link Game}.
     */
    @Override
    public void hide() {
        screenSwitchDelta = -1;
    }

    /**
     * Called when this screen should release all resources.
     */
    @Override
    public void dispose() {
    }
}
