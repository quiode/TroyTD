package com.troytd.waves;

import com.badlogic.gdx.math.Vector2;
import com.troytd.enemies.Enemy;
import com.troytd.game.TroyTD;
import com.troytd.helpers.enemyAmount;
import com.troytd.maps.Map;

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
    protected final Map map;

    /**
     * @param game          game instance
     * @param mapDistortion the distortion of the map
     * @param enemyList     the enemies that can spawn
     * @param path          the path the enemies traverse
     */
    public Wave(TroyTD game, Vector2 mapDistortion, ArrayList<enemyAmount> enemyList, Vector2[] path, Map map) {
        this.game = game;
        this.map = map;
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
                                                                     Vector2.class, Vector2[].class, Map.class);
            Enemy enemyInstance = ctor.newInstance(line, game, position, mapDistortion, path, map);
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
        for (int i = activeEnemies.size() - 1; i >= 0; i--) {
            try {
                activeEnemies.get(i).update(activeEnemies);
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
            }
        }
    }

    private void updateWave() {
        for (int i = enemyList.size() - 1; i >= 0; i--) {
            try {
                enemyAmount enemyAmount = enemyList.get(i);
                if (enemyAmount.allSpawned()) {
                    enemyList.remove(enemyAmount); // remove the enemyAmount if all enemies spawned
                    return;
                }
                if (enemyAmount.readyToSpawn()) spawnEnemy(enemyAmount);
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
            }
        }
    }

    public void update() {
        updateWave();
        updateEnemies();
    }

    public boolean isFinished() {
        return activeEnemies.isEmpty() && enemyList.isEmpty();
    }

    public ArrayList<Enemy> getEnemies(){
        return activeEnemies;
    }
}

