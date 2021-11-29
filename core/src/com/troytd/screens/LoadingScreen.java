package com.troytd.screens;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.troytd.game.TroyTD;
import com.troytd.helpers.Loadable;
import com.troytd.maps.Map;

/**
 * Displays a loading screen with a progress bar for the asset manager.
 */
public class LoadingScreen implements Screen {

    private final TroyTD game;
    private final Screen calledScreen;

    // Camera and stuff
    private final OrthographicCamera camera;
    private final FitViewport viewport;
    private final Stage stage;
    private final ProgressBar loadingBar;

    // Map
    private Loadable afterLoadObject = null;


    /**
     * Constructor for the LoadingScreen
     *
     * @param game         game instance
     * @param calledScreen the screen to go to after loading is complete
     * @param loadingText  the text to display while loading (loadingText2: loadingText...)
     * @param loadingText2 the text before the loadingText (loadingText2: loadingText...)
     */
    public LoadingScreen(final TroyTD game, final Screen calledScreen, final String loadingText, final String loadingText2) {
        this.game = game;
        this.calledScreen = calledScreen;

        // Camera and stuff
        camera = new OrthographicCamera(game.settingPreference.getInteger("width"), game.settingPreference.getInteger("height"));
        game.batch.setProjectionMatrix(camera.combined);
        viewport = new FitViewport(game.settingPreference.getInteger("width"), game.settingPreference.getInteger("height"), camera);
        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);

        // UI
        // UI
        VerticalGroup verticalGroup = new VerticalGroup();
        verticalGroup.center();
        stage.addActor(verticalGroup);

        Label loadingLabel = new Label(loadingText2 + loadingText, game.skin);
        verticalGroup.addActor(loadingLabel);

        loadingBar = new ProgressBar(0, 100, 1, false, game.skin);
        verticalGroup.addActor(loadingBar);
    }

    public LoadingScreen(final TroyTD game, final Screen calledScreen, final String loadingText) {
        this(game, calledScreen, loadingText, "Loading: ");
    }

    public LoadingScreen(final TroyTD game, final Screen calledScreen) {
        this(game, calledScreen, "Loading...", "");
    }

    public LoadingScreen(final TroyTD game, final Screen calledScreen, final String loadingText, final String loadingText2, final Loadable afterLoadObject) {
        this(game, calledScreen, loadingText, loadingText2);
        this.afterLoadObject = afterLoadObject;
    }

    public LoadingScreen(final TroyTD game, final Screen calledScreen, final Map afterLoadMap) {
        this(game, calledScreen);
        afterLoadObject = afterLoadMap;
    }

    public LoadingScreen(final TroyTD game, final Screen calledScreen, final String loadingText, final Map afterLoadMap) {
        this(game, calledScreen, loadingText);
        afterLoadObject = afterLoadMap;
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
        ScreenUtils.clear(game.BACKGROUND_COLOR);
        camera.update();

        // Update the loading bar
        loadingBar.setValue(game.assetManager.getProgress() * 100);

        // Draw the stage
        stage.act(delta);
        game.batch.begin();
        stage.draw();
        game.batch.end();

        // If the loading is complete, go to the menu screen
        if (game.assetManager.update() && game.assetManager.isFinished()) {
            game.setScreen(calledScreen);
            if (afterLoadObject != null) {
                afterLoadObject.afterLoad();
            }
            dispose();
        }
    }

    /**
     * @param width  width of new window
     * @param height height of new window
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
