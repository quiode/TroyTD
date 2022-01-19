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
import com.troytd.maps.Map;
import com.troytd.screens.GameScreen;

public class InfoTowerHUD extends SideHUD {
    private final Image towerImage;
    private final TextField towerName;
    private final Label damageAmount;
    private final Label rangeAmount;
    private final Label speedAmount;
    private final Label HPAmount;
    private final Label maxHPAmount;
    private final Label killsAmount;
    private final Label totalDamageAmount;
    private final Label atspeedAmount;
    private final Label typeAmount;
    private final Label refundAmount;

    public InfoTowerHUD(final TroyTD game, final Stage stage, Map map, float topHUDHeight,
                        final GameScreen gameScreen) {
        super(game, stage, map, topHUDHeight, "Tower Stats", gameScreen);

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
        // damage
        table.row();
        final Label damage = new Label("Damage: ", game.skin);
        damageAmount = new Label("0", game.skin);
        table.add(damage).colspan(2).left().padTop(10).padLeft(25);
        table.add(damageAmount).colspan(3).right().padRight(25).padTop(10);
        // range
        table.row().colspan(2).left().padTop(10).padLeft(25);
        final Label range = new Label("Range: ", game.skin);
        rangeAmount = new Label("0", game.skin);
        table.add(range).colspan(2).left().padTop(10).padLeft(25);
        table.add(rangeAmount).colspan(3).right().padRight(25).padTop(10);
        // speed
        table.row();
        final Label speed = new Label("Speed: ", game.skin);
        speedAmount = new Label("0", game.skin);
        table.add(speed).colspan(2).left().padTop(10).padLeft(25);
        table.add(speedAmount).colspan(3).right().padRight(25).padTop(10);
        // HP
        table.row();
        final Label HP = new Label("HP: ", game.skin);
        HPAmount = new Label("0", game.skin);
        table.add(HP).colspan(2).left().padTop(10).padLeft(25);
        table.add(HPAmount).colspan(3).right().padRight(25).padTop(10);
        // max HP
        table.row();
        final Label maxHP = new Label("Max HP: ", game.skin);
        maxHPAmount = new Label("0", game.skin);
        table.add(maxHP).colspan(2).left().padTop(10).padLeft(25);
        table.add(maxHPAmount).colspan(3).right().padRight(25).padTop(10);
        // kills
        table.row().colspan(2).left().padTop(10).padLeft(25);
        final Label kills = new Label("Kills: ", game.skin);
        killsAmount = new Label("0", game.skin);
        table.add(kills).colspan(2).left().padTop(10).padLeft(25);
        table.add(killsAmount).colspan(3).right().padRight(25).padTop(10);
        // total damage
        table.row();
        final Label totalDamage = new Label("Total Damage: ", game.skin);
        totalDamageAmount = new Label("0", game.skin);
        table.add(totalDamage).colspan(2).left().padTop(10).padLeft(25);
        table.add(totalDamageAmount).colspan(3).right().padRight(25).padTop(10);
        // ats speed
        table.row();
        final Label atspeed = new Label("Attack Speed: ", game.skin);
        atspeedAmount = new Label("0", game.skin);
        table.add(atspeed).colspan(2).left().padTop(10).padLeft(25);
        table.add(atspeedAmount).colspan(3).right().padRight(25).padTop(10);
        // type
        table.row();
        final Label type = new Label("Type: ", game.skin);
        typeAmount = new Label("0", game.skin);
        table.add(type).colspan(2).left().padTop(10).padLeft(25);
        table.add(typeAmount).colspan(3).right().padRight(25).padTop(10);
        // refund
        table.row();
        table.add(new Label("", game.skin));
        table.row();
        TextButton refund = new TextButton("Refund", game.skin, "info");
        refund.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                try {
                    gameScreen.money += ((int) ClassReflection.getField(towerPlace.getTower().getClass(), "cost")
                            .get(null) / 3) * 2;
                    towerPlace.removeTower();
                    close();
                    Dialog dialog = new Dialog("", game.skin, "info") {
                        @Override
                        public void result(Object object) {
                            InfoTowerHUD.this.stage.getActors().removeValue(this, true);
                        }
                    };
                    dialog.text("Refunded " + refundAmount.getText() + " gold");
                    dialog.button("OK", true, game.skin.get("info", TextButton.TextButtonStyle.class));

                    dialog.center();
                    dialog.center();
                    if (dialog.getButtonTable().getCells().size > 1)
                        dialog.getButtonTable().getCells().first().pad(0, 0, 0, 75);
                    dialog.getContentTable().pad(0, 25, 25, 25);
                    dialog.setWidth(Gdx.graphics.getWidth() / 2f);
                    dialog.setPosition(Gdx.graphics.getWidth() / 2f - dialog.getWidth() / 2f,
                                       Gdx.graphics.getHeight() / 2f, Align.center);
                    dialog.show(stage);
                } catch (ReflectionException e) {
                    e.printStackTrace();
                }
            }
        });
        refundAmount = new Label("0", game.skin);
        Image moneyIcon = new Image(new TextureRegionDrawable(game.assetManager.get("hud/coin.png", Texture.class)),
                                    Scaling.fit);
        table.add(refund).colspan(3).center().expandX();
        table.add(refundAmount).right();
        table.add(moneyIcon).left().padLeft(5).size(game.settingPreference.getInteger("icon-size") / 2f);
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
        towerImage.setDrawable(new TextureRegionDrawable(towerPlace.getTower().getTexture()));
        towerName.setText(towerPlace.getTower().name);
        try {
            damageAmount.setText(String.valueOf(
                    (int) ClassReflection.getField(towerPlace.getTower().getClass(), "damage").get(null)));
            rangeAmount.setText(String.valueOf(
                    (int) ClassReflection.getField(towerPlace.getTower().getClass(), "range").get(null)));
            speedAmount.setText(String.valueOf(
                    (int) ClassReflection.getField(towerPlace.getTower().getClass(), "speed").get(null)));
            HPAmount.setText(String.valueOf(towerPlace.getTower().hp));
            maxHPAmount.setText(String.valueOf(
                    (int) ClassReflection.getField(towerPlace.getTower().getClass(), "maxHP").get(null)));
            killsAmount.setText(String.valueOf(towerPlace.getTower().kills));
            totalDamageAmount.setText(String.valueOf(towerPlace.getTower().totalDamage));
            atspeedAmount.setText(String.valueOf(
                    (int) ClassReflection.getField(towerPlace.getTower().getClass(), "atspeed").get(null)));
            typeAmount.setText(towerPlace.getTower().getType());
            refundAmount.setText(String.valueOf(
                    ((int) ClassReflection.getField(towerPlace.getTower().getClass(), "cost").get(null) / 3) * 2));
        } catch (ReflectionException e) {
            e.printStackTrace();
        }
    }
}
