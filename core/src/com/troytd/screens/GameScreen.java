package com.troytd.screens;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.troytd.game.TroyTD;

public class GameScreen implements Screen {

    private final TroyTD game;
    // settings Icon
    private final ImageButton settingsButton;
    OrthographicCamera camera;
    // time when this screen was switched to
    private long screenSwitchDelta = -1;
    // stage for this screen
    private final Stage stage;
    private final Table table;
    // camera and viewport
    private final Viewport viewport;
    private final Camera

    public GameScreen(final TroyTD game) {
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        screenSwitchDelta = System.currentTimeMillis();

        // settings icon
        settingsButton = new ImageButton(game.skin, "settings");
        settingsButton.setSize(64, 64);
        settingsButton.setPosition(Gdx.graphics.getWidth() - settingsButton.getWidth(), Gdx.graphics.getHeight());
        settingsButton.addListener(new ClickListener() {
            // TODO: make inputListener work
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("Debugging", "Clicked!");
            }
        });
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
        game.font.draw(game.batch, "Hello World", 100, 100);
        settingsButton.draw(game.batch, 1);
        game.batch.end();

        if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.ESCAPE) && screenSwitchDelta < System.currentTimeMillis() - 100) {
            game.setScreen(new PauseScreen(game, this));
            this.pause();
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
