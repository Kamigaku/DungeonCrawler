package com.kamigaku.dungeoncrawler.level;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface ILevel {

	void render(SpriteBatch batch);
	void dispose();
	void addInputProcessor(InputProcessor ip);
	AssetManager getAssetManager();
	OrthographicCamera getCamera();
	
}
