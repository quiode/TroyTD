package com.troytd.towers.shots.connecting;

import com.troytd.enemies.Enemy;
import com.troytd.game.TroyTD;
import com.troytd.screens.GameScreen;
import com.troytd.towers.Tower;

import java.util.ArrayList;

public class DebugConnectingShot extends ConnectingShot {
    public DebugConnectingShot(TroyTD game, Tower tower, ArrayList<Enemy> enemies) {
        super(game, tower, enemies);
    }
}
