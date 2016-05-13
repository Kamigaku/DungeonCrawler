package com.kamigaku.dungeoncrawler.component;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;
import com.kamigaku.dungeoncrawler.singleton.LevelManager;

public class GraphicsComponent implements Disposable {

    private final Sprite _sprite;
    private int _depthAxis = 0;
    
    public GraphicsComponent(Sprite sprite) {
        this._sprite = sprite;
    }
    
    public GraphicsComponent(String sprite) {
        this._sprite = new Sprite((Texture)LevelManager.getLevelManager().getAssetManager().get(sprite, Texture.class));
    }
    
    public void update(SpriteBatch batch, float x, float y) {
        this._depthAxis = (int)y;
        batch.draw(this._sprite.getTexture(), x, y);
    }
    
    public int getDepthAxis() {
        return this._depthAxis;
    }
    
    @Override
    public void dispose() {
        this._sprite.getTexture().dispose();
    }

}
