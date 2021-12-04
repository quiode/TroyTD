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
import com.troytd.hud.TopHUD;
import com.troytd.maps.DefaultMap;
import com.troytd.maps.Map;

public class GameScreen implements Screen {

    // settings Icon
    public final ImageButton pauseButton;
    // stage for this screen
    public final Stage stage;
    public final byte maxRounds;
    private final TroyTD game;
    private final Container<ImageButton> container;
    // camera and viewport
    private final Viewport viewport;
    private final Camera camera;
    // assets
    private final Map map;
    // stats
    public short money;
    public short kills;
    public short health;
    public byte round;
    private TopHUD topHUD;
    // time when this screen was switched to
    private long screenSwitchDelta = -1;

    public GameScreen(final TroyTD game, final Map map) {
        this.game = game;
        this.map = map;
        this.maxRounds = (byte) (map.maxRounds * game.settingPreference.getInteger("difficulty", 1));

        // create stage
        camera = new OrthographicCamera(game.settingPreference.getInteger("width"), game.settingPreference.getInteger("height"));
        viewport = new FitViewport(game.settingPreference.getInteger("width"), game.settingPreference.getInteger("height"), camera);
        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);
        // settings icon
        pauseButton = new ImageButton(game.skin, "toSettings");
        pauseButton.setSize(game.settingPreference.getInteger("icon-size"), game.settingPreference.getInteger("icon-size"));

        pauseButton.addListener(new ChangeListener() {
            /**
             * @param event The Event
             * @param actor The event target, which is the actor that emitted the change event.
             */
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new PauseScreen(game, GameScreen.this));
            }
        });

        container = new Container<ImageButton>(pauseButton).width(pauseButton.getWidth()).height(pauseButton.getHeight());
        stage.addActor(container);
        container.setPosition(viewport.getWorldWidth() - pauseButton.getWidth() + 20,
                viewport.getWorldHeight() - pauseButton.getHeight() + 20);

        // wait before switching screens
        screenSwitchDelta = System.currentTimeMillis();

        // HUD
        TopHUD.loadAssets(game);
    }

    public GameScreen(final TroyTD game) {
        this(game, new DefaultMap(game));
    }

    /**
     * Called when this screen becomes the current screen for a {@link Game}.
     */
    @Override
    public void show() {
        screenSwitchDelta = System.currentTimeMillis();
        Gdx.input.setInputProcessor(stage);
        if (!game.assetManager.isFinished()) {
            game.setScreen(new LoadingScreen(game, this));
        } else if (topHUD == null) {
            topHUD = new TopHUD(this, game);
        }
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
        game.batch.begin();
        // Draw map
        map.draw(game.batch, this);
        game.batch.end();
        // Draw HUD
        stage.draw();

        if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.ESCAPE) && screenSwitchDelta < System.currentTimeMillis() - 100) {
            game.setScreen(new PauseScreen(game, this));
        }
    }

    /**
     * @param width  width of new window
     * @param height height of new window
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
        topHUD.dispose();
    }
}
