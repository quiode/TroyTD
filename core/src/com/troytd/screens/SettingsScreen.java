package com.troytd.screens;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
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
    private final Screen lastScreen;

    // UI Elements
    private final ImageButton backToGameButton;

    private final Label screenResolutionLabel;
    private final TextField screenResolutionTextField1;
    private final TextField screenResolutionTextField2;
    private final Label screenResolutionLabel2;
    private final TextButton submitButton;

    public SettingsScreen(final TroyTD game, final Screen lastScreen) {
        this.game = game;
        this.lastScreen = lastScreen;

        camera = new OrthographicCamera(game.settingPreference.getInteger("width"), game.settingPreference.getInteger("height"));
        viewport = new FitViewport(game.settingPreference.getInteger("width"), game.settingPreference.getInteger("height"), camera);
        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);
        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        // backToGameButton
        backToGameButton = new ImageButton(game.skin, "backToGame");
        backToGameButton.setSize(backToGameButton.getWidth() * 0.25f, backToGameButton.getHeight() * 0.25f);

        backToGameButton.addListener(new ChangeListener() {
            /**
             * @param event The Event that triggered this listener.
             * @param actor The event target, which is the actor that emitted the change event.
             */
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(lastScreen);
                dispose();
            }
        });

        // Table
        table.add(backToGameButton).width(game.settingPreference.getInteger("icon-size")).height(game.settingPreference.getInteger("icon-size")).pad(10).expandX().left().colspan(4);
        table.top();

        // UI Elements
        screenResolutionLabel = new Label("Screen Resolution:", game.skin, "settings");
        screenResolutionTextField1 = new TextField(String.valueOf(game.settingPreference.getInteger("width")), game.skin, "settings");
        /*
        screenResolutionTextField1.setOnscreenKeyboard(new TextField.OnscreenKeyboard() {
            @Override
            public void show(boolean visible) {
            }
        });
        */
        screenResolutionTextField1.setTextFieldFilter(new TextField.TextFieldFilter.DigitsOnlyFilter());
        screenResolutionTextField1.addListener(new ChangeListener() {
            /**
             * @param event The Event that triggered this listener.
             * @param actor The event target, which is the actor that emitted the change event.
             */
            @Override
            public void changed(ChangeEvent event, Actor actor) {

            }
        });
        screenResolutionTextField1.setAlignment(Align.right);
        screenResolutionLabel2 = new Label("x", game.skin, "settings");
        screenResolutionTextField2 = new TextField(String.valueOf(game.settingPreference.getInteger("height")), game.skin, "settings");
        screenResolutionTextField2.setTextFieldFilter(new TextField.TextFieldFilter.DigitsOnlyFilter());
        screenResolutionTextField2.setAlignment(Align.left);
        submitButton = new TextButton("Submit", game.skin, "settings");
        table.row();
        table.add(screenResolutionLabel);
        table.add(screenResolutionTextField1).right();
        table.add(screenResolutionLabel2);
        table.add(screenResolutionTextField2).left();
        table.row();
        table.add(submitButton).colspan(4).pad(10).center();

        stage.setDebugAll(true);
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
        ScreenUtils.clear(game.BACKGROUND_COLOR);
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

    private void putData() {
        game.settingPreference.putInteger("width", Integer.parseInt(screenResolutionTextField1.getText()));
        game.settingPreference.putInteger("height", Integer.parseInt(screenResolutionTextField2.getText()));
        game.settingPreference.flush();

        Gdx.graphics.setWindowedMode(game.settingPreference.getInteger("width"), game.settingPreference.getInteger("height"));
    }
}
