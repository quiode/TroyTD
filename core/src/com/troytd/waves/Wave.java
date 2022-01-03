package com.troytd.waves;

import com.badlogic.gdx.math.Vector2;
import com.troytd.enemies.Enemy;
import com.troytd.game.TroyTD;
import com.troytd.helpers.enemyAmount;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

/**
 * Stores the enemies and the amount of enemies that spawn in a wave
 */
public abstract class Wave {
    /**
     * game instance
     */
    protected final TroyTD game;
    /**
     * how much map is stretched
     */
    protected final Vector2 mapDistortion;
    /**
     * enemies that can spawn
     */
    protected final ArrayList<enemyAmount> enemyList;
    /**
     * the current spawned enemies
     */
    protected final ArrayList<Enemy> activeEnemies = new ArrayList<>();
    /**
     * the path the enemies traverse
     */
    protected final Vector2[] path;

    /**
     * @param game          game instance
     * @param mapDistortion the distortion of the map
     * @param enemyList     the enemies that can spawn
     * @param path          the path the enemies traverse
     */
    public Wave(TroyTD game, Vector2 mapDistortion, ArrayList<enemyAmount> enemyList, Vector2[] path) {
        this.game = game;
        this.mapDistortion = mapDistortion;
        this.enemyList = enemyList;
        this.path = path;
    }

    public static Class<? extends Enemy>[] getEnemyList() {
        return new Class[]{};
    }

    /**
     * @param enemy    the enemy class to spawn
     * @param line     the line where to spawn the enemy
     * @param position the position to spawn
     * @return the spawned enemy
     */
    private Enemy spawnEnemy(Class<? extends Enemy> enemy, byte line, Vector2 position) {
        try {
            Constructor<? extends Enemy> ctor = enemy.getConstructor(byte.class, TroyTD.class, Vector2.class,
                                                                     Vector2.class, Vector2[].class);
            Enemy enemyInstance = ctor.newInstance(line, game, position, mapDistortion, path);
            activeEnemies.add(enemyInstance);
            return enemyInstance;
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void spawnEnemy(Class<? extends Enemy> enemy, byte line) {
        Enemy spawnedEnemy = spawnEnemy(enemy, line, path[0]);
        /*
        if (spawnedEnemy != null) {
            spawnedEnemy.hide();
        }
         */
    }

    private void spawnEnemy(enemyAmount enemyAmount) {
        if (enemyAmount.spawn()) spawnEnemy(enemyAmount.enemy, enemyAmount.getLine());
    }

    public void draw() {
        for (Enemy enemy : activeEnemies) {
            enemy.draw();
        }
    }

    private void updateEnemies() {
        for (Enemy enemy : activeEnemies) {
            enemy.update(activeEnemies);
        }
    }

    private void updateWave() {
        for (enemyAmount enemyAmount : enemyList) {
            if (enemyAmount.allSpawned()) {
                enemyList.remove(enemyAmount); // remove the enemyAmount if all enemies spawned
                return;
            }
            if (enemyAmount.readyToSpawn()) spawnEnemy(enemyAmount);
        }
    }

    public void update() {
        updateWave();
        updateEnemies();
    }

    public boolean isFinished() {
        return activeEnemies.isEmpty() && enemyList.isEmpty();
    }
}

