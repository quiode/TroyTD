package com.troytd.helpers;

import com.troytd.enemies.Enemy;

/**
 * Class which stores an enemy class and the number of enemies to spawn
 */
public class enemyAmount {
    public final int maxAmount;
    public final Class<? extends Enemy> enemy;
    public int currentAmount;

    public enemyAmount(int maxAmount, Class<? extends Enemy> enemy) {
        this.maxAmount = maxAmount;
        this.enemy = enemy;
        this.currentAmount = 0;
    }
}
