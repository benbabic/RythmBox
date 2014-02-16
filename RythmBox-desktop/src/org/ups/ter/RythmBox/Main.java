package org.ups.ter.RythmBox;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "RythmBox";
		cfg.useGL20 = false;
		cfg.width = 1024;
		cfg.height = 720;
		
		new LwjglApplication(new Game(), cfg);
	}
}
