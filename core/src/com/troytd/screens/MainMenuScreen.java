package com.troytd.screens;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.troytd.game.TroyTD;

public class MainMenuScreen implements Screen {

    final TroyTD game;
    final Viewport viewport;
    final OrthographicCamera camera;

    // UI
    final Stage stage;
    final VerticalGroup menuGroup;
    final TextButton toSettingsButton;
    final TextButton toGameButton;

    public MainMenuScreen(final TroyTD game) {
        this.game = game;

        camera = new OrthographicCamera(game.settingPreference.getInteger("width"),
                                        game.settingPreference.getInteger("height"));
        viewport = new FitViewport(game.settingPreference.getInteger("width"),
                                   game.settingPreference.getInteger("height"), camera);

        // UI
        // initialize
        stage = new Stage(viewport);
        menuGroup = new VerticalGroup();
        toGameButton = new TextButton("Play", game.skin);
        toSettingsButton = new TextButton("Settings", game.skin);
        TextButton exitButton = new TextButton("Exit", game.skin);

        // positioning
        menuGroup.center();

        menuGroup.setPosition(viewport.getWorldWidth() / 2f, viewport.getWorldHeight() / 2f);

        toGameButton.padBottom(50);

        menuGroup.addActor(toGameButton);
        menuGroup.addActor(toSettingsButton);
        menuGroup.addActor(exitButton);

        stage.addActor(menuGroup);

        // listeners
        toGameButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new ChooseMapScreen(game));
            }
        });

        toSettingsButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new SettingsScreen(game, MainMenuScreen.this));
            }
        });
        exitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });

        Gdx.input.setInputProcessor(stage);
    }

    /**
     * Called when this screen becomes the current screen for a {@link Game}.
     */
    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    /**
     * Called when the screen should render itself.
     *
     * @param delta The time in seconds since the last render.
     */
    @Override
    public void render(float delta) {
        if (!game.assetManager.isFinished()) game.setScreen(new LoadingScreen(game, this));

        ScreenUtils.clear(game.BACKGROUND_COLOR);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        stage.act();

        game.batch.begin();
        stage.draw();
        game.batch.end();

    }

    /**
     * @param width  new width
     * @param height new height
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

    }

    /**
     * Called when this screen should release all resources.
     */
    @Override
    public void dispose() {
        stage.dispose();
    }
}