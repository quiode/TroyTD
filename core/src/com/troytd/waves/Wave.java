package com.troytd.waves;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.ReflectionException;
import com.troytd.enemies.Enemy;
import com.troytd.enemies.enemyAmount;
import com.troytd.game.TroyTD;
import com.troytd.maps.Map;
import com.troytd.screens.GameScreen;

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
     * @param map           the map
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
    private Enemy spawnEnemy(Class<? extends Enemy> enemy, byte line, Vector2 position, Stage stage) {
        try {
            Enemy enemyInstance = (Enemy) ClassReflection.getConstructor(enemy, byte.class, TroyTD.class, Vector2.class,
                                                                         Vector2[].class, Map.class)
                    .newInstance(line, game, position, path, map);
            activeEnemies.add(enemyInstance);
            return enemyInstance;
        } catch (ReflectionException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void spawnEnemy(Class<? extends Enemy> enemy, byte line, Stage stage) {
        Enemy spawnedEnemy = spawnEnemy(enemy, line, path[0], stage);
    }

    private void spawnEnemy(enemyAmount enemyAmount, Stage stage) {
        if (enemyAmount.spawn()) spawnEnemy(enemyAmount.enemy, enemyAmount.getLine(), stage);
    }

    public void draw() {
        for (Enemy enemy : activeEnemies) {
            enemy.draw();
        }
    }

    private void updateEnemies(GameScreen gameScreen) {
        for (int i = activeEnemies.size() - 1; i >= 0; i--) {
            try {
                activeEnemies.get(i).update(activeEnemies, gameScreen);
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
            }
        }
    }

    private void updateWave(Stage stage) {
        for (int i = enemyList.size() - 1; i >= 0; i--) {
            try {
                enemyAmount enemyAmount = enemyList.get(i);
                if (enemyAmount.allSpawned()) {
                    enemyList.remove(enemyAmount); // remove the enemyAmount if all enemies spawned
                    return;
                }
                if (enemyAmount.readyToSpawn()) spawnEnemy(enemyAmount, stage);
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
            }
        }
    }

    public void update(Stage stage, GameScreen gameScreen) {
        updateWave(stage);
        updateEnemies(gameScreen);
    }

    public boolean isFinished() {
        return activeEnemies.isEmpty() && enemyList.isEmpty();
    }

    public ArrayList<Enemy> getEnemies() {
        return activeEnemies;
    }
}

