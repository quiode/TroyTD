package com.troytd.maps;

import com.badlogic.gdx.math.CatmullRomSpline;
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
    protected final TroyTD game;
    protected final Vector2 mapDistortion;
    /**
     * enemies that can spawn
     */
    public static final ArrayList<enemyAmount> enemyList = new ArrayList<>();
    /**
     * the current spawned enemies
     */
    public final ArrayList<Enemy> activeEnemies = new ArrayList<>();
    /**
     * the path the enemies traverse
     */
    protected final CatmullRomSpline<Vector2> path;

    /**
     * @param game          game instance
     * @param mapDistortion the distortion of the map
     * @param path
     */
    public Wave(TroyTD game, Vector2 mapDistortion,
                CatmullRomSpline<Vector2> path) {
        this.game = game;
        this.mapDistortion = mapDistortion;
        this.path = path;
    }

    /**
     * @param enemy    the enemy class to spawn
     * @param line     the line where to spawn the enemy
     * @param position the position to spawn
     * @return the spawned enemy
     */
    protected Enemy spawnEnemy(Class<? extends Enemy> enemy, byte line, Vector2 position) {
        try {
            Constructor<? extends Enemy> ctor = enemy.getConstructor(byte.class, TroyTD.class, Vector2.class,
                                                                     Vector2.class, CatmullRomSpline.class);
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

    protected void spawnEnemy(Class<? extends Enemy> enemy, byte line) {
        Enemy spawnedEnemy = spawnEnemy(enemy, line, new Vector2());
        if (spawnedEnemy != null) {
            spawnedEnemy.hide();
        }
    }

    public void draw() {
        for (Enemy enemy : activeEnemies) {
            enemy.draw();
        }
    }

    public void update() {
        for (Enemy enemy : activeEnemies) {
            enemy.update(activeEnemies);
        }
    }
}

