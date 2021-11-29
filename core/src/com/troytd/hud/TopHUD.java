package com.troytd.hud;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.troytd.game.TroyTD;
import com.troytd.screens.GameScreen;

import java.text.SimpleDateFormat;
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
    private final HorizontalGroup topBar;
    private final SimpleDateFormat formatter;
    private final Image moneyIcon;
    private final Image healthIcon;
    private final Label moneyLabel;
    private final Label healthLabel;
    private final Label roundLabel;
    private final Label timeLabel;

    public TopHUD(final GameScreen gameScreen, final TroyTD game) {
        this.gameScreen = gameScreen;
        this.game = game;
        this.height = gameScreen.stage.getHeight() * 0.05f;

        // create the top bar and set properties
        this.topBar = new HorizontalGroup();
        topBar.top();
        topBar.setWidth(gameScreen.stage.getWidth());
        topBar.setHeight(height);

        // money icon
        moneyIcon = new Image(game.assetManager.get("hud/coin.png", Texture.class));
        moneyIcon.setHeight(height);

        // money label
        moneyLabel = new Label("", game.skin) {
            @Override
            public void act(float delta) {
                this.setText(String.valueOf(gameScreen.money));
            }
        };
        moneyLabel.setHeight(height);

        // health icon
        healthIcon = new Image(game.assetManager.get("hud/heart.png", Texture.class));
        healthIcon.setHeight(height);

        // health label
        healthLabel = new Label("", game.skin) {
            @Override
            public void act(float delta) {
                this.setText(String.valueOf(gameScreen.health));
            }
        };
        healthLabel.setHeight(height);

        // round label
        roundLabel = new Label("", game.skin) {
            @Override
            public void act(float delta) {
                this.setText(String.valueOf(gameScreen.round) + "/" + String.valueOf(gameScreen.maxRounds));
            }
        };
        roundLabel.setHeight(height);

        // time label
        formatter = new SimpleDateFormat("HH:mm");
        this.timeLabel = new Label("", game.skin) {
            @Override
            public void act(float delta) {
                Date date = new Date();
                this.setText(formatter.format(date));
            }
        };
        timeLabel.setHeight(height);

        // add the labels to the top bar
        topBar.addActor(moneyIcon);
        topBar.addActor(moneyLabel);

        topBar.addActor(healthIcon);
        topBar.addActor(healthLabel);

        topBar.addActor(timeLabel);

        topBar.addActor(roundLabel);

        // Add topBar to the stage
        gameScreen.stage.addActor(topBar);

        // safe-guard
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
    }

    /**
     * for cleaning up, removes all actors from the stage
     */
    public void dispose() {
        gameScreen.stage.getActors().removeValue(topBar, true);
        game.assetManager.unload("hud/coin.png");
        game.assetManager.unload("hud/heart.png");
    }
}
