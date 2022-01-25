package com.troytd.screens;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.ReflectionException;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.troytd.game.TroyTD;
import com.troytd.hud.InfoTowerHUD;
import com.troytd.hud.PlaceTowerHUD;
import com.troytd.hud.SideHUD;
import com.troytd.hud.TopHUD;
import com.troytd.maps.DebugMap;
import com.troytd.maps.Map;
import com.troytd.maps.TowerPlace;
import com.troytd.towers.shots.Shot;

import java.util.ArrayList;

public class GameScreen implements Screen {
    // settings Icon
    public final ImageButton pauseButton;
    // stage for this screen
    public final Stage stage;
    private final TroyTD game;
    private final Container<ImageButton> container;
    // camera and viewport
    private final Viewport viewport;
    private final Camera camera;
    // assets
    private final Map map;
    /**
     * list of all singleShots
     */
    private final ArrayList<Shot> shots = new ArrayList<>();
    // stats
    public int money = 200;
    public short kills;
    public short health;
    public TowerPlace selectedTowerPlace = null;
    public SideHUD currentlyOpenHUD;
    // HUDs
    private PlaceTowerHUD placeTowerHUD;
    private InfoTowerHUD infoTowerHUD;
    private TopHUD topHUD;
    // time when this screen was switched to
    private long screenSwitchDelta = -1;

    public GameScreen(final TroyTD game, final Class<? extends Map> map) {
        this.game = game;
        health = (short) (200 + 50 * game.settingPreference.getInteger("difficulty", 1));
        Map temp1;
        try {
            temp1 = (Map) ClassReflection.getConstructor(map, TroyTD.class, ArrayList.class).newInstance(game, shots);
        } catch (ReflectionException e) {
            e.printStackTrace();
            temp1 = new DebugMap(game, shots);

        }
        this.map = temp1;
        money += (short) (money * game.settingPreference.getInteger("difficulty", 0));

        // create stage
        camera = new OrthographicCamera(game.settingPreference.getInteger("width"),
                                        game.settingPreference.getInteger("height"));
        viewport = new FitViewport(game.settingPreference.getInteger("width"),
                                   game.settingPreference.getInteger("height"), camera);
        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);
        // settings icon
        pauseButton = new ImageButton(game.skin, "toSettings");
        pauseButton.setSize(game.settingPreference.getInteger("icon-size"),
                            game.settingPreference.getInteger("icon-size"));

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

        container = new Container<ImageButton>(pauseButton).width(pauseButton.getWidth())
                .height(pauseButton.getHeight());
        stage.addActor(container);
        container.setPosition(viewport.getWorldWidth() - pauseButton.getWidth() + 20,
                              viewport.getWorldHeight() - pauseButton.getHeight() + 20);

        // wait before switching screens
        screenSwitchDelta = System.currentTimeMillis();

        // HUD
        TopHUD.loadAssets(game);
        InfoTowerHUD.loadAssets(game);
        PlaceTowerHUD.loadAssets(game);
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
        } else if (topHUD == null || infoTowerHUD == null || placeTowerHUD == null) {
            if (topHUD == null) topHUD = new TopHUD(this, game);
            if (infoTowerHUD == null) infoTowerHUD = new InfoTowerHUD(game, stage, map, topHUD.height, this);
            if (placeTowerHUD == null) placeTowerHUD = new PlaceTowerHUD(game, stage, topHUD.height, map, this);
        }
    }

    /**
     * Called when the screen should render itself.
     *
     * @param delta The time in seconds since the last render.
     */
    @Override
    public void render(float delta) {
        if (health <= 0) map.lost = true;

        ScreenUtils.clear(game.BACKGROUND_COLOR);
        stage.act(delta);


        if (Gdx.input.isTouched()) {
            TowerPlace selectedTowerPlace = map.checkTowerClick(
                    new Vector2(Gdx.input.getX(), viewport.getWorldHeight() - Gdx.input.getY()));
            /*
            Gdx.app.log("Touched", "X: " + Gdx.input.getX() + " Y: " + (viewport.getWorldHeight() - Gdx.input.getY
             ()));
            */
            if (selectedTowerPlace != null) {
                if (selectedTowerPlace.getTower() == null && currentlyOpenHUD == null) {
                    placeTowerHUD.show(selectedTowerPlace);
                    currentlyOpenHUD = placeTowerHUD;
                } else if (currentlyOpenHUD == null) {
                    infoTowerHUD.show(selectedTowerPlace);
                    currentlyOpenHUD = infoTowerHUD;
                }
            }
        }

        infoTowerHUD.update();

        game.batch.begin();
        // Draw map
        map.draw(game.batch, this, delta, stage);

        if (selectedTowerPlace != null) {
            drawHomeLocationPointer();
            checkHomeLocationPointer();
        }

        if (currentlyOpenHUD == infoTowerHUD) {
            drawHomeLocation();
        }
        game.batch.end();
        // Draw HUD
        stage.draw();

        if (Gdx.input.isKeyPressed(
                com.badlogic.gdx.Input.Keys.ESCAPE) && screenSwitchDelta < System.currentTimeMillis() - 100) {
            game.setScreen(new PauseScreen(game, this));
        }

        if (map.lost) {
            game.setScreen(new MessageScreen(game, "You lost!", "error"));
            this.dispose();
        } else if (map.won) {
            game.setScreen(new MessageScreen(game, "You won!", "good"));
            this.dispose();
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
        infoTowerHUD.dispose();
        placeTowerHUD.dispose();
    }

    public Map getMap() {
        return map;
    }

    public void drawHomeLocationPointer() {
        game.shapeDrawer.setColor(Color.RED);
        game.shapeDrawer.filledCircle(new Vector2(Gdx.input.getX() - game.settingPreference.getInteger("width") / 2f,
                                                  game.settingPreference.getInteger("height") / 2f - Gdx.input.getY()),
                                      game.settingPreference.getInteger("width") / 100f);
    }

    public void checkHomeLocationPointer() {
        if (Gdx.input.isTouched()) {
            selectedTowerPlace.getTower()
                    .setHomeLocation(new Vector2(Gdx.input.getX() - game.settingPreference.getInteger("width") / 2f,
                                                 game.settingPreference.getInteger("height") / 2f - Gdx.input.getY()));
            selectedTowerPlace = null;
        }
    }

    public void drawHomeLocation() {
        if (currentlyOpenHUD.getTowerPlace().getTower().getHomeLocation() != null) {
            game.shapeDrawer.setColor(Color.RED);
            game.shapeDrawer.filledCircle(currentlyOpenHUD.getTowerPlace().getTower().getHomeLocation(),
                                          game.settingPreference.getInteger("width") / 100f);
            drawHomeLocationRadius();
        }
    }

    public void drawHomeLocationRadius() {
        game.shapeDrawer.setColor(0.3f, 0.3f, 0.3f, 0.25f);
        game.shapeDrawer.filledCircle(currentlyOpenHUD.getTowerPlace().getTower().getHomeLocation(),
                                      (int) currentlyOpenHUD.getTowerPlace().getTower().getStat("range3").getValue());
    }
}
