package com.troytd.screens;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.troytd.game.TroyTD;

public class SettingsScreen implements Screen {

    private final TroyTD game;
    private final OrthographicCamera camera;
    private final Stage stage;
    private final Table table;
    private final Viewport viewport;
    private final GameScreen gameScreen;

    // UI Elements
    private final ImageButton backToGameButton;


    public SettingsScreen(final TroyTD game, final GameScreen gameScreen) {
        this.game = game;
        this.gameScreen = gameScreen;

        camera = new OrthographicCamera();
        viewport = new FitViewport(800, 480, camera);
        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);
        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        // backToGameButton
        backToGameButton = new ImageButton(game.skin, "backToGame");
        backToGameButton.setSize(backToGameButton.getWidth() * 0.25f, backToGameButton.getHeight() * 0.25f);

        final SettingsScreen thisGame = this;

        backToGameButton.addListener(new ChangeListener() {
            /**
             * @param event
             * @param actor The event target, which is the actor that emitted the change event.
             */
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(gameScreen);
                thisGame.dispose();
            }
        });

        // table
        table.add(backToGameButton).width(backToGameButton.getWidth()).height(backToGameButton.getHeight()).pad(10).expandX().right();
        table.top();
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
        stage.act(delta);
        stage.draw();
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
