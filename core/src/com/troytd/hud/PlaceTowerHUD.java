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
import com.troytd.maps.Map;
import com.troytd.screens.GameScreen;
import com.troytd.towers.Tower;

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
    final Label speedLabel;
    final Label speedAmount;
    final Label HPLabel;
    final Label HPAmount;
    final Label towerName;
    final Label atSpeedLabel;
    final Label atSpeedAmount;
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

        // cost label
        table.row();

        costLabel = new Label("Cost:", game.skin);
        table.add(costLabel).colspan(2).left();

        // cost amount
        String tmp_text = "";
        try {
            tmp_text = (int) ClassReflection.getField(selectedTower, "cost").get(null) + "";
        } catch (ReflectionException e) {
            e.printStackTrace();
            tmp_text = "";
        }
        costAmount = new Label(tmp_text, game.skin);
        table.add(costAmount).colspan(3).right();

        // damage label
        table.row();

        damageLabel = new Label("Damage:", game.skin);
        table.add(damageLabel).colspan(2).left();

        // damage amount
        tmp_text = "";
        try {
            tmp_text = (int) ClassReflection.getField(selectedTower, "damage").get(null) + "";
        } catch (ReflectionException e) {
            e.printStackTrace();
            tmp_text = "";
        }
        damageAmount = new Label(tmp_text, game.skin);
        table.add(damageAmount).colspan(3).right();

        // range label
        table.row();

        rangeLabel = new Label("Range:", game.skin);
        table.add(rangeLabel).colspan(2).left();

        // range amount
        tmp_text = "";
        try {
            tmp_text = (int) ClassReflection.getField(selectedTower, "range").get(null) + "";
        } catch (ReflectionException e) {
            e.printStackTrace();
            tmp_text = "";
        }
        rangeAmount = new Label(tmp_text, game.skin);
        table.add(rangeAmount).colspan(3).right();

        // speed label
        table.row();

        speedLabel = new Label("Speed:", game.skin);
        table.add(speedLabel).colspan(2).left();

        // speed amount
        tmp_text = "";
        try {
            tmp_text = (int) ClassReflection.getField(selectedTower, "speed").get(null) + "";
        } catch (ReflectionException e) {
            e.printStackTrace();
            tmp_text = "";
        }
        speedAmount = new Label(tmp_text, game.skin);
        table.add(speedAmount).colspan(3).right();

        // hp label
        table.row();

        HPLabel = new Label("HP:", game.skin);
        table.add(HPLabel).colspan(2).left();

        // hp amount
        tmp_text = "";
        try {
            tmp_text = (int) ClassReflection.getField(selectedTower, "maxHP").get(null) + "";
        } catch (ReflectionException e) {
            e.printStackTrace();
            tmp_text = "";
        }
        HPAmount = new Label(tmp_text, game.skin);
        table.add(HPAmount).colspan(3).right();

        // attack speed label
        table.row();

        atSpeedLabel = new Label("Attack Speed:", game.skin);
        table.add(atSpeedLabel).colspan(2).left();

        // attack speed label
        tmp_text = "";
        try {
            tmp_text = (int) ClassReflection.getField(selectedTower, "atspeed").get(null) + "";
        } catch (ReflectionException e) {
            e.printStackTrace();
            tmp_text = "";
        }
        atSpeedAmount = new Label(tmp_text, game.skin);
        table.add(atSpeedAmount).colspan(3).right();


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
                    if ((int) ClassReflection.getField(selectedTower, "cost").get(null) <= gameScreen.money) {
                        gameScreen.money -= (int) ClassReflection.getField(selectedTower, "cost").get(null);
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
                } catch (ReflectionException e) {
                    e.printStackTrace();
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
            try {
                costAmount.setText((int) ClassReflection.getField(selectedTower, "cost").get(null) + "");
                damageAmount.setText((int) ClassReflection.getField(selectedTower, "damage").get(null) + "");
                rangeAmount.setText((int) ClassReflection.getField(selectedTower, "range").get(null) + "");
                speedAmount.setText((int) ClassReflection.getField(selectedTower, "speed").get(null) + "");
                HPAmount.setText((int) ClassReflection.getField(selectedTower, "maxHP").get(null) + "");
                atSpeedAmount.setText((int) ClassReflection.getField(selectedTower, "atspeed").get(null) + "");
                towerName.setText(selectedTower.getSimpleName());
            } catch (ReflectionException e) {
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
