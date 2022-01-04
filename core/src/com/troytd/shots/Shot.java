package com.troytd.shots;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.troytd.enemies.Enemy;
import com.troytd.game.TroyTD;
import com.troytd.towers.Tower;

/**
 * shot class which represents a single shot
 */
public abstract class Shot {
    /**
     * tower the shot was shot from
     */
    private final Tower tower;
    /**
     * the sprite of the shot
     */
    private final Sprite sprite;
    /**
     * game instance
     */
    private final TroyTD game;
    /**
     * the damage the shot does
     */
    private int damage;
    /**
     * the speed of the shot
     */
    private int speed;

    public Shot(TroyTD game, Tower tower, Texture texture) {
        this.tower = tower;
        this.game = game;
        try {
            this.damage = tower.getClass().getField("damage").getInt(null);
            this.speed = tower.getClass().getField("speed").getInt(null);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        sprite = new Sprite(texture);
        sprite.setPosition(tower.getPosition().x, tower.getPosition().y);
    }

    /**
     * @param enemy the enemy the shot is shot at
     * @return true if the enemy was hit
     */
    public boolean hitDetection(Enemy enemy) {
        return enemy.getRectangle().overlaps(sprite.getBoundingRectangle());
    }

    /**
     * updates the position of the shot
     *
     * @param delta the time since the last update
     */
    public void update(float delta) {
        sprite.translate(speed * delta, speed * delta);
    }

    /**
     * draws the shot
     */
    public void draw() {
        sprite.draw(game.batch);
    }
}
