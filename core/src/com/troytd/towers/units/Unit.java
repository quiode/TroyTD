package com.troytd.towers.units;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.troytd.game.TroyTD;
import com.troytd.towers.Tower;

import java.util.ArrayList;

public abstract class Unit {
    private final UnitType type;
    private final TroyTD game;
    private final Sprite sprite;
    private final Tower tower;
    private final int hp;


    public Unit(UnitType type, TroyTD game, Tower tower) {
        this.type = type;
        this.game = game;
        this.tower = tower;

        this.hp = (Integer) tower.getStat("maxHP").getValue();

        sprite = new Sprite(
                game.assetManager.get("units/" + tower.getClass().getSimpleName() + "Unit" + ".png", Texture.class));
        sprite.setSize(Tower.getSize(game) / 5f, Tower.getSize(game) / 5f);
        sprite.setPosition(tower.getPosition().x + tower.getRect().width / 2f - sprite.getWidth() / 2f,
                           tower.getPosition().y + tower.getRect().height * 1.1f);
    }

    public void draw() {
        sprite.draw(game.batch);
    }

    public void update(ArrayList<Unit> units) {
        if (hp <= 0) {
            units.remove(this);
        }
    }

    public Tower getTower() {
        return tower;
    }

    public UnitType getType() {
        return type;
    }
}