package com.troytd.hud;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.troytd.game.TroyTD;
import com.troytd.maps.TowerPlace;

public class PlaceTowerHUD {
    // global variables
    final TroyTD game;
    final Stage stage;
    // hud
    final Table table;
    //final ImageButton closeButton;
    // variables
    TowerPlace towerPlace;

    public PlaceTowerHUD(final TroyTD game, final Stage stage, final float topHUDHeight) {
        this.game = game;
        this.stage = stage;

        // hud
        table = new Table();
        table.setSize(stage.getWidth() / 3, stage.getHeight() - topHUDHeight);
        table.setPosition(stage.getWidth() - table.getWidth(), 0);
        table.debug();
        table.setVisible(false);
        stage.addActor(table);
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

    /**
     * Shows a HUD to place a new tower.
     *
     * @param selectedTowerPlace the tower place which was selected
     */
    public void show(TowerPlace selectedTowerPlace) {
        this.towerPlace = selectedTowerPlace;
        table.setVisible(true);
    }

    /**
     * <strong>Clean-up</strong>
     * <p>removes unused objects from the stage</p>
     */
    public void dispose() {
        stage.getActors().removeValue(table, true);
    }
}
