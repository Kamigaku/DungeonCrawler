package com.kamigaku.dungeoncrawler.component;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GraphicsComponent {

    private final Sprite _sprite;
    private int _depthAxis = 0;
    
    public GraphicsComponent(Sprite sprite) {
        this._sprite = sprite;
    }
    
    public void update(SpriteBatch batch, float x, float y) {
        this._depthAxis = (int)y;
        batch.draw(this._sprite.getTexture(), x, y);
    }
    
    public int getDepthAxis() {
        return this._depthAxis;
    }

}
