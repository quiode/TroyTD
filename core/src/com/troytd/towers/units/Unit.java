package com.troytd.towers.units;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.troytd.enemies.Enemy;
import com.troytd.game.TroyTD;
import com.troytd.screens.GameScreen;
import com.troytd.towers.Tower;

import java.util.ArrayList;

public abstract class Unit {
    final Vector2 enemyCenter = new Vector2();
    private final UnitType type;
    private final TroyTD game;
    private final Sprite sprite;
    private final Tower tower;
    private final int hp;
    /**
     * center position of sprite
     */
    private final Vector2 position;
    private final Vector2 vectorToEnemy = new Vector2();
    private final Vector2 vectorToTower = new Vector2();


    public Unit(UnitType type, final TroyTD game, Tower tower) {
        this.type = type;
        this.game = game;
        this.tower = tower;

        this.hp = (Integer) tower.getStat("maxHP").getValue();

        sprite = new Sprite(
                game.assetManager.get("units/" + tower.getClass().getSimpleName() + "Unit" + ".png", Texture.class)) {
            @Override
            public void translate(float x, float y) {
                float rangeX = game.settingPreference.getInteger("height") / 350f;
                float rangeY = game.settingPreference.getInteger("width") / 350f;
                super.translate(x + MathUtils.random(-rangeX, +rangeX), y + MathUtils.random(-rangeY, +rangeY));
            }
        };
        sprite.setSize(Tower.getSize(game) / 5f, Tower.getSize(game) / 5f);
        sprite.setPosition(tower.getPosition().x + tower.getRect().width / 2f - sprite.getWidth() / 2f,
                           tower.getPosition().y + tower.getRect().height * 1.1f);
        this.position = sprite.getBoundingRectangle().getCenter(new Vector2());
    }

    public void draw() {
        sprite.draw(game.batch);
    }

    public void update(ArrayList<Unit> units, ArrayList<Enemy> enemies, GameScreen gameScreen) {
        if (hp <= 0) {
            units.remove(this);
            return;
        }

        if (!enemies.isEmpty()) {
            Enemy enemy = enemyInRange(enemies);
            if (enemy != null) {
                if (enemyInAttackRange(enemy)) {
                    attack(enemy, enemies, gameScreen);
                } else {
                    moveTo(enemy);
                }
            } else {
                goBackToTower();
            }
        } else {
            goBackToTower();
        }
    }

    public Tower getTower() {
        return tower;
    }

    public UnitType getType() {
        return type;
    }

    /**
     * @param enemies the enemies to attack
     * @return an enemy if he is in range (of the tower), null otherwise
     */
    private Enemy enemyInRange(ArrayList<Enemy> enemies) {
        Enemy closestEnemy = Enemy.getClosest(tower.getCenterPosition(), enemies);
        closestEnemy.getRectangle().getCenter(enemyCenter);

        if (enemyCenter.dst(tower.getCenterPosition()) <= (int) tower.getStat("range").getValue()) {
            return closestEnemy;
        } else {
            return null;
        }
    }


    private Vector2 getCenterPosition() {
        updateCenterPosition();
        return position;
    }

    private void updateCenterPosition() {
        position.set(sprite.getBoundingRectangle().getCenter(new Vector2()));
    }

    /**
     * attacks the enemy
     *
     * @param enemy
     * @param enemies
     * @param gameScreen
     */
    private void attack(Enemy enemy, ArrayList<Enemy> enemies, GameScreen gameScreen) {
        enemy.takeDamage((int) tower.getStat("damage").getValue(), enemies, tower, gameScreen);
    }

    /**
     * @param enemy the enemy to attack
     * @return true if enemy is in range, false otherwise
     */
    private boolean enemyInAttackRange(Enemy enemy) {
        return getCenterPosition().dst(enemy.getCenterPosition()) <= (int) tower.getStat("range2").getValue();
    }

    /**
     * moves the unit to the enemy according to speed of the unit
     *
     * @param enemy the enemy to move to
     */
    private void moveTo(Enemy enemy) {
        vectorToEnemy.set(enemy.getCenterPosition().sub(getCenterPosition()));

        /*
          copied from SingleShot
         */
        float tmp_speed = (int) tower.getStat("speed").getValue() * Gdx.graphics.getDeltaTime();
        vectorToEnemy.scl((float) Math.sqrt(
                1 / ((vectorToEnemy.x * vectorToEnemy.x + vectorToEnemy.y * vectorToEnemy.y) / tmp_speed * tmp_speed)));

        sprite.translate(vectorToEnemy.x, vectorToEnemy.y);
    }

    public void goBackToTower() {
        vectorToTower.set(tower.getCenterPosition().add(0, tower.getRect().height / 2f).sub(getCenterPosition()));

        /*
          copied from SingleShot
         */
        float tmp_speed = (int) tower.getStat("speed").getValue() * Gdx.graphics.getDeltaTime();
        vectorToTower.scl((float) Math.sqrt(
                1 / ((vectorToTower.x * vectorToTower.x + vectorToTower.y * vectorToTower.y) / tmp_speed * tmp_speed)));

        sprite.translate(vectorToTower.x, vectorToTower.y);
    }
}