package com.troytd.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.utils.ScreenUtils;
import com.troytd.game.TroyTD;

public class MainMenuScreen implements Screen {

    final TroyTD game;
    final String Text1 = "Welcome to TroyTD!!!";
    final String Text2 = "Tap anywhere or press any button to begin!";
    final GlyphLayout glyphLayout1;
    final GlyphLayout glyphLayout2;

    OrthographicCamera camera;

    public MainMenuScreen(TroyTD game) {
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, game.settingPreference.getInteger("width"), game.settingPreference.getInteger("height"));

        glyphLayout1 = new GlyphLayout(game.font, Text1);
        glyphLayout2 = new GlyphLayout(game.font, Text2);
    }

    /**
     * Called when this screen becomes the current screen for a {@link Game}.
     */
    @Override
    public void show() {

    }

    /**
     * Called when the screen should render itself.
     *
     * @param delta The time in seconds since the last render.
     */
    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.2f, 0.2f, 0.2f, 1);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.font.draw(game.batch, glyphLayout1, camera.viewportWidth / 2 - glyphLayout1.width / 2, camera.viewportHeight / 2);
        game.font.draw(game.batch, glyphLayout2, camera.viewportWidth / 2 - glyphLayout2.width / 2, (float) (camera.viewportHeight / 2 - glyphLayout1.height * 1.25));
        game.batch.end();

        if (Gdx.input.isTouched() || Gdx.input.isKeyPressed(Input.Keys.ANY_KEY) || Gdx.input.isButtonPressed(Input.Buttons.LEFT) || Gdx.input.isButtonPressed(Input.Buttons.RIGHT)) {
            game.setScreen(new GameScreen(game));
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

    }

    /**
     * @see ApplicationListener#resume()
     */
    @Override
    public void resume() {

    }

    /**
     * Called when this screen is no longer the current screen for a {@link Game}.
     */
    @Override
    public void hide() {

    }

    /**
     * Called when this screen should release all resources.
     */
    @Override
    public void dispose() {
    }
}