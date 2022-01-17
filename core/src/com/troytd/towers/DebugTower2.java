package com.troytd.towers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.troytd.game.TroyTD;
import com.troytd.towers.shots.single.DebugShot;

public class DebugTower2 extends Tower {
    public final static int cost = 200;
    public final static int damage = 26;

    public DebugTower2(TroyTD game, Vector2 position, Vector2 distortion) {
        super(game, position, game.assetManager.get("towers/DebugTower2.png", Texture.class), "DebugTower2",
              TowerTypes.SINGLE, distortion, DebugShot.class);
    }
}
