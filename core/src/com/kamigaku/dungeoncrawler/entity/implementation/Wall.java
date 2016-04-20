package com.kamigaku.dungeoncrawler.entity.implementation;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.kamigaku.dungeoncrawler.entity.AEntity;

public class Wall extends AEntity {

    public Wall(Sprite sprite, float x, float y) {
        super.baseLoading(sprite, BodyType.StaticBody, x, y, 16, 16);
    }

    @Override
    public void update(SpriteBatch batch) {
        this._graphics.update(batch, this.getPhysicsComponent().getPosition().x, this.getPhysicsComponent().getPosition().y);
    }

}
