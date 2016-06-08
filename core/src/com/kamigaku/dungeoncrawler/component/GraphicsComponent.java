package com.kamigaku.dungeoncrawler.component;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;
import com.kamigaku.dungeoncrawler.singleton.LevelManager;

public class GraphicsComponent implements Disposable {

    private final Sprite _sprite;
    private int _depthAxis = 0;
    
    private float _offsetX = 0;
    private float _offsetY = 0;
    
    public GraphicsComponent(Sprite sprite) {
        this._sprite = sprite;
        this._sprite.setSize(1f, 1f);
        this._sprite.setOriginCenter();
    }
    
    public GraphicsComponent(Sprite sprite, float offsetX, float offsetY) {
        this._sprite = sprite;
        this._sprite.setSize(1f, 1f);
        this._sprite.setOriginCenter();
        this._offsetX = offsetX;
        this._offsetY = (float)offsetY;
    }
    
    public GraphicsComponent(String sprite) {
        this._sprite = new Sprite((Texture)LevelManager.getLevelManager().getAssetManager().get(sprite, Texture.class));
        this._sprite.setSize(1f, 1f);
        this._sprite.setOriginCenter();
    }
    
    public GraphicsComponent(String sprite, float offsetX, float offsetY) {
        this._sprite = new Sprite((Texture)LevelManager.getLevelManager().getAssetManager().get(sprite, Texture.class));
        this._sprite.setSize(1f, 1f);
        this._sprite.setOriginCenter();
        this._offsetX = offsetX;
        this._offsetY = (float)offsetY;
    }
    
    public void update(SpriteBatch batch, float x, float y) {
        this._depthAxis = (int)y;
        this._sprite.setPosition(x - (this._sprite.getWidth() / 2) - this._offsetX, 
                                 y - (this._sprite.getHeight() / 2) - this._offsetY);
        this._sprite.draw(batch);
    }
    
    public int getDepthAxis() {
        return this._depthAxis;
    }
    
    public Sprite getSprite() {
        return this._sprite;
    }
    
    @Override
    public void dispose() {
        this._sprite.getTexture().dispose();
    }

}
