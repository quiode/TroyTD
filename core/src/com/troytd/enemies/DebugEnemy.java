package com.troytd.enemies;

import com.badlogic.gdx.math.Vector2;
import com.troytd.game.TroyTD;

/**
 * Class for debugging enemies
 */
public class DebugEnemy extends Enemy {
    public DebugEnemy(byte line, TroyTD game, Vector2 position) {
        super(line, game, position);
    }
}
