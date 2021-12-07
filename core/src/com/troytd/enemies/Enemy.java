package com.troytd.enemies;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.troytd.game.TroyTD;

/**
 * an enemy with its texture, health and other related properties
 */
public abstract class Enemy {
    protected final static short hp = 100;
    protected final static short speed = 100;
    protected final static short damage = 10;
    protected final byte line;
    protected final TroyTD game;
    protected Sprite enemySprite;
    protected Rectangle form;
    protected Vector2 position;

    /**
     * @param line     the line where the enemy is located, 0 is the top line, 1 is the middle line, 2 is the bottom line
     * @param game     the game instance
     * @param position the position of the enemy
     */
    public Enemy(byte line, final TroyTD game, final Vector2 position) {
        this.line = line;
        this.game = game;
        this.position = position;
    }

    /**
     * @param line the line where the enemy is located, 0 is the top line, 1 is the middle line, 2 is the bottom line
     * @param game the game instance
     */
    public Enemy(byte line, final TroyTD game) {
        this(line, game, new Vector2(0, 0));
    }

    public static void loadAssets() {
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public void dispose() {
        enemySprite.getTexture().dispose();
    }
}
