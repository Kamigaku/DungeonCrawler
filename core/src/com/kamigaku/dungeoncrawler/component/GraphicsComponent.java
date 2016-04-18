package com.kamigaku.dungeoncrawler.component;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GraphicsComponent {

    private final Sprite _sprite;
    
    public GraphicsComponent(Sprite sprite) {
        this._sprite = sprite;
    }
    
    public void update(SpriteBatch batch, float x, float y) {
        batch.draw(this._sprite.getTexture(), x, y);
    }

}
