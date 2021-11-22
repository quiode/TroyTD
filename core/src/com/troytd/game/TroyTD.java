package com.troytd.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.troytd.screens.MainMenuScreen;

public class TroyTD extends Game {

    public SpriteBatch batch;
    public BitmapFont font;
    public Skin skin;

    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont(); // use libGDX's default Arial font
        font.getData().setScale(2);
        this.setScreen(new MainMenuScreen(this));

        // set skin
        skin = new Skin(Gdx.files.internal("skins/troytd.json"));
    }

    public void render() {
        super.render(); // important!
    }

    public void dispose() {
        batch.dispose();
        font.dispose();
    }
}
