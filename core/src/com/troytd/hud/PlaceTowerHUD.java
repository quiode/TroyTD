package com.troytd.hud;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.troytd.game.TroyTD;
import com.troytd.maps.TowerPlace;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

public class PlaceTowerHUD {
    // global variables
    final TroyTD game;
    final Stage stage;
    // hud
    final Table table;
    final ImageButton closeButton;
    final Label placeTowerLabel;
    final ImageButton nextButton;
    final ImageButton previousButton;
    // variables
    TowerPlace towerPlace;

    public PlaceTowerHUD(final TroyTD game, final Stage stage, final float topHUDHeight) {
        this.game = game;
        this.stage = stage;

        // hud - table
        table = new Table();
        table.setSize(stage.getWidth() / 3, stage.getHeight() - (topHUDHeight + 5));
        table.setPosition(stage.getWidth(), 0);
        table.pad(5);
        table.setBackground(game.skin.getDrawable("grey"));
        table.setVisible(false);
        stage.addActor(table);

        // hud - place tower label
        placeTowerLabel = new Label("Place Tower", game.skin, "big");
        table.add(placeTowerLabel).expandX();

        // hud - close button
        closeButton = new ImageButton(game.skin, "close");
        closeButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                close();
            }
        });
        table.add(closeButton).size(game.settingPreference.getInteger("icon-size"), game.settingPreference.getInteger("icon-size"));
        table.top().right();
        table.row();

        // hud - previous button
        previousButton = new ImageButton(game.skin, "navigationPrevious");
        table.add(previousButton).size(game.settingPreference.getInteger("icon-size"), game.settingPreference.getInteger("icon-size"));

        // hud - next button
        nextButton = new ImageButton(game.skin, "navigationNext");
        table.add(nextButton).size(game.settingPreference.getInteger("icon-size"), game.settingPreference.getInteger("icon-size"));

        //table.debug();
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
        table.addAction(sequence(visible(true), moveTo(stage.getWidth() - table.getWidth(), 0, 1 / 3f)));
    }

    /**
     * <strong>Clean-up</strong>
     * <p>removes unused objects from the stage</p>
     */
    public void dispose() {
        stage.getActors().removeValue(table, true);
    }

    /**
     * closes the table
     */
    public void close() {
        table.addAction(sequence(moveTo(stage.getWidth(), 0, 1 / 3f), visible(false)));
    }
}
