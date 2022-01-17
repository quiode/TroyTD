package com.troytd.towers.shots.single;

import com.badlogic.gdx.math.Vector2;
import com.troytd.enemies.Enemy;
import com.troytd.game.TroyTD;
import com.troytd.towers.Tower;

public class DebugShot extends Shot {
    public DebugShot(TroyTD game, Tower tower, Enemy enemy, Vector2 distortion) {
        super(game, tower, enemy, distortion);
    }
}
