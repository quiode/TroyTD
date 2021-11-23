package com.troytd.screens;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.troytd.game.TroyTD;

public class GameScreen implements Screen {

    // settings Icon
    public final ImageButton settingsButton;
    private final TroyTD game;
    // stage for this screen
    private final Stage stage;
    private final Container<ImageButton> container;
    // camera and viewport
    private final Viewport viewport;
    private final Camera camera;
    // time when this screen was switched to
    private long screenSwitchDelta = -1;

    public GameScreen(final TroyTD game) {
        this.game = game;

        // create stage
        camera = new OrthographicCamera();
        viewport = new FitViewport(game.settingPreference.getInteger("width"), game.settingPreference.getInteger("height"), camera);
        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);
        // settings icon
        settingsButton = new ImageButton(game.skin, "toSettings");
        settingsButton.setSize(viewport.getWorldWidth() / 15f, viewport.getScreenWidth() / 15f);

        settingsButton.addListener(new ChangeListener() {
            /**
             * @param event
             * @param actor The event target, which is the actor that emitted the change event.
             */
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new SettingsScreen(game, GameScreen.this));
            }
        });

        container = new Container<ImageButton>(settingsButton).width(settingsButton.getWidth()).height(settingsButton.getHeight());
        stage.addActor(container);
        container.setPosition(viewport.getWorldWidth() - settingsButton.getWidth() - 10, viewport.getWorldHeight() - settingsButton.getHeight() - 10);
        container.setSize(settingsButton.getWidth(), settingsButton.getHeight());

        // wait before switching screens
        screenSwitchDelta = System.currentTimeMillis();
    }

    /**
     * Called when this screen becomes the current screen for a {@link Game}.
     */
    @Override
    public void show() {
        screenSwitchDelta = System.currentTimeMillis();
        Gdx.input.setInputProcessor(stage);
    }

    /**
     * Called when the screen should render itself.
     *
     * @param delta The time in seconds since the last render.
     */
    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.2f, 0.2f, 0.2f, 1);
        stage.act(delta);
        game.batch.begin();
        stage.draw();
        game.batch.end();

        if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.ESCAPE) && screenSwitchDelta < System.currentTimeMillis() - 100) {
            game.setScreen(new PauseScreen(game, this));
        }
    }

    /**
     * @param width
     * @param height
     * @see ApplicationListener#resize(int, int)
     */
    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
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
        screenSwitchDelta = -1;
    }

    /**
     * Called when this screen should release all resources.
     */
    @Override
    public void dispose() {
        stage.dispose();
    }
}
