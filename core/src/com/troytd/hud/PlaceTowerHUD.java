package com.troytd.hud;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.ReflectionException;
import com.troytd.game.TroyTD;
import com.troytd.helpers.Stat;
import com.troytd.maps.Map;
import com.troytd.screens.GameScreen;
import com.troytd.towers.Tower;
import com.troytd.towers.TowerTypes;

import java.util.HashMap;

public class PlaceTowerHUD extends SideHUD {
    // hud
    final ImageButton nextButton;
    final ImageButton previousButton;
    final TextButton cancelButton;
    final TextButton placeButton;
    final HorizontalGroup navigationGroup;
    final Label costLabel;
    final Label costAmount;
    final Label towerTypeLabel;
    final Label towerTypeAmount;
    final Label towerName;
    final Label towerDescription;
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
        previousButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                int index = map.towers.indexOf(selectedTower);
                leftTower.setDrawable(middleTower.getDrawable());
                if (map.towers.size() > 2) {
                    if (index > 0) {
                        setSelectedTower(map.towers.get(index - 1));
                        middleTower.setDrawable(new TextureRegionDrawable(
                                game.assetManager.get("towers/" + map.towers.get(index - 1).getSimpleName() + ".png",
                                                      Texture.class)));
                        if (index - 1 > 0) {
                            rightTower.setDrawable(new TextureRegionDrawable(game.assetManager.get(
                                    "towers/" + map.towers.get(index - 2).getSimpleName() + ".png", Texture.class)));
                        } else {
                            rightTower.setDrawable(new TextureRegionDrawable(game.assetManager.get(
                                    "towers/" + map.towers.get(map.towers.size() - 1).getSimpleName() + ".png",
                                    Texture.class)));
                        }
                    } else {
                        setSelectedTower(map.towers.get(map.towers.size() - 1));
                        middleTower.setDrawable(new TextureRegionDrawable(game.assetManager.get(
                                "towers/" + map.towers.get(map.towers.size() - 1).getSimpleName() + ".png",
                                Texture.class)));
                        rightTower.setDrawable(new TextureRegionDrawable(game.assetManager.get(
                                "towers/" + map.towers.get(map.towers.size() - 2).getSimpleName() + ".png",
                                Texture.class)));
                    }
                } else if (map.towers.size() > 1) {
                    if (index != 0) {
                        selectedTower = map.towers.get(0);
                        middleTower.setDrawable(new TextureRegionDrawable(
                                game.assetManager.get("towers/" + map.towers.get(0).getSimpleName() + ".png",
                                                      Texture.class)));
                        rightTower.setDrawable(new TextureRegionDrawable(
                                game.assetManager.get("towers/" + map.towers.get(1).getSimpleName() + ".png",
                                                      Texture.class)));
                    } else {
                        selectedTower = map.towers.get(1);
                        middleTower.setDrawable(new TextureRegionDrawable(
                                game.assetManager.get("towers/" + map.towers.get(1).getSimpleName() + ".png",
                                                      Texture.class)));
                        rightTower.setDrawable(new TextureRegionDrawable(
                                game.assetManager.get("towers/" + map.towers.get(0).getSimpleName() + ".png",
                                                      Texture.class)));
                    }
                } else {
                    selectedTower = map.towers.get(0);
                    middleTower.setDrawable(new TextureRegionDrawable(
                            game.assetManager.get("towers/" + map.towers.get(0).getSimpleName() + ".png",
                                                  Texture.class)));
                    rightTower.setDrawable(new TextureRegionDrawable(
                            game.assetManager.get("towers/" + map.towers.get(0).getSimpleName() + ".png",
                                                  Texture.class)));
                }
            }
        });
        table.add(previousButton)
                .size(icon_size, (float) (icon_size))
                .left()
                .padTop(icon_size / 2f)
                .padBottom(icon_size / 2f);


        // left tower
        leftTower = new Image(new TextureRegionDrawable(
                game.assetManager.get("towers/" + map.towers.get(0).getSimpleName() + ".png", Texture.class)),
                              Scaling.fit);
        table.add(leftTower)
                .size(icon_size, icon_size)
                .padTop(icon_size / 2f)
                .padBottom(icon_size / 2f)
                .padLeft(icon_size / 10f);
        // middle tower
        if (map.towers.size() > 1) {
            middleTower = new Image(new TextureRegionDrawable(
                    game.assetManager.get("towers/" + map.towers.get(1).getSimpleName() + ".png", Texture.class)),
                                    Scaling.fit);
            setSelectedTower(map.towers.get(1));
        } else {
            middleTower = new Image(new TextureRegionDrawable(
                    game.assetManager.get("towers/" + map.towers.get(0).getSimpleName() + ".png", Texture.class)),
                                    Scaling.fit);
            setSelectedTower(map.towers.get(0));
        }
        table.add(middleTower)
                .size(icon_size * 2, icon_size * 2)
                .padLeft(icon_size / 10f)
                .padRight(icon_size / 10f)
                .expandX();
        // right tower
        if (map.towers.size() > 2) {
            rightTower = new Image(new TextureRegionDrawable(
                    game.assetManager.get("towers/" + map.towers.get(2).getSimpleName() + ".png", Texture.class)),
                                   Scaling.fit);
        } else {
            rightTower = new Image(new TextureRegionDrawable(
                    game.assetManager.get("towers/" + map.towers.get(0).getSimpleName() + ".png", Texture.class)),
                                   Scaling.fit);
        }
        table.add(rightTower)
                .size(icon_size, icon_size)
                .padTop(icon_size / 2f)
                .padBottom(icon_size / 2f)
                .padRight(icon_size / 10f);
        // hud - next button
        nextButton = new ImageButton(game.skin, "navigationNext");
        nextButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                int index = map.towers.indexOf(selectedTower);
                int maxIndex = map.towers.size() - 1;
                rightTower.setDrawable(middleTower.getDrawable());
                if (map.towers.size() > 2) {
                    if (index < maxIndex) {
                        setSelectedTower(map.towers.get(index + 1));
                        middleTower.setDrawable(new TextureRegionDrawable(
                                game.assetManager.get("towers/" + map.towers.get(index + 1).getSimpleName() + ".png",
                                                      Texture.class)));
                        if (index + 1 < maxIndex) {
                            leftTower.setDrawable(new TextureRegionDrawable(game.assetManager.get(
                                    "towers/" + map.towers.get(index + 2).getSimpleName() + ".png", Texture.class)));
                        } else {
                            leftTower.setDrawable(new TextureRegionDrawable(
                                    game.assetManager.get("towers/" + map.towers.get(0).getSimpleName() + ".png",
                                                          Texture.class)));
                        }
                    } else {
                        setSelectedTower(map.towers.get(0));
                        middleTower.setDrawable(new TextureRegionDrawable(
                                game.assetManager.get("towers/" + map.towers.get(0).getSimpleName() + ".png",
                                                      Texture.class)));
                        leftTower.setDrawable(new TextureRegionDrawable(
                                game.assetManager.get("towers/" + map.towers.get(1).getSimpleName() + ".png",
                                                      Texture.class)));
                    }
                } else if (map.towers.size() > 1) {
                    if (index != 0) {
                        selectedTower = map.towers.get(0);
                        middleTower.setDrawable(new TextureRegionDrawable(
                                game.assetManager.get("towers/" + map.towers.get(0).getSimpleName() + ".png",
                                                      Texture.class)));
                        leftTower.setDrawable(new TextureRegionDrawable(
                                game.assetManager.get("towers/" + map.towers.get(1).getSimpleName() + ".png",
                                                      Texture.class)));
                    } else {
                        selectedTower = map.towers.get(1);
                        middleTower.setDrawable(new TextureRegionDrawable(
                                game.assetManager.get("towers/" + map.towers.get(1).getSimpleName() + ".png",
                                                      Texture.class)));
                        leftTower.setDrawable(new TextureRegionDrawable(
                                game.assetManager.get("towers/" + map.towers.get(0).getSimpleName() + ".png",
                                                      Texture.class)));
                    }
                } else {
                    selectedTower = map.towers.get(0);
                    middleTower.setDrawable(new TextureRegionDrawable(
                            game.assetManager.get("towers/" + map.towers.get(0).getSimpleName() + ".png",
                                                  Texture.class)));
                    leftTower.setDrawable(new TextureRegionDrawable(
                            game.assetManager.get("towers/" + map.towers.get(0).getSimpleName() + ".png",
                                                  Texture.class)));
                }
            }
        });
        table.add(nextButton)
                .size(icon_size, (float) (icon_size))
                .right()
                .padTop(icon_size / 2f)
                .padBottom(icon_size / 2f);

        // tower name
        towerName = new Label(selectedTower.getSimpleName(), game.skin, "big");
        table.row();
        table.add(new Label("", game.skin));
        table.row();
        table.add(towerName).colspan(5).center();
        table.row();

        TowerTypes towerType;
        try {
            towerType = (TowerTypes) ClassReflection.getField(selectedTower, "type").get(null);
        } catch (ReflectionException e) {
            towerType = Tower.type;
        }

        HashMap<String, Stat> towerStats;
        try {
            towerStats = (HashMap<String, Stat>) ClassReflection.getField(selectedTower.getClass(), "defaultStats")
                    .get(null);
        } catch (ReflectionException e) {
            towerStats = Tower.defaultStats;
        }

        // universal stats

        // tower type label
        table.row();

        towerTypeLabel = new Label("Tower Type", game.skin);
        table.add(towerTypeLabel).colspan(3).left().padTop(10).padLeft(25);

        // tower type amount
        towerTypeAmount = new Label(towerType.toString(), game.skin);
        table.add(towerTypeAmount).colspan(2).right().padRight(25).padTop(10);

        // cost label
        table.row();

        costLabel = new Label("Cost:", game.skin);
        table.add(costLabel).colspan(3).left().padTop(10).padLeft(25);

        // cost amount
        costAmount = new Label(String.valueOf(towerStats.get("cost").getValue()), game.skin);
        table.add(costAmount).colspan(2).right().padRight(25).padTop(10);

        // description label
        table.row();
        towerDescription = new Label(Tower.description, game.skin);
        towerDescription.setAlignment(Align.center);
        towerDescription.setWrap(true);
        table.add(towerDescription).colspan(5).expandX().padTop(10).width(table.getWidth() - 55);

        // hud - place tower button
        navigationGroup = new HorizontalGroup();
        navigationGroup.space(icon_size / 2f);

        table.row();
        table.add(navigationGroup).expandX().colspan(5).padTop(10).padBottom(10).center();

        placeButton = new TextButton("Place", game.skin, "place");
        placeButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                HashMap<String, Stat> towerStats;
                try {
                    towerStats = (HashMap<String, Stat>) ClassReflection.getField(selectedTower, "defaultStats")
                            .get(null);
                } catch (ReflectionException e) {
                    towerStats = Tower.defaultStats;
                }
                if ((int) towerStats.get("cost").getValue() <= gameScreen.money) {
                    gameScreen.money -= (int) towerStats.get("cost").getValue();
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

                try {
                    towerPlace.setTower(
                            (Tower) ClassReflection.getConstructor(getSelectedTower(), TroyTD.class, Vector2.class,
                                                                   Vector2.class, Map.class)
                                    .newInstance(game, towerPlace.place, map.mapDistortion, map), game);
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
            HashMap<String, Stat> towerStats;
            try {
                towerStats = (HashMap<String, Stat>) ClassReflection.getField(selectedTower, "defaultStats").get(null);
            } catch (ReflectionException e) {
                towerStats = Tower.defaultStats;
            }
            costAmount.setText(String.valueOf(towerStats.get("cost").getValue()));
            try {
                towerTypeAmount.setText(ClassReflection.getField(selectedTower, "type").get(null).toString());
            } catch (ReflectionException e) {
                towerTypeAmount.setText(Tower.type.toString());
            }
            try {
                towerDescription.setText(ClassReflection.getField(selectedTower, "description").get(null).toString());
            } catch (ReflectionException e) {
                towerDescription.setText(Tower.description);
            }
            towerName.setText(selectedTower.getSimpleName());
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
