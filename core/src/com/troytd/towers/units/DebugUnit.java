package com.troytd.towers.units;

import com.troytd.game.TroyTD;
import com.troytd.towers.Tower;

import java.util.ArrayList;

public class DebugUnit extends Unit {
    public DebugUnit(TroyTD game, Tower tower, ArrayList<Unit> units) {
        super(UnitType.MELEE, game, tower, units);
    }
}
