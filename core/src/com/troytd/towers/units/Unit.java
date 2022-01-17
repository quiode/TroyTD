package com.troytd.towers.units;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.troytd.game.TroyTD;

public abstract class Unit {
    final UnitType type;
    final TroyTD game;
    final Sprite sprite;

    public Unit(UnitType type, TroyTD game, Texture texture) {
        this.type = type;
        this.game = game;

        sprite = new Sprite(texture);
    }
}