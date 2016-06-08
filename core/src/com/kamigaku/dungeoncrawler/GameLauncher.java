package com.kamigaku.dungeoncrawler;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kamigaku.dungeoncrawler.screen.MainScreen;
import com.kamigaku.dungeoncrawler.singleton.ScreenManager;

public class GameLauncher extends Game {
	
	public SpriteBatch batch;
	
        @Override
	public void create() {
            this.batch = new SpriteBatch();
            ScreenManager.game = this;
            ScreenManager.getScreenManger().changeScreen(new MainScreen());
	}

        @Override
	public void render() {
            super.render();
	}

        @Override
	public void dispose() {
            batch.dispose();
	}
        
}