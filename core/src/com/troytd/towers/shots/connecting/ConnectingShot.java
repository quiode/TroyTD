package com.troytd.towers.shots.connecting;

import com.badlogic.gdx.Gdx;
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
    private final long lifeDuration;
    private final long startTime;
    private final Tower tower;
    /**
     * the enemies the shot hits
     */
    private final Enemy[] enemies;
    private final TroyTD game;
    private final int damage;
    /**
     * the different sprites for connecting the enemies
     */
    Sprite[] sprites;
    Vector2 vectorToTarget;
    private short amountOfSpritesToDraw;

    public ConnectingShot(TroyTD game, Tower tower, ArrayList<Enemy> enemies) {
        this.game = game;
        this.tower = tower;

        this.lifeDuration = (int) tower.getStat("lifeDuration").getValue();
        this.damage = (int) tower.getStat("damage").getValue();

        int enemyAmount = (int) tower.getStat("enemyAmount").getValue();

        this.enemies = Enemy.getClosestN(tower.getPosition(), enemies, enemyAmount);
        this.sprites = new Sprite[enemyAmount];
        float sizeModifier = game.settingPreference.getInteger("width") * 0.0035f / game.assetManager.get(
                "shots/" + tower.getClass().getSimpleName() + "Shot" + ".png", Texture.class).getWidth();
        for (int i = 0; i < enemyAmount; i++) {
            sprites[i] = new Sprite(game.assetManager.get("shots/" + tower.getClass().getSimpleName() + "Shot" + ".png",
                                                          Texture.class));
            sprites[i].setSize(sprites[i].getWidth() * sizeModifier, sprites[i].getHeight() * sizeModifier);
        }

        vectorToTarget = new Vector2();
        startTime = System.currentTimeMillis();
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

        for (Enemy enemy : this.enemies) {
            if (enemy == null || enemy.isDead()) {
                shots.remove(this);
                return;
            }
        }

        // resize sprites
        float x = tower.getPosition().x + tower.getRect().width / 2f;
        float y = tower.getPosition().y + tower.getRect().height / 2f;
        float width = sprites[0].getWidth();
        vectorToTarget.set(
                (this.enemies[0].getRectangle().x + this.enemies[0].getRectangle().width / 2f) - x + width / 2f,
                (this.enemies[0].getRectangle().y + this.enemies[0].getRectangle().height / 2f) - y);
        float height = vectorToTarget.len();
        try {
            if (vectorToTarget.len() > (Integer) ClassReflection.getField(tower.getClass(), "range").get(null)) {
                shots.remove(this);
                return;
            }
        } catch (ReflectionException e) {
            if (vectorToTarget.len() > (int) Tower.defaultStats.get("range").getValue()) {
                shots.remove(this);
                return;
            }
        }

        sprites[0].setOrigin(width / 2f, 0);
        sprites[0].setRotation(vectorToTarget.angleDeg() - 90);
        sprites[0].setBounds(x, y, width, height);
        this.enemies[0].takeDamage((int) (damage * Gdx.graphics.getDeltaTime()), enemies, tower, gameScreen);

        amountOfSpritesToDraw = 1;
        for (int i = 1; i < this.enemies.length; i++) {
            x = this.enemies[i - 1].getRectangle().x + this.enemies[i - 1].getRectangle().width / 2f;
            y = this.enemies[i - 1].getRectangle().y + this.enemies[i - 1].getRectangle().height / 2f;
            width = sprites[i].getWidth();
            vectorToTarget.set(
                    (this.enemies[i].getRectangle().x + this.enemies[i].getRectangle().width / 2f) - x + width / 2f,
                    (this.enemies[i].getRectangle().y + this.enemies[i].getRectangle().height / 2f) - y);
            height = vectorToTarget.len();
            try {
                if (vectorToTarget.len() > (Integer) ClassReflection.getField(tower.getClass(), "range2").get(null)) {
                    break;
                }
            } catch (ReflectionException e) {
                if (vectorToTarget.len() > (int) Tower.defaultStats.get("range2").getValue()) {
                    break;
                }
            }

            sprites[i].setOrigin(width / 2f, 0);
            sprites[i].setRotation(vectorToTarget.angleDeg() - 90);
            sprites[i].setBounds(x, y, width, height);
            amountOfSpritesToDraw++;
            this.enemies[i].takeDamage((int) (damage * Gdx.graphics.getDeltaTime()), enemies, tower, gameScreen);
        }
    }

    @Override
    public void draw() {
        for (int i = 0; i < amountOfSpritesToDraw; i++) {
            sprites[i].draw(game.batch);
        }
    }
}
