package com.troytd.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.troytd.screens.MainMenuScreen;
import space.earlygrey.shapedrawer.ShapeDrawer;

public class TroyTD extends Game {
    // 22.05.2021 <3

    public final Color BACKGROUND_COLOR = new Color(0.2f, 0.2f, 0.2f, 1);
    public ShapeDrawer shapeDrawer;
    public SpriteBatch batch;
    public BitmapFont font;
    public Skin skin;
    public Preferences settingPreference;
    public AssetManager assetManager;
    public Music music;

    public void create() {
        // preferences
        settingPreference = Gdx.app.getPreferences("TroyTD-settings");

        assetManager = new AssetManager();
        float volume;

        // standard values
        if (!settingPreference.contains("width")) {
            settingPreference.putInteger("width", 1000);
        }
        if (!settingPreference.contains("height")) {
            settingPreference.putInteger("height", 750);
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
        // get asset
        assetManager.load("others/white_pixel.png", Texture.class);
        // load everything
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

        // shape drawer
        shapeDrawer = new ShapeDrawer(batch,
                                      new TextureRegion(assetManager.get("others/white_pixel.png", Texture.class)));
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

    public static final class Constants {
        public static final String ParticlePackAtlasPath = "particles/texture_atlas/ParticlePack.atlas";
        public static final String ParticleDirectory = "particles/";
        public static final Color range_radius = new Color(0.5f, 0.5f, 0.5f, 0.5f);
        public static final Color HomeLocationRadius = new Color(0.3f, 0.3f, 0.3f, 0.5f);
    }
}
