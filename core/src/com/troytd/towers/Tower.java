package com.troytd.towers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.troytd.game.TroyTD;

public class Tower {
    public final static int size = 100;
    protected final static int damage = 100;
    protected final static int range = 100;
    protected final static int speed = 100;
    protected final static int cost = 100;
    protected final static int hp = 100;
    protected final TroyTD game;
    private final String fileName;
    protected Sprite towerSprite;

    public Tower(final TroyTD game, Vector2 position, Texture texture, final String fileName) {
        this.game = game;
        this.towerSprite = new Sprite(texture);
        towerSprite.setPosition(position.x, position.y);
        this.fileName = fileName;
    }

    public void dispose() {
        towerSprite.getTexture().dispose();
    }

    public Rectangle getRect() {
        return towerSprite.getBoundingRectangle();
    }
}
