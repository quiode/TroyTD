package com.troytd.towers.units;

import com.badlogic.gdx.graphics.Texture;
import com.troytd.game.TroyTD;
import com.troytd.towers.Tower;

public class DebugUnit extends Unit {
    public DebugUnit(TroyTD game, Tower tower) {
        super(UnitType.MELEE, game, tower);
    }
}
