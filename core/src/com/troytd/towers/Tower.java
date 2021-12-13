package com.troytd.towers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.troytd.game.TroyTD;

public class Tower {
    public final static int size = 100;
    public final static int cost = 100;
    protected final static int damage = 100;
    protected final static int range = 100;
    protected final static int speed = 100;
    protected final static int maxHP = 100;
    protected final Vector2 distortion;
    protected final TowerTypes type;
    protected final TroyTD game;
    private final String name;
    protected int hp = maxHP;
    protected Sprite towerSprite;

    public Tower(final TroyTD game, Vector2 position, Texture texture, final String name, final TowerTypes type,
                 Vector2 distortion) {
        this.game = game;
        this.towerSprite = new Sprite(texture);
        towerSprite.setPosition(position.x, position.y);
        towerSprite.setSize(towerSprite.getWidth() * distortion.x, towerSprite.getHeight() * distortion.y);
        this.name = name;
        this.type = type;
        this.distortion = distortion;

    }

    public void dispose() {
        towerSprite.getTexture().dispose();
    }

    public Rectangle getRect() {
        return towerSprite.getBoundingRectangle();
    }

    public void setPosition(Vector2 position) {
        towerSprite.setPosition(position.x, position.y);
    }

    public void draw() {
        towerSprite.draw(game.batch);
    }

    public void setSize(float width, float height) {
        towerSprite.setSize(width * distortion.x, height * distortion.y);
    }
}
