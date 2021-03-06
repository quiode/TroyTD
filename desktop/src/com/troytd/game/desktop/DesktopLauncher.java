package com.troytd.game.desktop;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.troytd.game.TroyTD;

public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setTitle("TroyTD");
		config.setWindowedMode(800,480);
		config.setResizable(false);
		config.setMaximized(true);
		new Lwjgl3Application(new TroyTD(), config);
	}
}
