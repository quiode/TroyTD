package com.troytd.hud;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Scaling;
import com.troytd.game.TroyTD;
import com.troytd.maps.TowerPlace;
import com.troytd.towers.Tower;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

public class PlaceTowerHUD {
    // global variables
    final TroyTD game;
    final Stage stage;
    final Class<? extends Tower>[] towers;
    // hud
    final Table table;
    final ImageButton closeButton;
    final Label placeTowerLabel;
    final ImageButton nextButton;
    final ImageButton previousButton;
    final TextButton cancelButton;
    final TextButton placeButton;
    final HorizontalGroup navigationGroup;
    Image leftTower;
    Image rightTower;
    Image middleTower;
    // variables
    TowerPlace towerPlace;
    Tower selectedTower;

    public PlaceTowerHUD(final TroyTD game, final Stage stage, final float topHUDHeight,
                         final Class<? extends Tower>[] towers) {
        this.game = game;
        this.stage = stage;
        this.towers = towers;

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
        placeTowerLabel = new Label("Place Tower", game.skin, "big");
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

        // hud - previous button
        previousButton = new ImageButton(game.skin, "navigationPrevious");
        table.add(previousButton)
                .size(icon_size, (float) (icon_size))
                .left()
                .padTop(icon_size / 2f)
                .padBottom(icon_size / 2f);


        // left tower
        leftTower = new Image(new TextureRegionDrawable(
                game.assetManager.get("towers/" + towers[0].getSimpleName() + ".png", Texture.class)), Scaling.fit);
        table.add(leftTower)
                .size(icon_size, icon_size)
                .padTop(icon_size / 2f)
                .padBottom(icon_size / 2f)
                .padLeft(icon_size / 10f);
        // middle tower
        if (towers.length > 1) {
            middleTower = new Image(new TextureRegionDrawable(
                    game.assetManager.get("towers/" + towers[1].getSimpleName() + ".png", Texture.class)), Scaling.fit);
        } else {
            middleTower = new Image(new TextureRegionDrawable(
                    game.assetManager.get("towers/" + towers[0].getSimpleName() + ".png", Texture.class)), Scaling.fit);
        }
        table.add(middleTower)
                .size(icon_size * 2, icon_size * 2)
                .padLeft(icon_size / 10f)
                .padRight(icon_size / 10f)
                .expandX();
        // right tower
        if (towers.length > 2) {
            rightTower = new Image(new TextureRegionDrawable(
                    game.assetManager.get("towers/" + towers[2].getSimpleName() + ".png", Texture.class)), Scaling.fit);
        } else {
            rightTower = new Image(new TextureRegionDrawable(
                    game.assetManager.get("towers/" + towers[0].getSimpleName() + ".png", Texture.class)), Scaling.fit);
        }
        table.add(rightTower)
                .size(icon_size, icon_size)
                .padTop(icon_size / 2f)
                .padBottom(icon_size / 2f)
                .padRight(icon_size / 10f);
        // hud - next button
        nextButton = new ImageButton(game.skin, "navigationNext");
        table.add(nextButton)
                .size(icon_size, (float) (icon_size))
                .right()
                .padTop(icon_size / 2f)
                .padBottom(icon_size / 2f);

        // hud - place tower button
        navigationGroup = new HorizontalGroup();
        navigationGroup.space(icon_size / 2f);

        table.row();
        table.add(navigationGroup).expandX().colspan(5).padTop(10).padBottom(10).center();

        placeButton = new TextButton("Place", game.skin, "place");
        placeButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // TODO: set towerPlace tower to selected tower
            }
        });
        placeButton.pad(5);
        navigationGroup.addActor(placeButton);

        // hud - cancel button
        cancelButton = new TextButton("Cancel", game.skin, "cancel");
        cancelButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                close();
            }
        });
        cancelButton.pad(5);
        navigationGroup.addActor(cancelButton);

        table.debug();
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
    public void show(final TowerPlace selectedTowerPlace) {
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
