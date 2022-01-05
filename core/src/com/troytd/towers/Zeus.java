package com.troytd.towers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.troytd.game.TroyTD;
import com.troytd.shots.DebugShot;

public class Zeus extends Tower {
    public final static int cost = 130;
    public final static int damage = 50;
    public final static int range = 120;
    public final static int speed = 100;
    public final static int maxHP = 100;
    public final static float atspeed = 0.4f;
    public final static int AOE = 4;

    public Zeus(TroyTD game, Vector2 position, Texture texture, String name, TowerTypes type, Vector2 distortion) {
        super(game, position, new Texture("towers/Zeus.jpg"), "Zeus", TowerTypes.AOE, distortion, DebugShot.class);
    }
}
