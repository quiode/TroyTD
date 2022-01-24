package com.troytd.enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.ReflectionException;
import com.troytd.game.TroyTD;
import com.troytd.helpers.Helper;
import com.troytd.helpers.Stat;
import com.troytd.maps.Map;
import com.troytd.screens.GameScreen;
import com.troytd.towers.Tower;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * an enemy with its texture, health and other related properties
 */
public abstract class Enemy {
    public final static HashMap<String, Stat> defaultStats = new HashMap<>();

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
    /**
     * t in [0,1] in path
     */
    protected int position_on_path;
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
        Vector2[] enemyPositions = new Vector2[enemies.size()];
        for (int i = 0; i < enemyPositions.length; i++) {
            enemyPositions[i] = enemies.get(i).getPosition();
        }
        Vector2[] positions = new Vector2[n + 1];
        positions[0] = position;
        for (int i = 0; i < n; i++) {
            positions[i + 1] = enemies.get(Helper.getClosest(positions[i], enemyPositions)).getPosition();
        }
        Enemy[] result = new Enemy[n];
        for (int i = 0; i < n; i++) {
            for (Enemy enemy : enemies) {
                if (enemy.getPosition().equals(positions[i + 1])) {
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

    public void draw() {
        if (draw) {
            enemySprite.draw(game.batch);
            if (healthBar.isVisible()) healthBar.draw(game.batch, 1);
        }
    }

    public void update(final ArrayList<Enemy> enemies, GameScreen gameScreen) {
        HashMap<String, Stat> currentDefaultStats;
        try {
            currentDefaultStats = (HashMap<String, Stat>) ClassReflection.getDeclaredField(this.getClass(),
                                                                                           "defaultStats").get(null);
        } catch (ReflectionException e) {
            currentDefaultStats = defaultStats;
        }

        position_on_path += (int) currentDefaultStats.get("speed").getValue() * Gdx.graphics.getDeltaTime() * 50;

        if (position_on_path < path.length) {
            enemySprite.setPosition(path[position_on_path].x,
                                    path[position_on_path].y - line * game.settingPreference.getInteger(
                                            "height") / 24f);

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
     */
    public void takeDamage(int damage, ArrayList<Enemy> enemies, Tower tower, GameScreen gameScreen) {
        HashMap<String, Stat> currentDefaultStats;
        try {
            currentDefaultStats = (HashMap<String, Stat>) ClassReflection.getDeclaredField(this.getClass(),
                                                                                           "defaultStats").get(null);
        } catch (ReflectionException e) {
            currentDefaultStats = defaultStats;
        }

        hp -= damage;
        if (hp <= 0) {
            enemies.remove(this);
            tower.kills++;
            gameScreen.kills++;
            try {
                gameScreen.money += (int) ClassReflection.getField(this.getClass(), "worth").get(null);
            } catch (ReflectionException e) {
                gameScreen.money += (int) currentDefaultStats.get("worth").getValue();
            }
        }
    }

    public boolean isDead() {
        return hp <= 0;
    }

    public Vector2 getCenterPosition() {
        return enemySprite.getBoundingRectangle().getCenter(centerPosition);
    }
}
