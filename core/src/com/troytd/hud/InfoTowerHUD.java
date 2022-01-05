package com.troytd.hud;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
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
    private final Label AOEAmount;
    private final Label atspeedAmount;
    private final Label typeAmount;

    public InfoTowerHUD(TroyTD game, Stage stage, Map map, float topHUDHeight, final GameScreen gameScreen) {
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
        table.add(damage).colspan(2).left().padTop(15).padLeft(25);
        table.add(damageAmount).colspan(3).right().padRight(25).padTop(15);
        // range
        table.row().colspan(2).left().padTop(15).padLeft(25);
        final Label range = new Label("Range: ", game.skin);
        rangeAmount = new Label("0", game.skin);
        table.add(range).colspan(2).left().padTop(15).padLeft(25);
        table.add(rangeAmount).colspan(3).right().padRight(25).padTop(15);
        // speed
        table.row();
        final Label speed = new Label("Speed: ", game.skin);
        speedAmount = new Label("0", game.skin);
        table.add(speed).colspan(2).left().padTop(15).padLeft(25);
        table.add(speedAmount).colspan(3).right().padRight(25).padTop(15);
        // HP
        table.row();
        final Label HP = new Label("HP: ", game.skin);
        HPAmount = new Label("0", game.skin);
        table.add(HP).colspan(2).left().padTop(15).padLeft(25);
        table.add(HPAmount).colspan(3).right().padRight(25).padTop(15);
        // max HP
        table.row();
        final Label maxHP = new Label("Max HP: ", game.skin);
        maxHPAmount = new Label("0", game.skin);
        table.add(maxHP).colspan(2).left().padTop(15).padLeft(25);
        table.add(maxHPAmount).colspan(3).right().padRight(25).padTop(15);
        // kills
        table.row().colspan(2).left().padTop(15).padLeft(25);
        final Label kills = new Label("Kills: ", game.skin);
        killsAmount = new Label("0", game.skin);
        table.add(kills).colspan(2).left().padTop(15).padLeft(25);
        table.add(killsAmount).colspan(3).right().padRight(25).padTop(15);
        // total damage
        table.row();
        final Label totalDamage = new Label("Total Damage: ", game.skin);
        totalDamageAmount = new Label("0", game.skin);
        table.add(totalDamage).colspan(2).left().padTop(15).padLeft(25);
        table.add(totalDamageAmount).colspan(3).right().padRight(25).padTop(15);
        // AOE
        table.row();
        final Label AOE = new Label("AOE: ", game.skin);
        AOEAmount = new Label("0", game.skin);
        table.add(AOE).colspan(2).left().padTop(15).padLeft(25);
        table.add(AOEAmount).colspan(3).right().padRight(25).padTop(15);
        // ats speed
        table.row();
        final Label atspeed = new Label("Attack Speed: ", game.skin);
        atspeedAmount = new Label("0", game.skin);
        table.add(atspeed).colspan(2).left().padTop(15).padLeft(25);
        table.add(atspeedAmount).colspan(3).right().padRight(25).padTop(15);
        // type
        table.row();
        final Label type = new Label("Type: ", game.skin);
        typeAmount = new Label("0", game.skin);
        table.add(type).colspan(2).left().padTop(15).padLeft(25);
        table.add(typeAmount).colspan(3).right().padRight(25).padTop(15);
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
        //noinspection AccessStaticViaInstance
        damageAmount.setText(String.valueOf(towerPlace.getTower().damage));
        //noinspection AccessStaticViaInstance
        rangeAmount.setText(String.valueOf(towerPlace.getTower().range));
        //noinspection AccessStaticViaInstance
        speedAmount.setText(String.valueOf(towerPlace.getTower().speed));
        HPAmount.setText(String.valueOf(towerPlace.getTower().hp));
        //noinspection AccessStaticViaInstance
        maxHPAmount.setText(String.valueOf(towerPlace.getTower().maxHP));
        killsAmount.setText(String.valueOf(towerPlace.getTower().kills));
        totalDamageAmount.setText(String.valueOf(towerPlace.getTower().totalDamage));
        //noinspection AccessStaticViaInstance
        AOEAmount.setText(String.valueOf(towerPlace.getTower().AOE));
        //noinspection AccessStaticViaInstance
        atspeedAmount.setText(String.valueOf(towerPlace.getTower().atspeed));
        typeAmount.setText(towerPlace.getTower().getType());
    }
}
