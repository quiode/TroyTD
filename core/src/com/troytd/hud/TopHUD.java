package com.troytd.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.TimeUtils;
import com.troytd.game.TroyTD;
import com.troytd.screens.GameScreen;

import java.util.Date;

/**
 * HUD for the top of the screen
 * <p>
 * <strong>Top HUD</strong>
 * <i>Asset Manager has to have finished loading to initialize the class</i>
 * <p>A small bar at the top of the screen that displays important information to the player, such as:
 * <ul>
 *     <li>The player's health</li>
 *     <li>The player's money</li>
 *     <li>The current round</li>
 *     <li>The rounds to finish</li>
 *     <li><i>The current time(?)</i></li>
 * </ul>
 * </p>
 * </p>
 */
public class TopHUD {
    public final float height;
    private final GameScreen gameScreen;
    private final TroyTD game;
    private final Table topBar;
    private final Image moneyIcon;
    private final Image healthIcon;
    private final Label moneyLabel;
    private final Label healthLabel;
    private final Label roundLabel;
    private final Label timeLabel;
    private final Label difficultyLabel;
    private final Label killsLabel;
    private final Image killsIcon;

    public TopHUD(final GameScreen gameScreen, final TroyTD game) {
        this.gameScreen = gameScreen;
        this.game = game;
        this.height = game.settingPreference.getInteger("icon-size");

        // create the top bar and set properties
        this.topBar = new Table();
        topBar.setWidth(gameScreen.stage.getWidth());
        topBar.setHeight(height);
        topBar.center();
        topBar.setPosition(0, gameScreen.stage.getHeight() - height);
        topBar.padTop(height * 0.1f);

        // money icon
        moneyIcon = new Image(new TextureRegionDrawable(game.assetManager.get("hud/coin.png", Texture.class)),
                              Scaling.fit);

        // money label
        moneyLabel = new Label("", game.skin) {
            @Override
            public void act(float delta) {
                this.setText(String.valueOf(gameScreen.money));
            }
        };

        // health icon
        healthIcon = new Image(new TextureRegionDrawable(game.assetManager.get("hud/heart.png", Texture.class)),
                               Scaling.fit);

        // health label
        healthLabel = new Label("", game.skin) {
            @Override
            public void act(float delta) {
                this.setText(String.valueOf(gameScreen.health));
            }
        };

        // kills label
        killsLabel = new Label("", game.skin) {
            @Override
            public void act(float delta) {
                this.setText(String.valueOf(gameScreen.kills));
            }
        };

        // kills icon
        killsIcon = new Image(new TextureRegionDrawable(game.assetManager.get("hud/kills.png", Texture.class)),
                              Scaling.fit);

        // round label
        roundLabel = new Label("", game.skin) {
            @Override
            public void act(float delta) {
                this.setText(String.valueOf(gameScreen.getMap().currentWave()) + "/" + gameScreen.getMap().maxWaves());
            }
        };

        // time label
        timeLabel = new Label("", game.skin) {
            @Override
            public void act(float delta) {
                Date date = new Date(TimeUtils.millis());
                this.setText(date.getHours() + ":" + date.getMinutes());
            }
        };

        // difficulty label
        difficultyLabel = new Label("", game.skin) {
            @Override
            public void act(float delta) {
                String difficulty;
                switch (game.settingPreference.getInteger("difficulty")) {
                    case 0:
                        difficulty = "Easy";
                        break;
                    case 1:
                        difficulty = "Normal";
                        break;
                    case 2:
                        difficulty = "Hard";
                        break;
                    default:
                        difficulty = "Normal";
                        game.settingPreference.putInteger("difficulty", 1);
                        game.settingPreference.flush();
                        Gdx.app.log("Error", "Invalid difficulty setting");
                        break;
                }
                this.setText("Difficulty: " + difficulty);
            }
        };

        // add the labels to the top bar
        topBar.add(moneyLabel).padRight(height * 0.1f);
        topBar.add(moneyIcon).maxWidth(height * 0.75f).padRight(height * 0.5f);

        topBar.add(healthLabel).padRight(height * 0.1f);
        topBar.add(healthIcon).maxWidth(height * 0.75f).padRight(height * 0.5f);

        topBar.add(killsLabel).padRight(height * 0.1f);
        topBar.add(killsIcon).maxWidth(height * 0.75f);

        topBar.add(timeLabel).padLeft(height * 0.3f).padRight(height * 0.3f);

        topBar.add(roundLabel);

        topBar.add(difficultyLabel).padLeft(height * 0.3f);

        // Add topBar to the stage
        gameScreen.stage.addActor(topBar);
        gameScreen.stage.setDebugAll(false);

        // safeguard
        loadAssets(game);
    }

    /**
     * <li>adds the assets to the asset manager</li>
     * <li>needs to be called before the class is initialized</li>
     * <li>asset manager has to have finished loading to initialize the class</li>
     *
     * @param game the game instance
     */
    public static void loadAssets(final TroyTD game) {
        game.assetManager.load("hud/coin.png", Texture.class);
        game.assetManager.load("hud/heart.png", Texture.class);
        game.assetManager.load("hud/kills.png", Texture.class);
    }

    /**
     * for cleaning up, removes all actors from the stage and unloads the assets
     */
    public void dispose() {
        gameScreen.stage.getActors().removeValue(topBar, true);
        game.assetManager.unload("hud/coin.png");
        game.assetManager.unload("hud/heart.png");
    }
}
