package com.troytd.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
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

public class InfoTowerHUD extends SideHUD {
    private final Label lifeDurationAmount;
    private final Label enemyAmountAmount;
    private final Label unitAmountAmount;
    private final Image towerImage;
    private final TextField towerName;
    private final Label damageAmount;
    private final Label rangeAmount;
    private final Label speedAmount;
    private final Label maxHPAmount;
    private final Label killsAmount;
    private final Label totalDamageAmount;
    private final Label atspeedAmount;
    private final Label typeAmount;
    private final Label range2Amount;
    private final Label refundAmount;
    private final Label range3Amount;

    private final ImageButton upgradeDamageButton;
    private final ImageButton upgradeRangeButton;
    private final ImageButton upgradeSpeedButton;
    private final ImageButton upgradeHpButton;
    private final ImageButton upgradeAtSpeedButton;
    private final ImageButton upgradeRange2Button;
    private final ImageButton upgradelifeDurationButton;
    private final ImageButton upgradeEnemyAmountButton;
    private final ImageButton upgradeUnitAmountButton;
    private final ImageButton upgradeRange3Button;

    private final TextButton setHomeLocation;

    public InfoTowerHUD(final TroyTD game, final Stage stage, Map map, float topHUDHeight,
                        final GameScreen gameScreen) {
        super(game, stage, map, topHUDHeight, "Tower Stats", gameScreen);

        final int iconSize = game.settingPreference.getInteger("icon-size");

        // tower preview image
        towerImage = new Image(game.assetManager.get("towers/NoTower.png", Texture.class));

        table.add(towerImage).width(topHUDHeight * 0.8f).height(topHUDHeight * 0.8f).pad(topHUDHeight * 0.1f);
        // tower name
        towerName = new TextField("No Tower", game.skin, "small");
        towerName.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                towerPlace.getTower().name = towerName.getText();
            }
        });
        table.add(towerName).colspan(4).expandX();
        table.row();

        // stats
        // type
        table.row();
        final Label type = new Label("Type: ", game.skin);
        typeAmount = new Label("0", game.skin);
        table.add(type).colspan(3).left().padTop(10).padLeft(25);
        table.add(typeAmount).colspan(1).right().padTop(10);
        // kills
        table.row().colspan(3).left().padTop(10).padLeft(25);
        final Label kills = new Label("Kills: ", game.skin);
        killsAmount = new Label("0", game.skin);
        table.add(kills).colspan(3).left().padTop(10).padLeft(25);
        table.add(killsAmount).colspan(1).right().padTop(10);
        // total damage
        table.row();
        final Label totalDamage = new Label("Total Damage: ", game.skin);
        totalDamageAmount = new Label("0", game.skin);
        table.add(totalDamage).colspan(3).left().padTop(10).padLeft(25);
        table.add(totalDamageAmount).colspan(1).right().padTop(10);

        // variable stats

        // damage
        table.row();
        final Label damage = new Label("Damage: ", game.skin);
        table.add(damage).colspan(3).left().padTop(10).padLeft(25);

        upgradeDamageButton = new ImageButton(game.skin, "upgrade");
        upgradeDamageButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (gameScreen.money < (towerPlace.getTower()
                        .getStat("damage")
                        .getLevel() + 1) * 0.35 * Tower.upgradeCost + Tower.upgradeCost) return;
                gameScreen.money -= (towerPlace.getTower()
                        .getStat("damage")
                        .getLevel() + 1) * 0.35 * Tower.upgradeCost + Tower.upgradeCost;
                towerPlace.getTower()
                        .setStat("damage", new Stat<>("damage", (int) ((Integer) towerPlace.getTower()
                                .getStat("damage")
                                .getValue() + (Integer) towerPlace.getTower().getStat("damage").getValue() * 0.1f),
                                                      towerPlace.getTower().getStat("damage").getLevel() + 1));
            }
        });
        damageAmount = new Label("0", game.skin);

        table.add(damageAmount).colspan(1).right().padTop(10);
        table.add(upgradeDamageButton).colspan(1).center().padTop(10).size(iconSize / 2f);
        // range
        table.row();
        final Label range = new Label("Tower Range: ", game.skin);
        table.add(range).colspan(3).left().padTop(10).padLeft(25);

        upgradeRangeButton = new ImageButton(game.skin, "upgrade");
        upgradeRangeButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (gameScreen.money < (towerPlace.getTower()
                        .getStat("range")
                        .getLevel() + 1) * 0.35 * Tower.upgradeCost + Tower.upgradeCost) return;
                gameScreen.money -= (towerPlace.getTower()
                        .getStat("range")
                        .getLevel() + 1) * 0.35 * Tower.upgradeCost + Tower.upgradeCost;
                towerPlace.getTower()
                        .setStat("range", new Stat<>("range", (int) ((Integer) towerPlace.getTower()
                                .getStat("range")
                                .getValue() + (Integer) towerPlace.getTower().getStat("range").getValue() * 0.1f),
                                                     towerPlace.getTower().getStat("range").getLevel() + 1));
            }
        });
        rangeAmount = new Label("0", game.skin);

        table.add(rangeAmount).colspan(1).right().padTop(10);
        table.add(upgradeRangeButton).colspan(1).center().padTop(10).size(iconSize / 2f);
        // speed
        table.row();
        final Label speed = new Label("Speed: ", game.skin);
        table.add(speed).colspan(3).left().padTop(10).padLeft(25);

        upgradeSpeedButton = new ImageButton(game.skin, "upgrade");
        upgradeSpeedButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (gameScreen.money < (towerPlace.getTower()
                        .getStat("speed")
                        .getLevel() + 1) * 0.35 * Tower.upgradeCost + Tower.upgradeCost) return;
                gameScreen.money -= (towerPlace.getTower()
                        .getStat("speed")
                        .getLevel() + 1) * 0.35 * Tower.upgradeCost + Tower.upgradeCost;
                towerPlace.getTower()
                        .setStat("speed", new Stat<>("speed", (int) ((Integer) towerPlace.getTower()
                                .getStat("speed")
                                .getValue() + (Integer) towerPlace.getTower().getStat("speed").getValue() * 0.1f),
                                                     towerPlace.getTower().getStat("speed").getLevel() + 1));
            }
        });
        speedAmount = new Label("0", game.skin);

        table.add(speedAmount).colspan(1).right().padTop(10);
        table.add(upgradeSpeedButton).colspan(1).center().padTop(10).size(iconSize / 2f);
        // ats speed
        table.row();
        final Label atspeed = new Label("Attack Speed: ", game.skin);
        table.add(atspeed).colspan(3).left().padTop(10).padLeft(25);

        upgradeAtSpeedButton = new ImageButton(game.skin, "upgrade");
        upgradeAtSpeedButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (gameScreen.money < (towerPlace.getTower()
                        .getStat("atspeed")
                        .getLevel() + 1) * 0.35 * Tower.upgradeCost + Tower.upgradeCost) return;
                gameScreen.money -= (towerPlace.getTower()
                        .getStat("atspeed")
                        .getLevel() + 1) * 0.35 * Tower.upgradeCost + Tower.upgradeCost;
                towerPlace.getTower()
                        .setStat("atspeed", new Stat<>("atspeed", (int) ((Integer) towerPlace.getTower()
                                .getStat("atspeed")
                                .getValue() + (Integer) towerPlace.getTower().getStat("atspeed").getValue() * 0.1f),
                                                       towerPlace.getTower().getStat("atspeed").getLevel() + 1));
            }
        });
        atspeedAmount = new Label("0", game.skin);

        table.add(atspeedAmount).colspan(1).right().padTop(10);
        table.add(upgradeAtSpeedButton).colspan(1).center().padTop(10).size(iconSize / 2f);
        // enemy amount
        table.row();
        final Label enemyAmount = new Label("Enemy Amount: ", game.skin);
        table.add(enemyAmount).colspan(3).left().padTop(10).padLeft(25);

        upgradeEnemyAmountButton = new ImageButton(game.skin, "upgrade");
        upgradeEnemyAmountButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (gameScreen.money < (towerPlace.getTower()
                        .getStat("enemyAmount")
                        .getLevel() + 1) * 0.7 * Tower.upgradeCost + Tower.upgradeCost * 2 + Tower.upgradeCost) return;
                gameScreen.money -= (towerPlace.getTower()
                        .getStat("enemyAmount")
                        .getLevel() + 1) * 0.7 * Tower.upgradeCost + Tower.upgradeCost * 2;
                towerPlace.getTower()
                        .setStat("enemyAmount", new Stat<>("enemyAmount", (Integer) towerPlace.getTower()
                                .getStat("enemyAmount")
                                .getValue() + 1, towerPlace.getTower().getStat("enemyAmount").getLevel() + 1));
            }
        });
        enemyAmountAmount = new Label("0", game.skin);

        table.add(enemyAmountAmount).colspan(1).right().padTop(10);
        table.add(upgradeEnemyAmountButton).colspan(1).center().padTop(10).size(iconSize / 2f);
        // life duration amount
        table.row();
        final Label lifeDuration = new Label("Life Duration: ", game.skin);
        table.add(lifeDuration).colspan(3).left().padTop(10).padLeft(25);

        upgradelifeDurationButton = new ImageButton(game.skin, "upgrade");
        upgradelifeDurationButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (gameScreen.money < (towerPlace.getTower()
                        .getStat("lifeDuration")
                        .getLevel() + 1) * 0.35 * Tower.upgradeCost + Tower.upgradeCost) return;
                gameScreen.money -= (towerPlace.getTower()
                        .getStat("lifeDuration")
                        .getLevel() + 1) * 0.35 * Tower.upgradeCost + Tower.upgradeCost;
                towerPlace.getTower()
                        .setStat("lifeDuration", new Stat<>("lifeDuration", (Integer) towerPlace.getTower()
                                .getStat("range2")
                                .getValue() + 1, towerPlace.getTower().getStat("lifeDuration").getLevel() + 1));
            }
        });
        lifeDurationAmount = new Label("0", game.skin);

        table.add(lifeDurationAmount).colspan(1).right().padTop(10);
        table.add(upgradelifeDurationButton).colspan(1).center().padTop(10).size(iconSize / 2f);
        // range2
        table.row();
        final Label range2 = new Label("Tactical Range: ", game.skin);
        table.add(range2).colspan(3).left().padTop(10).padLeft(25);

        upgradeRange2Button = new ImageButton(game.skin, "upgrade");
        upgradeRange2Button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (gameScreen.money < (towerPlace.getTower()
                        .getStat("range2")
                        .getLevel() + 1) * 0.35 * Tower.upgradeCost + Tower.upgradeCost) return;
                gameScreen.money -= (towerPlace.getTower()
                        .getStat("range2")
                        .getLevel() + 1) * 0.35 * Tower.upgradeCost + Tower.upgradeCost;
                towerPlace.getTower()
                        .setStat("range2", new Stat<>("range2", (int) ((Integer) towerPlace.getTower()
                                .getStat("range2")
                                .getValue() + (Integer) towerPlace.getTower().getStat("range2").getValue() * 0.1f),
                                                      towerPlace.getTower().getStat("range2").getLevel() + 1));
            }
        });
        range2Amount = new Label("0", game.skin);

        table.add(range2Amount).colspan(1).right().padTop(10);
        table.add(upgradeRange2Button).colspan(1).center().padTop(10).size(iconSize / 2f);
        // max HP
        table.row();
        final Label maxHP = new Label("Max HP: ", game.skin);
        table.add(maxHP).colspan(3).left().padTop(10).padLeft(25);

        upgradeHpButton = new ImageButton(game.skin, "upgrade");
        upgradeHpButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (gameScreen.money < (towerPlace.getTower()
                        .getStat("maxHP")
                        .getLevel() + 1) * 0.35 * Tower.upgradeCost + Tower.upgradeCost) return;
                gameScreen.money -= (towerPlace.getTower()
                        .getStat("maxHP")
                        .getLevel() + 1) * 0.35 * Tower.upgradeCost + Tower.upgradeCost;
                towerPlace.getTower()
                        .setStat("maxHP", new Stat<>("maxHP", (int) ((Integer) towerPlace.getTower()
                                .getStat("maxHP")
                                .getValue() + (Integer) towerPlace.getTower().getStat("maxHP").getValue() * 0.1f),
                                                     towerPlace.getTower().getStat("maxHP").getLevel() + 1));
            }
        });
        maxHPAmount = new Label("0", game.skin);

        table.add(maxHPAmount).colspan(1).right().padTop(10);
        table.add(upgradeHpButton).colspan(1).center().padTop(10).size(iconSize / 2f);
        // unit amount
        table.row();
        final Label unitAmount = new Label("Unit Amount: ", game.skin);
        table.add(unitAmount).colspan(3).left().padTop(10).padLeft(25);

        upgradeUnitAmountButton = new ImageButton(game.skin, "upgrade");
        upgradeUnitAmountButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (gameScreen.money < (towerPlace.getTower()
                        .getStat("unitAmount")
                        .getLevel() + 1) * 0.7 * Tower.upgradeCost + Tower.upgradeCost * 2) return;
                gameScreen.money -= (towerPlace.getTower()
                        .getStat("unitAmount")
                        .getLevel() + 1) * 0.7 * Tower.upgradeCost + Tower.upgradeCost * 2;
                towerPlace.getTower()
                        .setStat("unitAmount", new Stat<>("unitAmount", (Integer) towerPlace.getTower()
                                .getStat("unitAmount")
                                .getValue() + 1, towerPlace.getTower().getStat("unitAmount").getLevel() + 1));
            }
        });
        unitAmountAmount = new Label("0", game.skin);

        table.add(unitAmountAmount).colspan(1).right().padTop(10);
        table.add(upgradeUnitAmountButton).colspan(1).center().padTop(10).size(iconSize / 2f);
        // range3 amount
        table.row();
        final Label range3 = new Label("Site Range: ", game.skin);
        table.add(range3).colspan(3).left().padTop(10).padLeft(25);

        upgradeRange3Button = new ImageButton(game.skin, "upgrade");
        upgradeRange3Button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (gameScreen.money < (towerPlace.getTower()
                        .getStat("range3")
                        .getLevel() + 1) * 0.35 * Tower.upgradeCost + Tower.upgradeCost) return;
                gameScreen.money -= (towerPlace.getTower()
                        .getStat("range3")
                        .getLevel() + 1) * 0.35 * Tower.upgradeCost + Tower.upgradeCost;
                towerPlace.getTower()
                        .setStat("range3", new Stat<>("range3", (int) ((Integer) towerPlace.getTower()
                                .getStat("range3")
                                .getValue() + (Integer) towerPlace.getTower().getStat("range3").getValue() * 0.1f),
                                                      towerPlace.getTower().getStat("range3").getLevel() + 1));
            }
        });
        range3Amount = new Label("0", game.skin);

        table.add(range3Amount).colspan(1).right().padTop(10);
        table.add(upgradeRange3Button).colspan(1).center().padTop(10).size(iconSize / 2f);
        // refund
        table.row();
        table.add(new Label("", game.skin));
        table.row();
        TextButton refund = new TextButton("Refund", game.skin, "info");
        refund.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                int refundAmountAmount = ((int) towerPlace.getTower().getStat("cost").getValue() / 3) * 2;
                try {
                    HashMap<String, Stat> defaultStats = (HashMap<String, Stat>) ClassReflection.getField(
                            towerPlace.getTower().getClass(), "defaultStats").get(null);

                    int upgradeCost;
                    try {
                        upgradeCost = (int) ClassReflection.getField(towerPlace.getTower().getClass(), "upgradeCost")
                                .get(null);
                    } catch (ReflectionException e) {
                        upgradeCost = Tower.upgradeCost;
                    }
                    for (String statName : defaultStats.keySet()) {
                        refundAmountAmount += ((towerPlace.getTower()
                                .getStat(statName)
                                .getLevel()) * upgradeCost * 0.35 / 3);
                    }
                } catch (ReflectionException e) {
                    refundAmountAmount = ((int) towerPlace.getTower().getStat("cost").getValue() / 3) * 2;
                }
                gameScreen.money += refundAmountAmount;
                towerPlace.removeTower();
                close();
                Dialog dialog = new Dialog("", game.skin, "info") {
                    @Override
                    public void result(Object object) {
                        InfoTowerHUD.this.stage.getActors().removeValue(this, true);
                    }
                };
                dialog.text("Refunded " + refundAmountAmount + " gold");
                dialog.button("OK", true, game.skin.get("info", TextButton.TextButtonStyle.class));

                dialog.center();
                dialog.center();
                if (dialog.getButtonTable().getCells().size > 1)
                    dialog.getButtonTable().getCells().first().pad(0, 0, 0, 75);
                dialog.getContentTable().pad(0, 25, 25, 25);
                dialog.setWidth(Gdx.graphics.getWidth() / 2f);
                dialog.setPosition(Gdx.graphics.getWidth() / 2f - dialog.getWidth() / 2f, Gdx.graphics.getHeight() / 2f,
                                   Align.center);
                dialog.show(stage);

            }
        });
        refundAmount = new Label("0", game.skin);
        Image moneyIcon = new Image(new TextureRegionDrawable(game.assetManager.get("hud/coin.png", Texture.class)),
                                    Scaling.fit);
        table.add(refund).colspan(3).center().expandX();
        table.add(refundAmount).right();
        table.add(moneyIcon).left().padLeft(5).size(game.settingPreference.getInteger("icon-size") / 2f);

        setHomeLocation = new TextButton("Set Home Location", game.skin, "info");
        setHomeLocation.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gameScreen.selectedTowerPlace = towerPlace;
                close();
            }
        });
        table.row();
        table.add(new Label("", game.skin));
        table.row();
        table.add(setHomeLocation).colspan(10).center().expandX();

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
        game.assetManager.load("towers/NoTower.png", Texture.class);
    }

    /**
     * gets called when the tower place is updated
     * used to update the labels
     */
    @Override
    protected void updatedTowerPlace() {
        updateStats();
    }

    public void update() {
        if (towerPlace == null) return;
        if (towerPlace.getTower() == null) return;

        updateStats();

        upgradeDamageButton.setDisabled(gameScreen.money < (towerPlace.getTower()
                .getStat("damage")
                .getLevel() + 1) * 0.35 * Tower.upgradeCost + Tower.upgradeCost);
        upgradeRangeButton.setDisabled(gameScreen.money < (towerPlace.getTower()
                .getStat("range")
                .getLevel() + 1) * 0.35 * Tower.upgradeCost + Tower.upgradeCost);
        upgradeSpeedButton.setDisabled(gameScreen.money < (towerPlace.getTower()
                .getStat("speed")
                .getLevel() + 1) * 0.35 * Tower.upgradeCost + Tower.upgradeCost);
        upgradeHpButton.setDisabled(gameScreen.money < (towerPlace.getTower()
                .getStat("maxHP")
                .getLevel() + 1) * 0.35 * Tower.upgradeCost + Tower.upgradeCost);
        upgradeAtSpeedButton.setDisabled(gameScreen.money < (towerPlace.getTower()
                .getStat("atspeed")
                .getLevel() + 1) * 0.35 * Tower.upgradeCost + Tower.upgradeCost);
        upgradelifeDurationButton.setDisabled(gameScreen.money < (towerPlace.getTower()
                .getStat("lifeDuration")
                .getLevel() + 1) * 0.35 * Tower.upgradeCost + Tower.upgradeCost);
        upgradeEnemyAmountButton.setDisabled(gameScreen.money < (towerPlace.getTower()
                .getStat("enemyAmount")
                .getLevel() + 1) * 0.7 * Tower.upgradeCost + Tower.upgradeCost * 2);
        upgradeUnitAmountButton.setDisabled(gameScreen.money < (towerPlace.getTower()
                .getStat("unitAmount")
                .getLevel() + 1) * 0.7 * Tower.upgradeCost + Tower.upgradeCost * 2);
        upgradeRange2Button.setDisabled(gameScreen.money < (towerPlace.getTower()
                .getStat("range2")
                .getLevel() + 1) * 0.35 * Tower.upgradeCost + Tower.upgradeCost);
        upgradeRange3Button.setDisabled(gameScreen.money < (towerPlace.getTower()
                .getStat("range3")
                .getLevel() + 1) * 0.35 * Tower.upgradeCost + Tower.upgradeCost);

        TowerTypes towerType;
        try {
            towerType = (TowerTypes) ClassReflection.getField(towerPlace.getTower().getClass(), "type").get(null);
        } catch (ReflectionException e) {
            towerType = TowerTypes.NONE;
        }

        switch (towerType) {
            case MELEE:
                upgradeEnemyAmountButton.setDisabled(true);
                upgradelifeDurationButton.setDisabled(true);
                break;
            case SINGLE:
                upgradeEnemyAmountButton.setDisabled(true);
                upgradeRange2Button.setDisabled(true);
                upgradelifeDurationButton.setDisabled(true);
                upgradeHpButton.setDisabled(true);
                upgradeUnitAmountButton.setDisabled(true);
                upgradeRange3Button.setDisabled(true);
                break;
            case AOE:
                upgradeHpButton.setDisabled(true);
                upgradeUnitAmountButton.setDisabled(true);
                upgradeRange3Button.setDisabled(true);
                break;
            case NONE:
                break;
        }
    }

    private void updateStats() {
        towerImage.setDrawable(new TextureRegionDrawable(towerPlace.getTower().getTexture()));
        towerName.setText(towerPlace.getTower().name);

        setHomeLocation.setDisabled(towerPlace.getTower().getType() != TowerTypes.MELEE);

        typeAmount.setText(towerPlace.getTower().getType().toString());
        killsAmount.setText(String.valueOf(towerPlace.getTower().kills));
        damageAmount.setText(String.valueOf(towerPlace.getTower().getStat("damage").getLevel() + 1));
        rangeAmount.setText(String.valueOf(towerPlace.getTower().getStat("range").getLevel() + 1));
        speedAmount.setText(String.valueOf(towerPlace.getTower().getStat("speed").getLevel() + 1));
        maxHPAmount.setText(String.valueOf(towerPlace.getTower().getStat("maxHP").getLevel() + 1));
        totalDamageAmount.setText(String.valueOf(towerPlace.getTower().totalDamage));
        atspeedAmount.setText(String.valueOf(towerPlace.getTower().getStat("atspeed").getLevel() + 1));
        range2Amount.setText(String.valueOf(towerPlace.getTower().getStat("range2").getLevel() + 1));
        range3Amount.setText(String.valueOf(towerPlace.getTower().getStat("range3").getLevel() + 1));
        unitAmountAmount.setText(String.valueOf(towerPlace.getTower().getStat("unitAmount").getLevel() + 1));
        enemyAmountAmount.setText(String.valueOf(towerPlace.getTower().getStat("enemyAmount").getLevel() + 1));
        lifeDurationAmount.setText(String.valueOf(towerPlace.getTower().getStat("lifeDuration").getLevel() + 1));
        int refundAmountAmount = ((int) towerPlace.getTower().getStat("cost").getValue() / 3) * 2;
        try {
            HashMap<String, Stat> defaultStats = (HashMap<String, Stat>) ClassReflection.getField(
                    towerPlace.getTower().getClass(), "defaultStats").get(null);

            int upgradeCost;
            try {
                upgradeCost = (int) ClassReflection.getField(towerPlace.getTower().getClass(), "upgradeCost").get(null);
            } catch (ReflectionException e) {
                upgradeCost = Tower.upgradeCost;
            }
            for (String statName : defaultStats.keySet()) {
                refundAmountAmount += ((towerPlace.getTower().getStat(statName).getLevel()) * upgradeCost * 0.35 / 3);
            }
        } catch (ReflectionException e) {
            refundAmountAmount = ((int) towerPlace.getTower().getStat("cost").getValue() / 3) * 2;
        }
        refundAmount.setText(String.valueOf(refundAmountAmount));
    }
}
