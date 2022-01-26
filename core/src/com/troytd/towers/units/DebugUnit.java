package com.troytd.towers.units;

import com.troytd.game.TroyTD;
import com.troytd.maps.Map;
import com.troytd.towers.Tower;

public class DebugUnit extends Unit {
    public DebugUnit(TroyTD game, Tower tower, Map map) {
        super(UnitType.MELEE, game, tower, map);
    }
}
