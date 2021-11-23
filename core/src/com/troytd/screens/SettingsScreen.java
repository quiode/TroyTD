package com.troytd.screens;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
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
    private final Screen lastScreen;

    // UI Elements
    private final ImageButton backToGameButton;

    private final Label screenResolutionLabel;
    private final TextField screenResolutionTextField1;
    private final TextField screenResolutionTextField2;
    private final Label screenResolutionLabel2;

    public SettingsScreen(final TroyTD game, final Screen lastScreen) {
        this.game = game;
        this.lastScreen = lastScreen;

        camera = new OrthographicCamera();
        viewport = new FitViewport(game.settingPreference.getInteger("width"), game.settingPreference.getInteger("height"), camera);
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
                game.setScreen(lastScreen);
                thisGame.dispose();
            }
        });

        // Preferences


        // Table
        table.add(backToGameButton).width(backToGameButton.getWidth()).height(backToGameButton.getHeight()).pad(10).expandX().right().colspan(4);
        table.top();

        // UI Elements
        screenResolutionLabel = new Label("Screen Resolution:", game.skin);
        screenResolutionTextField1 = new TextField(String.valueOf(game.settingPreference.getInteger("width")), game.skin);
        screenResolutionLabel2 = new Label("x", game.skin);
        screenResolutionTextField2 = new TextField(String.valueOf(game.settingPreference.getInteger("height")), game.skin);
        table.row();
        table.add(screenResolutionLabel);
        table.add(screenResolutionTextField1).right();
        table.add(screenResolutionLabel2).width(10);
        table.add(screenResolutionTextField2).left();

        table.debug();
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

    private void putData() {
        game.settingPreference.putInteger("width", Integer.parseInt(screenResolutionTextField1.getText()));
        game.settingPreference.putInteger("height", Integer.parseInt(screenResolutionTextField2.getText()));
        game.settingPreference.flush();

        Gdx.graphics.setWindowedMode(game.settingPreference.getInteger("width"), game.settingPreference.getInteger("height"));
    }
}
