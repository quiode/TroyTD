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
    private final Vector2 vectorToHomeLocation = new Vector2();
    private final ProgressBar healthBar;
    private int hp;
    private Enemy target;


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
            if (target != null) target.setTargeted(false);
            return;
        }

        // target logic/moving logic
        if (target == null) { // searches for a target if there is none
            target = enemyInRange(enemies);
            if (target != null) {
                if (enemyInRange(target)) {
                    target.setTargeted(true);
                    if (enemyInAttackRange(target)) {
                        attack(target, enemies, gameScreen);
                    } else {
                        moveTo(target, units);
                    }
                } else {
                    target.setTargeted(false);
                    target = null;
                    goBackToHomeLocation(units);
                }
            } else {
                goBackToHomeLocation(units);
            }
        } else { // if there is a target, try to attack it
            if (enemyInRange(target)) {
                target.setTargeted(true);
                if (enemyInAttackRange(target)) {
                    attack(target, enemies, gameScreen);
                } else {
                    moveTo(target, units);
                }
            } else {
                target.setTargeted(false);
                target = null;
                goBackToHomeLocation(units);
            }
        }

        // heal if near tower
        if (tower.getRect()
                .getPosition(vectorToHomeLocation)
                .dst(sprite.getBoundingRectangle().getPosition(position)) < Tower.getSize(game) / 5f) {
            if (hp < (Integer) tower.getStat("maxHP").getValue()) {
                hp += MathUtils.round((Integer) tower.getStat("maxHP").getValue() * 0.025f + 0.5f);
            }
        }

        // health bar
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
        Enemy closestEnemy = Enemy.getClosestNotTargeted(tower.getCenterPosition(), enemies);

        if (closestEnemy != null && enemyInRange(closestEnemy)) {
            return closestEnemy;
        } else {
            return null;
        }
    }

    /**
     * @param enemy the enemy to check
     * @return true if the enemy is in range
     */
    private boolean enemyInRange(Enemy enemy) {
        enemy.getRectangle().getCenter(enemyCenter);
        return enemyCenter.dst(tower.getCenterPosition()) <= (int) tower.getStat("range").getValue();
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
     * @param enemy      the enemy to attack
     * @param enemies    the enemies array
     * @param gameScreen the game screen
     */
    private void attack(Enemy enemy, ArrayList<Enemy> enemies, GameScreen gameScreen) {
        if (enemy.takeDamage((int) tower.getStat("damage").getValue(), enemies, tower, gameScreen)) {
            target.setTargeted(false);
            target = null;
        }
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
        //correctIfOverlap(vectorToEnemy, units);

        /*
          copied from SingleShot
         */
        float tmp_speed = (int) tower.getStat("speed").getValue() * Gdx.graphics.getDeltaTime();
        vectorToEnemy.scl((float) Math.sqrt(
                1 / ((vectorToEnemy.x * vectorToEnemy.x + vectorToEnemy.y * vectorToEnemy.y) / tmp_speed * tmp_speed)));

        sprite.translate(vectorToEnemy.x, vectorToEnemy.y);
    }

    public void goBackToHomeLocation(ArrayList<Unit> units) {
        vectorToHomeLocation.set(new Vector2(getHomeLocation()).sub(getCenterPosition()));
        //correctIfOverlap(vectorToTower, units);

        /*
          copied from SingleShot
         */
        float tmp_speed = (int) tower.getStat("speed").getValue() * Gdx.graphics.getDeltaTime();
        vectorToHomeLocation.scl((float) Math.sqrt(
                1 / ((vectorToHomeLocation.x * vectorToHomeLocation.x + vectorToHomeLocation.y * vectorToHomeLocation.y) / tmp_speed * tmp_speed)));

        sprite.translate(vectorToHomeLocation.x, vectorToHomeLocation.y);
    }

    public Rectangle getRect() {
        return sprite.getBoundingRectangle();
    }

    public void takeDamage(int damage, ArrayList<Unit> units) {
        hp -= damage;
        if (hp <= 0) {
            if (target != null) target.setTargeted(false);
            units.remove(this);
            return;
        }
    }

    /**
     * @return the home location
     */
    public Vector2 getHomeLocation() {
        return tower.getHomeLocation();
    }

    /**
     * sets the home location
     *
     * @param homeLocation the new home location
     */
    public void setHomeLocation(final Vector2 homeLocation) {
        tower.setHomeLocation(homeLocation);
    }
}