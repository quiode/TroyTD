package com.troytd.towers.shots.connecting;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.troytd.enemies.Enemy;
import com.troytd.game.TroyTD;
import com.troytd.towers.shots.ShotType;

import java.util.ArrayList;

public abstract class ConnectingShot {
    private static final ShotType shotType = ShotType.CONNECTING;
    /**
     * amount of enemies to connect
     */
    public final short amountOfEnemies = 3;
    public final short damage = 10;
    Sprite sprite;
    /**
     * the enemies the shot hits
     */
    ArrayList<Enemy> enemies = new ArrayList<Enemy>(amountOfEnemies);
    TroyTD game;

    public ConnectingShot(TroyTD game, Texture texture) {
        this.game = game;

    }
}
