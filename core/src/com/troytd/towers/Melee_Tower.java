package com.troytd.towers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.troytd.game.TroyTD;
import com.troytd.shots.DebugShot;

public class Melee_Tower extends Tower {
    public final static int cost = 100;
    public final static int damage = 15;
    public final static int range = 150;
    public final static int speed = 100;
    public final static int maxHP = 100;
    public final static int atspeed = 200;

    public Melee_Tower(TroyTD game, Vector2 position, Texture texture, String name, TowerTypes type,
                       Vector2 distortion) {
        super(game, position, new Texture("towers/range.jpg"), "Swordsmen", TowerTypes.MELEE, distortion,
              DebugShot.class);
    }


}

