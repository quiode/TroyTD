package com.troytd.towers.shots.connecting;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.ReflectionException;
import com.troytd.enemies.Enemy;
import com.troytd.game.TroyTD;
import com.troytd.screens.GameScreen;
import com.troytd.towers.Tower;
import com.troytd.towers.shots.Shot;
import com.troytd.towers.shots.ShotType;

import java.util.ArrayList;

public abstract class ConnectingShot implements Shot {
    private static final ShotType shotType = ShotType.CONNECTING;
    /**
     * amount of enemies to connect
     */
    private final long lifeDuration = 500;
    private final long startTime;
    private final Tower tower;
    /**
     * the enemies the shot hits
     */
    private final Enemy[] enemies;
    private final TroyTD game;
    /**
     * the different sprites for connecting the enemies
     */
    Sprite[] sprites;
    Vector2 vectorToTarget;

    public ConnectingShot(TroyTD game, Tower tower, ArrayList<Enemy> enemies, GameScreen gameScreen) {
        int damage = 10;
        this.game = game;
        startTime = System.currentTimeMillis();
        this.tower = tower;

        try {
            damage = (int) ClassReflection.getField(tower.getClass(), "damage").get(null);
        } catch (ReflectionException e) {
            e.printStackTrace();
        }

        short enemyAmount = Tower.enemyAmount;
        try {
            enemyAmount = (short) ClassReflection.getField(tower.getClass(), "enemyAmount").get(null);
        } catch (ReflectionException e) {
            e.printStackTrace();
        }

        this.enemies = Enemy.getClosestN(tower.getPosition(), enemies, enemyAmount);
        this.sprites = new Sprite[enemyAmount];
        float sizeModifier = game.settingPreference.getInteger("width") * 0.0035f / game.assetManager.get(
                "shots/" + tower.getClass().getSimpleName() + "Shot" + ".png", Texture.class).getWidth();
        for (int i = 0; i < enemyAmount; i++) {
            sprites[i] = new Sprite(game.assetManager.get("shots/" + tower.getClass().getSimpleName() + "Shot" + ".png",
                                                          Texture.class));
            sprites[i].setSize(sprites[i].getWidth() * sizeModifier, sprites[i].getHeight() * sizeModifier);
        }

        for (Enemy enemy : this.enemies) {
            if (enemy != null) {
                enemy.takeDamage(damage, enemies, tower, gameScreen);
                tower.totalDamage += damage;
            }
        }
    }

    public ShotType getShotType() {
        return shotType;
    }

    @Override
    public void update(float delta, ArrayList<Shot> shots, ArrayList<Enemy> enemies, GameScreen gameScreen) {
        if (System.currentTimeMillis() - startTime > lifeDuration) {
            shots.remove(this);
            return;
        }

        for (Enemy enemy : enemies) {
            if (enemy == null) {
                shots.remove(this);
                return;
            }
        }

        // resize sprites
        if (vectorToTarget == null) vectorToTarget = new Vector2();
        sprites[0].setRotation(vectorToTarget.angleDeg() + 90);

        sprites[0].setBounds(tower.getPosition().x + tower.getRect().width / 2f,
                             tower.getPosition().y + tower.getRect().height / 2f, sprites[0].getWidth(),
                             vectorToTarget.set(
                                             (tower.getPosition().x + tower.getRect().width / 2f) - (this.enemies[0].getPosition().x + this.enemies[0].getRectangle().width / 2f),
                                             (tower.getPosition().y + tower.getRect().height / 2f) - (this.enemies[0].getPosition().y + this.enemies[0].getRectangle().height / 2f))
                                     .dst(sprites[0].getBoundingRectangle().x, sprites[0].getBoundingRectangle().y));

        for (int i = 1; i < this.enemies.length; i++) {
            vectorToTarget.set(
                    (this.enemies[i - 1].getPosition().x + this.enemies[i - 1].getRectangle().width / 2f) - (this.enemies[i].getPosition().x + this.enemies[i].getRectangle().width / 2f),
                    (this.enemies[i - 1].getPosition().y + this.enemies[i - 1].getRectangle().height / 2f) - (this.enemies[i].getPosition().y + this.enemies[i].getRectangle().height / 2f));

            sprites[i].setRotation(vectorToTarget.angleDeg() + 90);

            sprites[i].setBounds(this.enemies[i - 1].getPosition().x + this.enemies[i - 1].getRectangle().width / 2f,
                                 this.enemies[i - 1].getPosition().y + this.enemies[i - 1].getRectangle().height / 2f,
                                 sprites[i].getWidth(), vectorToTarget.dst(sprites[i].getBoundingRectangle().x,
                                                                           sprites[i].getBoundingRectangle().y));
        }
    }

    @Override
    public void draw() {
        for (Sprite sprite : sprites) {
            sprite.draw(game.batch);
        }
    }
}
