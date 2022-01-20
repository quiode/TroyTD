package com.troytd.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.troytd.game.TroyTD;

public class MessageScreen implements Screen {
    private final Stage stage;
    private final TroyTD game;

    public MessageScreen(TroyTD game, String message, String skin) {
        // variables
        this.game = game;
        // create stage
        OrthographicCamera camera = new OrthographicCamera(game.settingPreference.getInteger("width"),
                                                           game.settingPreference.getInteger("height"));
        FitViewport viewport = new FitViewport(game.settingPreference.getInteger("width"),
                                               game.settingPreference.getInteger("height"), camera);
        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);

        // label
        Label textLabel = new Label(message, game.skin, skin);
        textLabel.setAlignment(1, 2);

        // credits
        Label creditsLabel1 = new Label("Created by", game.skin, "big");
        creditsLabel1.setAlignment(1, 2);
        Label creditsLabel2 = new Label("Dominik Schwaiger - Code", game.skin, "big");
        creditsLabel2.setAlignment(1, 2);
        Label creditsLabel3 = new Label("Jakob Mayr - Grafiken", game.skin, "big");
        creditsLabel3.setAlignment(1, 2);
        Label creditsLabel4 = new Label("Oriane Schweizer - Emotional Support", game.skin, "big");
        creditsLabel4.setAlignment(1, 2);
        Label creditsLabel5 = new Label("Clément Lucas-Hirtz - war auch dabei", game.skin, "big");
        creditsLabel5.setAlignment(1, 2);

        // vertical row
        VerticalGroup verticalGroup = new VerticalGroup();
        verticalGroup.setFillParent(true);
        verticalGroup.center();

        verticalGroup.addActor(textLabel);

        verticalGroup.addActor(new Label("", game.skin, "big"));

        verticalGroup.addActor(creditsLabel1);
        verticalGroup.addActor(creditsLabel2);
        verticalGroup.addActor(creditsLabel3);
        verticalGroup.addActor(creditsLabel4);
        verticalGroup.addActor(creditsLabel5);

        stage.addActor(verticalGroup);

    }

    public MessageScreen(TroyTD game, String message) {
        this(game, message, "big");
    }

    /**
     * Called when this screen becomes the current screen for a {@link com.badlogic.gdx.Game}.
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
        // close on keypress
        if (Gdx.input.isKeyPressed(Input.Keys.ANY_KEY)) {
            Gdx.app.exit();
        }

        ScreenUtils.clear(game.BACKGROUND_COLOR);

        stage.act(delta);
        stage.draw();
    }

    /**
     * @param width
     * @param height
     * @see com.badlogic.gdx.ApplicationListener#resize(int, int)
     */
    @Override
    public void resize(int width, int height) {

    }

    /**
     * @see com.badlogic.gdx.ApplicationListener#pause()
     */
    @Override
    public void pause() {

    }

    /**
     * @see com.badlogic.gdx.ApplicationListener#resume()
     */
    @Override
    public void resume() {

    }

    /**
     * Called when this screen is no longer the current screen for a {@link com.badlogic.gdx.Game}.
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
