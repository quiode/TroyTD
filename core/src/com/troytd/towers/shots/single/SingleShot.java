package com.troytd.towers.shots.single;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.troytd.enemies.Enemy;
import com.troytd.game.TroyTD;
import com.troytd.screens.GameScreen;
import com.troytd.towers.Tower;
import com.troytd.towers.shots.Shot;
import com.troytd.towers.shots.ShotType;

import java.util.ArrayList;

/**
 * shot class which represents a single shot
 */
public abstract class SingleShot implements Shot {
    private static final ShotType shotType = ShotType.SINGLE;
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
     * the target of the shot
     */
    private final Enemy target;
    /**
     * the damage the shot does
     */
    private final int damage;
    /**
     * the speed of the shot
     */
    private final int speed;
    Vector2 vectorToTarget;

    public SingleShot(TroyTD game, Tower tower, final Enemy target) {
        this.tower = tower;
        this.game = game;
        this.target = target;

        damage = (int) tower.getStat("damage").getValue();
        speed = (int) tower.getStat("speed").getValue();

        sprite = new Sprite(
                game.assetManager.get("shots/" + tower.getClass().getSimpleName() + "Shot" + ".png", Texture.class));
        sprite.setPosition(tower.getPosition().x + tower.getRect().width / 2f,
                           tower.getPosition().y + tower.getRect().height / 2f);
        float sizeModifier = game.settingPreference.getInteger("width") * 0.0035f / sprite.getWidth();
        sprite.setSize(sprite.getWidth() * sizeModifier, sprite.getHeight() * sizeModifier); // scales
        // the enemy
    }

    /**
     * @return true if the enemy was hit
     */
    public boolean hitDetection() {
        return target.getRectangle().overlaps(sprite.getBoundingRectangle());
    }

    /**
     * updates the position of the shot
     *
     * @param delta the time since the last update
     */
    public void update(float delta, final ArrayList<Shot> shots, final ArrayList<Enemy> enemies,
                       GameScreen gameScreen) {
        if (vectorToTarget == null) vectorToTarget = new Vector2();
        vectorToTarget.set(
                (target.getPosition().x + target.getRectangle().width / 2f) - (sprite.getBoundingRectangle().x + sprite.getBoundingRectangle().width / 2f),
                (target.getPosition().y + target.getRectangle().height / 2f) - (sprite.getBoundingRectangle().y + sprite.getBoundingRectangle().height));


        float tmp_speed = speed * delta; // speed modified to be the same speed regardless of the performance of
        // the computer

        vectorToTarget.scl((float) Math.sqrt(
                1 / ((vectorToTarget.x * vectorToTarget.x + vectorToTarget.y * vectorToTarget.y) / tmp_speed)));

        sprite.translate(vectorToTarget.x, vectorToTarget.y);

        sprite.setOrigin(sprite.getWidth() / 2f, 0);
        sprite.setRotation(vectorToTarget.angleDeg() - 90);

        // if target was hit, deal damage to it and remove the shot
        if (hitDetection()) {
            target.takeDamage(damage, enemies, tower, gameScreen);
            tower.totalDamage += damage;
            shots.remove(this);
        }

        // if the target is dead, remove the shot
        if (target.isDead()) {
            shots.remove(this);
        }
    }

    /**
     * draws the shot
     */
    public void draw() {
        sprite.draw(game.batch);
    }

    public ShotType getShotType() {
        return shotType;
    }
}
