package com.troytd.enemies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.troytd.game.TroyTD;

public class Enemy {
    protected final TroyTD game;

    protected final static int hp = 100;
    protected final static int speed = 100;
    protected final static int damage = 10;

    protected Texture texture;
    protected Rectangle form;

    public Enemy(TroyTD game) {
        this.game = game;
    }

    public void dispose() {
        texture.dispose();
    }
}
