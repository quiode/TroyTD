package com.troytd.towers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.troytd.game.TroyTD;
import com.troytd.towers.shots.single.DebugShot;

public class Spearthrower extends Tower {
    public final static int cost = 80;
    public final static int damage = 40;
    public final static int range = 160;
    public final static int speed = 100;
    public final static int maxHP = 100;
    public final static TowerTypes type = TowerTypes.SINGLE;

    public Spearthrower(TroyTD game, Vector2 position, Vector2 distortion) {
        super(game, position, game.assetManager.get("towers/Spearthrower.png", Texture.class), "Spearthrower",
              distortion, DebugShot.class);
    }
}
