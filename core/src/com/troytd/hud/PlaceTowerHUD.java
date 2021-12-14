package com.troytd.hud;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Scaling;
import com.troytd.game.TroyTD;
import com.troytd.maps.Map;
import com.troytd.screens.GameScreen;
import com.troytd.towers.Tower;

import java.lang.reflect.Constructor;

public class PlaceTowerHUD extends SideHUD {
    // hud
    final ImageButton nextButton;
    final ImageButton previousButton;
    final TextButton cancelButton;
    final TextButton placeButton;
    final HorizontalGroup navigationGroup;
    final Label costLabel;
    final Label costAmount;
    Image leftTower;
    Image rightTower;
    Image middleTower;
    // variables
    private Class<? extends Tower> selectedTower;

    public PlaceTowerHUD(final TroyTD game, final Stage stage, final float topHUDHeight, final Map map,
                         final GameScreen gameScreen) {
        super(game, stage, map, topHUDHeight, "Place Tower", gameScreen);

        final int icon_size = game.settingPreference.getInteger("icon-size");

        // hud - previous button
        previousButton = new ImageButton(game.skin, "navigationPrevious");
        table.add(previousButton)
                .size(icon_size, (float) (icon_size))
                .left()
                .padTop(icon_size / 2f)
                .padBottom(icon_size / 2f);


        // left tower
        leftTower = new Image(new TextureRegionDrawable(
                game.assetManager.get("towers/" + map.towers[0].getSimpleName() + ".png", Texture.class)), Scaling.fit);
        table.add(leftTower)
                .size(icon_size, icon_size)
                .padTop(icon_size / 2f)
                .padBottom(icon_size / 2f)
                .padLeft(icon_size / 10f);
        // middle tower
        if (map.towers.length > 1) {
            middleTower = new Image(new TextureRegionDrawable(
                    game.assetManager.get("towers/" + map.towers[1].getSimpleName() + ".png", Texture.class)),
                                    Scaling.fit);
            setSelectedTower(map.towers[1]);
        } else {
            middleTower = new Image(new TextureRegionDrawable(
                    game.assetManager.get("towers/" + map.towers[0].getSimpleName() + ".png", Texture.class)),
                                    Scaling.fit);
            setSelectedTower(map.towers[0]);
        }
        table.add(middleTower)
                .size(icon_size * 2, icon_size * 2)
                .padLeft(icon_size / 10f)
                .padRight(icon_size / 10f)
                .expandX();
        // right tower
        if (map.towers.length > 2) {
            rightTower = new Image(new TextureRegionDrawable(
                    game.assetManager.get("towers/" + map.towers[2].getSimpleName() + ".png", Texture.class)),
                                   Scaling.fit);
        } else {
            rightTower = new Image(new TextureRegionDrawable(
                    game.assetManager.get("towers/" + map.towers[0].getSimpleName() + ".png", Texture.class)),
                                   Scaling.fit);
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

        // cost label
        table.row();

        costLabel = new Label("Cost:", game.skin);
        table.add(costLabel);

        // cost amount
        String tmp_text = "";
        try {
            tmp_text = selectedTower.getField("cost").getInt(null) + "";
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            tmp_text = "";
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            tmp_text = "";
        }
        costAmount = new Label(tmp_text, game.skin);
        table.add(costAmount).colspan(4).right();

        // hud - place tower button
        navigationGroup = new HorizontalGroup();
        navigationGroup.space(icon_size / 2f);

        table.row();
        table.add(navigationGroup).expandX().colspan(5).padTop(10).padBottom(10).center();

        placeButton = new TextButton("Place", game.skin, "place");
        placeButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                try {
                    if (selectedTower.getField("cost").getInt(null) <= gameScreen.money) {
                        gameScreen.money -= selectedTower.getField("cost").getInt(null);
                    } else {
                        Dialog errorDialog = new Dialog("", game.skin, "error") {
                            @Override
                            public void result(Object object) {
                                stage.getActors().removeValue(this, true);
                            }
                        };
                        errorDialog.text("Not enough money", game.skin.get("error", Label.LabelStyle.class));
                        errorDialog.button("OK", true, game.skin.get("error", TextButton.TextButtonStyle.class));
                        stage.addActor(errorDialog);
                        errorDialog.show(stage);
                        close();
                        return;
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                }
                try {
                    Constructor<? extends Tower> ctor = getSelectedTower().getConstructor(TroyTD.class, Vector2.class,
                                                                                          Vector2.class);
                    towerPlace.setTower(ctor.newInstance(game, towerPlace.place, map.mapDistortion), game);
                } catch (Exception e) {
                    e.printStackTrace();
                    Dialog errorDialog = new Dialog("", game.skin, "error") {
                        @Override
                        public void result(Object object) {
                            stage.getActors().removeValue(this, true);
                        }
                    };
                    errorDialog.text("An error occurred!", game.skin.get("error", Label.LabelStyle.class));
                    errorDialog.button("OK", true, game.skin.get("error", TextButton.TextButtonStyle.class));
                    stage.addActor(errorDialog);
                    errorDialog.show(stage);
                }
                close();
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

    private Class<? extends Tower> getSelectedTower() {
        return selectedTower;
    }

    private void setSelectedTower(Class<? extends Tower> selectedTower) {
        if (costAmount != null) {
            try {
                costAmount.setText(selectedTower.getField("cost").getInt(null) + "");
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        this.selectedTower = selectedTower;
    }

    /**
     * gets called when the tower place is updated
     * used to update the labels
     */
    @Override
    protected void updatedTowerPlace() {
    }
}
