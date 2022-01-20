package com.troytd.hud;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
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
    final Label damageLabel;
    final Label damageAmount;
    final Label rangeLabel;
    final Label rangeAmount;
    final Label towerTypeLabel;
    final Label towerTypeAmount;
    final Label HPLabel;
    final Label HPAmount;
    final Label towerName;
    final Label atSpeedLabel;
    final Label atSpeedAmount;
    final Label range2Label;
    final Label range2Amount;
    final Label lifeDurationLabel;
    final Label lifeDurationAmount;
    final Label enemyAmountLabel;
    final Label enemyAmountAmount;
    final Label unitAmountLabel;
    final Label unitAmountAmount;
    final Label speedLabel;
    final Label speedAmount;
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

        String tmp_text = "";

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

        // damage label
        table.row();

        damageLabel = new Label("Damage:", game.skin);
        table.add(damageLabel).colspan(3).left().padTop(10).padLeft(25);

        // damage amount
        damageAmount = new Label(String.valueOf(towerStats.get("damage").getValue()), game.skin);
        table.add(damageAmount).colspan(2).right().padRight(25).padTop(10);

        // range label
        table.row();

        rangeLabel = new Label("Range:", game.skin);
        table.add(rangeLabel).colspan(3).left().padTop(10).padLeft(25);

        // range amount
        rangeAmount = new Label(String.valueOf(towerStats.get("range").getValue()), game.skin);
        table.add(rangeAmount).colspan(2).right().padRight(25).padTop(10);

        // hp label
        table.row();

        HPLabel = new Label("HP:", game.skin);
        table.add(HPLabel).colspan(3).left().padTop(10).padLeft(25);

        // hp amount
        HPAmount = new Label(String.valueOf(towerStats.get("maxHP").getValue()), game.skin);
        table.add(HPAmount).colspan(2).right().padRight(25).padTop(10);

        // attack speed label
        table.row();

        atSpeedLabel = new Label("Attack Speed:", game.skin);
        table.add(atSpeedLabel).colspan(3).left().padTop(10).padLeft(25);

        // attack speed amount
        atSpeedAmount = new Label(String.valueOf(towerStats.get("atspeed").getValue()), game.skin);
        table.add(atSpeedAmount).colspan(2).right().padRight(25).padTop(10);

        // special attributes

        // unitAmount label
        table.row();

        unitAmountLabel = new Label("Unit Amount:", game.skin);
        table.add(unitAmountLabel).colspan(3).left().padTop(10).padLeft(25);

        // unitAmount amount
        unitAmountAmount = new Label(String.valueOf(towerStats.get("unitAmount").getValue()), game.skin);
        table.add(unitAmountAmount).colspan(2).right().padRight(25).padTop(10);
        // speed label
        table.row();

        speedLabel = new Label("Speed:", game.skin);
        table.add(speedLabel).colspan(3).left().padTop(10).padLeft(25);

        // speed amount
        speedAmount = new Label(String.valueOf(towerStats.get("speed").getValue()), game.skin);
        table.add(speedAmount).colspan(2).right().padRight(25).padTop(10);
        // range2 label
        table.row();

        range2Label = new Label("Range2:", game.skin);
        table.add(range2Label).colspan(3).left().padTop(10).padLeft(25);

        // range2 amount
        range2Amount = new Label(String.valueOf(towerStats.get("range2").getValue()), game.skin);
        table.add(range2Amount).colspan(2).right().padRight(25).padTop(10);

        // lifeDuration label
        table.row();

        lifeDurationLabel = new Label("Life Duration:", game.skin);
        table.add(lifeDurationLabel).colspan(3).left().padTop(10).padLeft(25);

        // lifeDuration amount
        lifeDurationAmount = new Label(String.valueOf(towerStats.get("lifeDuration").getValue()), game.skin);
        table.add(lifeDurationAmount).colspan(2).right().padRight(25).padTop(10);

        // enemyAmount label
        table.row();

        enemyAmountLabel = new Label("Enemy Amount:", game.skin);
        table.add(enemyAmountLabel).colspan(3).left().padTop(10).padLeft(25);

        // enemyAmount amount
        enemyAmountAmount = new Label(String.valueOf(towerStats.get("enemyAmount").getValue()), game.skin);
        table.add(enemyAmountAmount).colspan(2).right().padRight(25).padTop(10);


        // hud - place tower button
        navigationGroup = new HorizontalGroup();
        navigationGroup.space(icon_size / 2f);

        table.row();
        table.add(navigationGroup).expandX().colspan(5).padTop(10).padBottom(10).center();

        placeButton = new TextButton("Place", game.skin, "place");
        final HashMap<String, Stat> finalTowerStats = towerStats;
        placeButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if ((int) finalTowerStats.get("cost").getValue() <= gameScreen.money) {
                    gameScreen.money -= (int) finalTowerStats.get("cost").getValue();
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
                                                                   Vector2.class)
                                    .newInstance(game, towerPlace.place, map.mapDistortion), game);
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
            damageAmount.setText(String.valueOf(towerStats.get("damage").getValue()));
            rangeAmount.setText(String.valueOf(towerStats.get("range").getValue()));
            speedAmount.setText(String.valueOf(towerStats.get("speed").getValue()));
            HPAmount.setText(String.valueOf(towerStats.get("maxHP").getValue()));
            atSpeedAmount.setText(String.valueOf(towerStats.get("atspeed").getValue()));
            try {
                towerTypeAmount.setText(ClassReflection.getField(selectedTower, "type").get(null).toString());
            } catch (ReflectionException e) {
                towerTypeAmount.setText(Tower.type.toString());
            }
            range2Amount.setText(String.valueOf(towerStats.get("range2").getValue()));
            unitAmountAmount.setText(String.valueOf(towerStats.get("unitAmount").getValue()));
            enemyAmountAmount.setText(String.valueOf(towerStats.get("enemyAmount").getValue()));
            lifeDurationAmount.setText(String.valueOf(towerStats.get("lifeDuration").getValue()));
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
