package com.troytd.towers.units;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
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
    /**
     * center position of sprite
     */
    private final Vector2 position;
    private final Vector2 vectorToEnemy = new Vector2();
    private final Vector2 vectorToTower = new Vector2();
    private final ProgressBar healthBar;
    private int hp;


    public Unit(UnitType type, final TroyTD game, Tower tower) {
        this.type = type;
        this.game = game;
        this.tower = tower;

        this.hp = (Integer) tower.getStat("maxHP").getValue();

        sprite = new Sprite(
                game.assetManager.get("units/" + tower.getClass().getSimpleName() + "Unit" + ".png", Texture.class));
        sprite.setSize(Tower.getSize(game) / 5f, Tower.getSize(game) / 5f);
        sprite.setPosition(tower.getPosition().x + tower.getRect().width / 2f - sprite.getWidth() / 2f,
                           tower.getPosition().y + tower.getRect().height * 1.1f);
        this.position = sprite.getBoundingRectangle().getCenter(new Vector2());

        // the enemy
        healthBar = new ProgressBar(0, hp, 1, false, game.skin, "enemy_health");

        healthBar.setValue(hp);
        healthBar.setSize(sprite.getWidth(), healthBar.getHeight());
        healthBar.setVisible(false);
    }

    public void draw() {
        sprite.draw(game.batch);
        if (healthBar.isVisible()) healthBar.draw(game.batch, 1);
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
                    moveTo(enemy, units);
                }
            } else {
                goBackToTower(units);
            }
        } else {
            goBackToTower(units);
        }

        try {
            if (hp < (int) tower.getStat("maxHP").getValue()) {
                healthBar.setVisible(true);
                healthBar.setPosition(sprite.getX(), sprite.getY() - 2.5f);
                healthBar.setValue(hp);
            } else {
                healthBar.setVisible(false);
            }
        } catch (Exception e) {
            if (hp < (int) tower.getStat("maxHP").getValue() * 0.9f) {
                healthBar.setVisible(true);
                healthBar.setPosition(sprite.getX(), sprite.getY() - 2.5f);
                healthBar.setValue(hp);
            } else {
                healthBar.setVisible(false);
            }
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
    private void moveTo(Enemy enemy, ArrayList<Unit> units) {
        vectorToEnemy.set(enemy.getCenterPosition().sub(getCenterPosition()));
        correctIfOverlap(vectorToEnemy, units);

        /*
          copied from SingleShot
         */
        float tmp_speed = (int) tower.getStat("speed").getValue() * Gdx.graphics.getDeltaTime();
        vectorToEnemy.scl((float) Math.sqrt(
                1 / ((vectorToEnemy.x * vectorToEnemy.x + vectorToEnemy.y * vectorToEnemy.y) / tmp_speed * tmp_speed)));

        sprite.translate(vectorToEnemy.x, vectorToEnemy.y);
    }

    public void goBackToTower(ArrayList<Unit> units) {
        vectorToTower.set(tower.getCenterPosition().add(0, tower.getRect().height / 2f).sub(getCenterPosition()));
        correctIfOverlap(vectorToTower, units);

        /*
          copied from SingleShot
         */
        float tmp_speed = (int) tower.getStat("speed").getValue() * Gdx.graphics.getDeltaTime();
        vectorToTower.scl((float) Math.sqrt(
                1 / ((vectorToTower.x * vectorToTower.x + vectorToTower.y * vectorToTower.y) / tmp_speed * tmp_speed)));

        sprite.translate(vectorToTower.x, vectorToTower.y);
    }

    /**
     * adds randomness to the unit's movement
     */
    private Vector2 correctVector(final Vector2 vector) {
        float rangeX = game.settingPreference.getInteger("height") / 10f;
        float rangeY = game.settingPreference.getInteger("width") / 10f;
        rangeX = MathUtils.random(-rangeX, rangeX);
        rangeY = MathUtils.random(-rangeY, rangeY);
        vector.add(rangeX, rangeY);
        return vector;
    }

    private Vector2 correctIfOverlap(final Vector2 vector, final ArrayList<Unit> units) {
        for (Unit unit : units) {
            if (unit.sprite.getBoundingRectangle().overlaps(sprite.getBoundingRectangle())) {
                return correctVector(vector);
            }
        }
        return vector;
    }

    public Rectangle getRect() {
        return sprite.getBoundingRectangle();
    }

    public void takeDamage(int damage, ArrayList<Unit> units) {
        hp -= damage;
        if (hp <= 0) units.remove(this);
    }
}