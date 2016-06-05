package com.kamigaku.dungeoncrawler.component;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;
import static com.kamigaku.dungeoncrawler.constants.Constants.PIXELS_PER_METER;
import com.kamigaku.dungeoncrawler.singleton.LevelManager;

public class GraphicsComponent implements Disposable {

    private final Sprite _sprite;
    private int _depthAxis = 0;
    
    public GraphicsComponent(Sprite sprite) {
        this._sprite = sprite;
        this._sprite.setSize(this._sprite.getWidth() / PIXELS_PER_METER, this._sprite.getHeight() / PIXELS_PER_METER);
    }
    
    public GraphicsComponent(String sprite) {
        this._sprite = new Sprite((Texture)LevelManager.getLevelManager().getAssetManager().get(sprite, Texture.class));
        this._sprite.setSize(this._sprite.getWidth() / PIXELS_PER_METER, this._sprite.getHeight() / PIXELS_PER_METER);
    }
    
    public void update(SpriteBatch batch, float x, float y) {
        this._depthAxis = (int)y;
        this._sprite.setPosition(x, y);
        this._sprite.draw(batch);
        //batch.draw(this._sprite.getTexture(), x - (this._sprite.getWidth() / 2), y - (this._sprite.getHeight() / 2), 1, 1);
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
