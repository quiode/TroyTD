package com.troytd.towers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.troytd.game.TroyTD;

public class Spearthrower extends Tower{
    public final static int cost = 80;
    public final static int damage = 40;
    public final static int range = 160;
    public final static int speed = 100;
    public final static int maxHP = 100;
    public Spearthrower(TroyTD game, Vector2 position, Texture texture, String name, TowerTypes type,
                        Vector2 distortion) {
        super(game, position, new Texture("towers/range.jpg"), "Spearthrower", TowerTypes.SINGLE_TARGET, distortion);
    }
}
