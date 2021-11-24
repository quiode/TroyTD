package com.troytd.towers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.troytd.game.TroyTD;

public class Tower {
    protected final static int damage = 100;
    protected final static int range = 100;
    protected final static int speed = 100;
    protected final static int cost = 100;
    protected final static int hp = 100;
    protected final TroyTD game;
    protected Rectangle form;
    protected Texture texture;

    public Tower(TroyTD game) {
        this.game = game;
    }

    public void dispose() {
        texture.dispose();
    }
}
