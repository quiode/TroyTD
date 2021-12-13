package com.troytd.hud;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.troytd.game.TroyTD;
import com.troytd.maps.Map;

public class InfoTowerHUD extends SideHUD {
    public InfoTowerHUD(TroyTD game, Stage stage, Map map, float topHUDHeight) {
        super(game, stage, map, topHUDHeight, "Tower Information");
    }

    /**
     * <li>adds the assets to the asset manager</li>
     * <li>needs to be called before the class is initialized</li>
     * <li>asset manager has to have finished loading to initialize the class</li>
     *
     * @param game the game instance
     */
    public static void loadAssets(final TroyTD game) {
    }
}
