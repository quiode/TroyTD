package com.troytd.enemies;

import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.ReflectionException;
import com.troytd.helpers.Stat;

import java.util.HashMap;

/**
 * Class which stores an enemy class and the number of enemies to spawn
 */
public class enemyAmount {
    public final int maxAmount;
    public final Class<? extends Enemy> enemy;
    /**
     * last spawn as timestamp
     */
    public long lastSpawn;
    private byte line;
    private int currentAmount;

    public enemyAmount(int maxAmount, Class<? extends Enemy> enemy) {
        this.maxAmount = maxAmount;
        this.enemy = enemy;
        this.currentAmount = 0;
        this.lastSpawn = TimeUtils.millis();
        this.line = 0;
    }

    /**
     * updates the amount of enemies spawned and returns false if the max amount has been reached
     *
     * @return true if the enemy has been spawned, false if all enemies have been spawned
     */
    public boolean spawn() {
        if (currentAmount < maxAmount) {
            currentAmount++;
            lastSpawn = TimeUtils.millis();
            if (this.line < 3) {
                line++;
            } else {
                line = 0;
            }
            return true;
        }
        return false;
    }

    /**
     * @return true if all enemies have been spawned
     */
    public boolean allSpawned() {
        return currentAmount >= maxAmount;
    }

    /**
     * @return true if new enemy can be spawned
     */
    public boolean readyToSpawn() {
        HashMap<String, Stat> currentDefaultStats;
        try {
            currentDefaultStats = (HashMap<String, Stat>) ClassReflection.getDeclaredField(enemy, "defaultStats")
                    .get(null);
        } catch (ReflectionException e) {
            currentDefaultStats = Enemy.defaultStats;
        }
        return TimeUtils.timeSinceMillis(lastSpawn) > 200000 / (int) currentDefaultStats.get("spawnSpeed").getValue();
    }

    public byte getLine() {
        return line;
    }
}
