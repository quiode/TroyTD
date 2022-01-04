package com.troytd.shots;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.troytd.enemies.Enemy;
import com.troytd.towers.Tower;

/**
 * shot class which represents a single shot
 */
public abstract class Shot {
    /**
     * tower the shot was shot from
     */
    private Tower tower;
    /**
     * the damage the shot does
     */
    private int damage;
    /**
     * the sprite of the shot
     */
    private Sprite sprite;
    /**
     * the speed of the shot
     */
    private int speed;

    public Shot() {}

    /**
     * @param enemy the enemy the shot is shot at
     * @return true if the enemy was hit
     */
    public boolean hitDetection(Enemy enemy) {
        return enemy.getRectangle().overlaps(sprite.getBoundingRectangle());
    }
}
