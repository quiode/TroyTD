package com.troytd.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.troytd.screens.MainMenuScreen;

public class TroyTD extends Game {
    // 22.05.2021 <3

    public final Color BACKGROUND_COLOR = new Color(0.2f, 0.2f, 0.2f, 1);
    public SpriteBatch batch;
    public BitmapFont font;
    public Skin skin;
    public Preferences settingPreference;
    public AssetManager assetManager;
    public Music music;

    public void create() {        // preferences
        settingPreference = Gdx.app.getPreferences("TroyTD-settings");

        assetManager = new AssetManager();
        float volume;

        // standard values
        if (!settingPreference.contains("width")) {
            settingPreference.putInteger("width", 800);
        }
        if (!settingPreference.contains("height")) {
            settingPreference.putInteger("height", 480);
        }
        if (!settingPreference.contains("fullscreen")) {
            settingPreference.putBoolean("fullscreen", false);
        }
        if (!settingPreference.contains("icon-size")) {
            settingPreference.putInteger("icon-size", settingPreference.getInteger("width") / 18);
        }
        if (!settingPreference.contains("difficulty")) {
            settingPreference.putInteger("difficulty", 1); // 0 = easy, 1 = normal, 2 = hard
        }
        if (!settingPreference.contains("volume")) {
            settingPreference.putFloat("volume", 0.5f);
            volume = 0.5f;
        } else {
            volume = settingPreference.getFloat("volume");
        }
        if (!settingPreference.contains("mute")) {
            settingPreference.putBoolean("mute", false);
        } else {
            if (settingPreference.getBoolean("mute")) {
                volume = 0;
            }
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

        // set window title and icon
        Gdx.graphics.setTitle("TroyTD");
        if (settingPreference.getBoolean("fullscreen")) Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());

        // play music
        music = Gdx.audio.newMusic(Gdx.files.internal("music/background_music.mp3"));
        music.play();
        music.setVolume(volume);
        music.setLooping(true);
    }

    public void render() {
        super.render();
    }

    public void dispose() {
        batch.dispose();
        font.dispose();
        assetManager.dispose();
        music.dispose();
    }
}
