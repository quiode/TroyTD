package com.troytd.enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.ReflectionException;
import com.talosvfx.talos.runtime.ParticleEffectInstance;
import com.troytd.game.TroyTD;
import com.troytd.helpers.Helper;
import com.troytd.helpers.Stat;
import com.troytd.maps.Map;
import com.troytd.screens.GameScreen;
import com.troytd.towers.Tower;
import com.troytd.towers.units.Unit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

/**
 * an enemy with its texture, health and other related properties
 */
public abstract class Enemy {
    public final static HashMap<String, Stat> defaultStats = new HashMap<>();
    /**
     * the type of the enemy
     */
    public static final EnemyTypes type = EnemyTypes.NONE;

    static {
        defaultStats.put("spawnSpeed", new Stat<>("spawnSpeed", 100));
        defaultStats.put("maxHP", new Stat<>("maxHP", 100));
        defaultStats.put("speed", new Stat<>("speed", 50));
        defaultStats.put("damage", new Stat<>("damage", 100));
        defaultStats.put("range", new Stat<>("range", 100));
        defaultStats.put("worth", new Stat<>("worth", 100));
        defaultStats.put("sizeModifier", new Stat<>("sizeModifier", 0.1f));
    }

    protected final Vector2[] path;
    /**
     * the line where the enemy is located, 0 is the top line, 1 is the middle line, 2 is the bottom line
     */
    protected final byte line;
    protected final TroyTD game;
    final protected Sprite enemySprite;
    protected final Map map;
    private final ProgressBar healthBar;
    private final Vector2 position = new Vector2();
    private final Vector2 centerPosition;
    private final ParticleEffectInstance damagerEffect;
    /**
     * t in [0,1] in path
     */
    protected int position_on_path;
    /**
     * true if unit is targeting this enemy
     */
    private boolean isTargeted = false;
    private boolean draw = true;
    private int hp;

    /**
     * @param line     the line where the enemy is located, 0 is the top line, 1 is the middle line, 2 is the bottom line
     * @param game     the game instance
     * @param position the position of the enemy
     * @param path     the path the enemy will follow, in precalculated points
     */
    public Enemy(byte line, final TroyTD game, final Vector2 position, final Texture texture, Vector2[] path,
                 final Map map) {
        HashMap<String, Stat> currentDefaultStats;
        try {
            currentDefaultStats = (HashMap<String, Stat>) ClassReflection.getDeclaredField(this.getClass(),
                                                                                           "defaultStats").get(null);
        } catch (ReflectionException e) {
            currentDefaultStats = defaultStats;
        }

        hp = (int) ((int) currentDefaultStats.get("maxHP").getValue() + (int) currentDefaultStats.get("maxHP")
                .getValue() * 0.1f * game.settingPreference.getInteger("difficulty"));
        this.line = line;
        this.game = game;
        this.map = map;
        this.enemySprite = new Sprite(texture) {
            @Override
            public void setPosition(float x, float y) {
                super.setPosition(x - game.settingPreference.getInteger("width") / 2f,
                                  y - game.settingPreference.getInteger("height") / 2f);
            }
        };
        this.path = path;
        enemySprite.setPosition(position.x, position.y);
        float sizeModifier = game.settingPreference.getInteger("width") * 0.025f / enemySprite.getWidth();
        enemySprite.setSize(enemySprite.getWidth() * sizeModifier, enemySprite.getHeight() * sizeModifier); // scales
        // the enemy
        healthBar = new ProgressBar(0, hp, 1, false, game.skin, "enemy_health");

        healthBar.setValue(hp);
        healthBar.setSize(enemySprite.getWidth(), healthBar.getHeight());
        healthBar.setVisible(false);
        centerPosition = enemySprite.getBoundingRectangle().getCenter(new Vector2());

        // particles
        damagerEffect = map.BiggerDamagerEffectDescriptor.createEffectInstance();
        damagerEffect.pause();
    }

    public static void loadAssets() {
    }

    public static Enemy getClosest(Vector2 position, ArrayList<Enemy> enemies) {
        Vector2[] positions = new Vector2[enemies.size()];
        for (int i = 0; i < positions.length; i++) {
            positions[i] = enemies.get(i).getPosition();
        }
        return enemies.get(Helper.getClosest(position, positions));
    }

    /**
     * @param position the position to start the search from
     * @param enemies  the enemies to check
     * @return the closest not-targeted enemy or null if none is found
     */
    public static Enemy getClosestNotTargeted(final Vector2 position, ArrayList<Enemy> enemies) {
        ArrayList<Enemy> toSort = (ArrayList<Enemy>) enemies.clone();
        Collections.sort(toSort, new Comparator<Enemy>() {
            @Override
            public int compare(Enemy enemy, Enemy t1) {
                if (position.dst(enemy.getPosition()) == position.dst(t1.getPosition())) {
                    return 0;
                } else if (position.dst(enemy.getPosition()) > position.dst(t1.getPosition())) {
                    return 1;
                } else {
                    return -1;
                }
            }
        });

        for (Enemy enemy : toSort) {
            if (!enemy.isTargeted()) {
                return enemy;
            }
        }
        return null;
    }

    /**
     * gets the closest n amount of enemies to the position
     *
     * @param position the position to check
     * @param enemies  the enemies to check
     * @param n        the amount of enemies to check
     * @return an array of the closest n positions from enemies or an array of all position of enemies if n is bigger
     * than the amount of enemies
     */
    public static Enemy[] getClosestN(Vector2 position, ArrayList<Enemy> enemies, int n) {
        if (n >= enemies.size()) {
            return enemies.toArray(new Enemy[0]);
        }
        ArrayList<Vector2> enemyPositions = new ArrayList<>(enemies.size());
        for (int i = 0; i < enemies.size(); i++) {
            enemyPositions.add(enemies.get(i).getPosition());
        }
        ArrayList<Vector2> positions = new ArrayList<>(n + 1);
        positions.add(position);
        for (int i = 0; i < n; i++) {
            enemyPositions.removeAll(positions);
            positions.add(enemies.get(Helper.getClosest(positions.get(i), enemyPositions)).getPosition());
        }
        Enemy[] result = new Enemy[n];
        for (int i = 0; i < n; i++) {
            for (Enemy enemy : enemies) {
                if (enemy.getPosition().equals(positions.get(i + 1))) {
                    result[i] = enemy;
                    break;
                }
            }
        }
        return result;
    }

    public Vector2 getPosition() {
        return enemySprite.getBoundingRectangle().getPosition(position);
    }

    public void setPosition(Vector2 position) {
        enemySprite.setPosition(position.x, position.y);
    }

    public void dispose() {
        enemySprite.getTexture().dispose();
    }

    public void hide() {
        draw = false;
    }

    public void show() {
        draw = true;
    }

    public void draw(GameScreen gameScreen) {
        if (draw) {
            enemySprite.draw(game.batch);
            if (healthBar.isVisible()) healthBar.draw(game.batch, 1);

            // particles
            damagerEffect.render(gameScreen.defaultRenderer);
        }
    }

    public void update(final ArrayList<Enemy> enemies, GameScreen gameScreen, ArrayList<Unit> units) {
        HashMap<String, Stat> currentDefaultStats;
        try {
            currentDefaultStats = (HashMap<String, Stat>) ClassReflection.getDeclaredField(this.getClass(),
                                                                                           "defaultStats").get(null);
        } catch (ReflectionException e) {
            currentDefaultStats = defaultStats;
        }

        if (!isTargeted) { // only move when not targeted
            position_on_path += (int) currentDefaultStats.get("speed").getValue() * Gdx.graphics.getDeltaTime() * 50;

            if (position_on_path < path.length) {
                enemySprite.setPosition(path[position_on_path].x,
                                        path[position_on_path].y - (1+line) * game.settingPreference.getInteger(
                                                "height") / 36f);

                try {
                    if (hp < ((int) ClassReflection.getField(this.getClass(), "maxHP")
                            .get(null) + (int) ClassReflection.getField(this.getClass(), "maxHP")
                            .get(null) * 0.1f * game.settingPreference.getInteger("difficulty")) * 0.9f) {
                        healthBar.setVisible(true);
                        healthBar.setPosition(enemySprite.getX(), enemySprite.getY() - 2.5f);
                        healthBar.setValue(hp);
                    } else {
                        healthBar.setVisible(false);
                    }
                } catch (Exception e) {
                    if (hp < (int) currentDefaultStats.get("maxHP").getValue() * 0.9f) {
                        healthBar.setVisible(true);
                        healthBar.setPosition(enemySprite.getX(), enemySprite.getY() - 2.5f);
                        healthBar.setValue(hp);
                    } else {
                        healthBar.setVisible(false);
                    }
                }

                attackUnits(units);
            } else {
                enemies.remove(this);
                try {
                    gameScreen.health -= (short) ClassReflection.getField(this.getClass(), "damage").get(null);
                } catch (ReflectionException e) {
                    gameScreen.health -= (int) currentDefaultStats.get("damage").getValue();
                }
                if (gameScreen.health <= 0) {
                    map.lost = true;
                }
            }
        } else {
            attackUnits(units);
            try {
                if (hp < ((int) ClassReflection.getField(this.getClass(), "maxHP")
                        .get(null) + (int) ClassReflection.getField(this.getClass(), "maxHP")
                        .get(null) * 0.1f * game.settingPreference.getInteger("difficulty")) * 0.9f) {
                    healthBar.setVisible(true);
                    healthBar.setPosition(enemySprite.getX(), enemySprite.getY() - 2.5f);
                    healthBar.setValue(hp);
                } else {
                    healthBar.setVisible(false);
                }
            } catch (Exception e) {
                if (hp < (int) currentDefaultStats.get("maxHP").getValue() * 0.9f) {
                    healthBar.setVisible(true);
                    healthBar.setPosition(enemySprite.getX(), enemySprite.getY() - 2.5f);
                    healthBar.setValue(hp);
                } else {
                    healthBar.setVisible(false);
                }
            }
        }

        // particles
        damagerEffect.update(Gdx.graphics.getDeltaTime());
    }

    public Rectangle getRectangle() {
        return new Rectangle(enemySprite.getBoundingRectangle());
    }

    /**
     * takes damage and removes the enemy if its hp below 0
     *
     * @param damage  the damage to take
     * @param enemies the enemies to remove the enemy from
     * @param tower   the tower the shot was sent from
     * @return true if the enemy was killed
     */
    public boolean takeDamage(int damage, ArrayList<Enemy> enemies, Tower tower, GameScreen gameScreen) {
        HashMap<String, Stat> currentDefaultStats;
        try {
            currentDefaultStats = (HashMap<String, Stat>) ClassReflection.getDeclaredField(this.getClass(),
                                                                                           "defaultStats").get(null);
        } catch (ReflectionException e) {
            currentDefaultStats = defaultStats;
        }

        hp -= damage;

        // particles
        damagerEffect.setPosition(getCenterPosition().x, getCenterPosition().y);
        if (damagerEffect.isPaused()) {
            damagerEffect.resume();
        }
        damagerEffect.restart();

        if (hp <= 0) {
            enemies.remove(this);
            tower.kills++;
            gameScreen.kills++;
            try {
                gameScreen.money += (int) ClassReflection.getField(this.getClass(), "worth").get(null);
            } catch (ReflectionException e) {
                gameScreen.money += (int) currentDefaultStats.get("worth").getValue();
            }
            return true;
        }
        return false;
    }

    public boolean isDead() {
        return hp <= 0;
    }

    public Vector2 getCenterPosition() {
        return enemySprite.getBoundingRectangle().getCenter(centerPosition);
    }

    /**
     * if the enemy overlaps a unit, attack it
     *
     * @param units the units to check for collision
     */
    public void attackUnits(ArrayList<Unit> units) {
        HashMap<String, Stat> currentDefaultStats;
        try {
            currentDefaultStats = (HashMap<String, Stat>) ClassReflection.getDeclaredField(this.getClass(),
                                                                                           "defaultStats").get(null);
        } catch (ReflectionException e) {
            currentDefaultStats = defaultStats;
        }

        for (int i = 0; i < units.size(); i++) {
            if (units.get(i) != null) {
                if (units.get(i).getRect().overlaps(getRectangle())) {
                    units.get(i)
                            .takeDamage(MathUtils.round(
                                    (Integer) currentDefaultStats.get("damage").getValue() / 10f + 0.5f), units);
                }
            }
        }
    }

    /**
     * @return true if unit is targeting this enemy
     */
    public boolean isTargeted() {
        return isTargeted;
    }

    /**
     * @param isTargeted true if unit is targeting this enemy
     */
    public void setTargeted(boolean isTargeted) {
        if (this.isTargeted != isTargeted) {
            this.isTargeted = isTargeted;
        }
    }
}
