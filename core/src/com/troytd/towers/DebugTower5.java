package com.troytd.towers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.troytd.game.TroyTD;
import com.troytd.towers.shots.single.DebugShot;


/**
 * Class for debugging towers.
 */
public class DebugTower5 extends Tower {
    public final static int cost = 24;
    public final static int damage = 53;
    public final static int range = 45;
    public final static int speed = 45;
    public final static int maxHP = 234;

    public DebugTower5(TroyTD game, Vector2 position, Vector2 distortion) {
        super(game, position, game.assetManager.get("towers/DebugTower5.png", Texture.class), "DebugTower5",
              TowerTypes.MELEE, distortion, DebugShot.class);
    }
}
