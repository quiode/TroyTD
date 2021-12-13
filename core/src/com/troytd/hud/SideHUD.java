package com.troytd.hud;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.troytd.game.TroyTD;
import com.troytd.maps.Map;
import com.troytd.maps.TowerPlace;
import com.troytd.screens.GameScreen;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

/**
 * <strong>The Class SideHUD</strong>
 * <p>Template for any kind of HUD that appears on the right side of the screen.</p>
 */
public abstract class SideHUD {
    // global variables
    final TroyTD game;
    final Stage stage;
    final Map map;
    final GameScreen gameScreen;
    // hud
    final Table table;
    final Label placeTowerLabel;
    final ImageButton closeButton;
    // variables
    TowerPlace towerPlace;

    public SideHUD(final TroyTD game, final Stage stage, final Map map, final float topHUDHeight, String title,
                   final GameScreen gameScreen) {
        this.game = game;
        this.stage = stage;
        this.map = map;
        this.gameScreen = gameScreen;

        final int icon_size = game.settingPreference.getInteger("icon-size");

        // hud - table
        table = new Table();
        table.setSize(icon_size * 7, stage.getHeight() - (topHUDHeight + 5));
        table.setPosition(stage.getWidth(), 0);
        table.pad(5);
        table.setBackground(game.skin.getDrawable("grey"));
        table.setVisible(false);
        stage.addActor(table);

        // hud - place tower label
        placeTowerLabel = new Label(title, game.skin, "big");
        table.add(placeTowerLabel).expandX().colspan(4);

        // hud - close button
        closeButton = new ImageButton(game.skin, "close");
        closeButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                close();
            }
        });
        table.add(closeButton).size(icon_size, icon_size);
        table.top().right();
        table.row();
    }

    /**
     * Shows a HUD to place a new tower.
     *
     * @param selectedTowerPlace the tower place which was selected
     */
    public void show(final TowerPlace selectedTowerPlace) {
        towerPlace = selectedTowerPlace;
        updatedTowerPlace();
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

    /**
     * gets called when the tower place is updated
     * used to update the labels
     */
    protected abstract void updatedTowerPlace();

}
