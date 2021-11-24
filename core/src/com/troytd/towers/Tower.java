package com.troytd.towers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.troytd.game.TroyTD;

public class Tower {
    protected final static int damage = 100;
    protected final static int range = 100;
    protected final static int speed = 100;
    protected final static int cost = 100;
    protected final static int hp = 100;
    protected final static int radius = 100;
    protected final TroyTD game;
    protected final Circle form;
    protected Texture texture;

    public Tower(TroyTD game, Vector2 position) {
        this.game = game;
        form = new Circle(position.x, position.y, radius);
    }

    public void dispose() {
        texture.dispose();
    }
}
