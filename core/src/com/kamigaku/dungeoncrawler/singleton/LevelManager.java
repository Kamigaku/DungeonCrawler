package com.kamigaku.dungeoncrawler.singleton;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kamigaku.dungeoncrawler.level.ILevel;

public class LevelManager {

    private static LevelManager levelManager;

    private ILevel currentLevel;

    public static LevelManager getLevelManager() {
        if(levelManager == null)
            levelManager = new LevelManager();
        return levelManager;
    }

    public void setLevel(ILevel level) {
        this.currentLevel = level;
        this.currentLevel.init();
    }

    public ILevel getLevel() {
        return this.currentLevel;
    }

    public AssetManager getAssetManager() {
        return this.currentLevel.getAssetManager();
    }

    public void render(SpriteBatch batch) {
        this.currentLevel.render(batch);
    }

    public void dispose() {
        this.currentLevel.dispose();
        LevelManager.levelManager = null;
    }

    public OrthographicCamera getCamera() {
        return this.currentLevel.getCamera();
    }
}
