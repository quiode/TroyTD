package com.troytd.hud;

import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.troytd.screens.GameScreen;

/**
 * HUD for the top of the screen
 * <p>
 * <strong>Top HUD</strong>
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
public class topHUD {
    private final GameScreen gameScreen;
    private final HorizontalGroup topBar;

    public topHUD(final GameScreen gameScreen) {
        this.gameScreen = gameScreen;
        this.topBar = new HorizontalGroup();
    }
}
