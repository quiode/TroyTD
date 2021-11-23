package com.troytd.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.troytd.screens.MainMenuScreen;

public class TroyTD extends Game {

    public SpriteBatch batch;
    public BitmapFont font;
    public Skin skin;
    public Preferences settingPreference;
    public AssetManager assetManager;

    public void create() {        // preferences
        settingPreference = Gdx.app.getPreferences("TroyTD-settings");

        assetManager = new AssetManager();

        if (!settingPreference.contains("width")) {
            settingPreference.putInteger("width", 800);
        }
        if (!settingPreference.contains("height")) {
            settingPreference.putInteger("height", 480);
        }

        settingPreference.flush();

        Gdx.graphics.setWindowedMode(settingPreference.getInteger("width"), settingPreference.getInteger("height"));

        batch = new SpriteBatch();
        font = new BitmapFont(); // use libGDX's default Arial font
        font.getData().setScale(2);

        // set skin
        assetManager.load("skins/troytd.json", Skin.class);
        assetManager.finishLoading();
        skin = assetManager.get("skins/troytd.json", Skin.class);
        this.setScreen(new MainMenuScreen(this));
    }

    public void render() {
        super.render();
    }

    public void dispose() {
        batch.dispose();
        font.dispose();
        assetManager.dispose();
    }
}
