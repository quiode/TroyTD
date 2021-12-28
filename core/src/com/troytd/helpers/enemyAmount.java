package com.troytd.helpers;

import com.badlogic.gdx.utils.TimeUtils;
import com.troytd.enemies.Enemy;

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
        try {
            return TimeUtils.timeSinceMillis(lastSpawn) > enemy.getField("spawnSpeed").getInt(new Object());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return false;
    }

    public byte getLine() {
        return line;
    }
}
