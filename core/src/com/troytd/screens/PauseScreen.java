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
import com.troytd.game.TroyTD;

public class PauseScreen implements Screen {

    private final TroyTD game;
    private final GameScreen gameScreen;
    private final OrthographicCamera camera;
    private final FitViewport viewport;
    private final Stage stage;
    private final VerticalGroup table;
    private final TextButton SettingsLabel;
    private final TextButton ResumeLabel;
    private final TextButton ExitLabel;
    private long screenSwitchDelta = -1;

    public PauseScreen(final TroyTD game, final GameScreen gameScreen) {
        this.game = game;
        this.gameScreen = gameScreen;

        camera = new OrthographicCamera(game.settingPreference.getInteger("width"), game.settingPreference.getInteger("height"));
        viewport = new FitViewport(game.settingPreference.getInteger("width"), game.settingPreference.getInteger("height"), camera);
        this.stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);


        screenSwitchDelta = System.currentTimeMillis();

        // Table
        table = new VerticalGroup();
        table.setFillParent(true);
        table.center();

        stage.addActor(table);

        // Buttons
        ResumeLabel = new TextButton("Resume", game.skin);
        ResumeLabel.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(gameScreen);
                dispose();
            }
        });
        table.addActor(ResumeLabel);

        SettingsLabel = new TextButton("Settings", game.skin);
        SettingsLabel.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new SettingsScreen(game, PauseScreen.this));
            }
        });
        table.addActor(SettingsLabel);

        ExitLabel = new TextButton("Exit", game.skin);
        ExitLabel.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });
        table.addActor(ExitLabel);
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
        if (!game.assetManager.isFinished()) game.setScreen(new LoadingScreen(game, this));

        ScreenUtils.clear(game.BACKGROUND_COLOR);

        stage.act(delta);
        stage.draw();

        if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.ESCAPE) && screenSwitchDelta < System.currentTimeMillis() - 100) {
            game.setScreen(gameScreen);
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
        viewport.update(width, height);
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
