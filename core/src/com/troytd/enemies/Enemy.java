package com.troytd.enemies;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.troytd.game.TroyTD;

public class Enemy {
    protected final static int hp = 100;
    protected final static int speed = 100;
    protected final static int damage = 10;
    protected final TroyTD game;
    protected Sprite enemySprite;
    protected Rectangle form;

    public Enemy(TroyTD game) {
        this.game = game;
    }

    public void dispose() {
        enemySprite.getTexture().dispose();
    }
}
