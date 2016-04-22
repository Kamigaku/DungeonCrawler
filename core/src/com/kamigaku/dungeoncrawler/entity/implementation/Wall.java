package com.kamigaku.dungeoncrawler.entity.implementation;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.kamigaku.dungeoncrawler.entity.AEntity;

public class Wall extends AEntity {

    public Wall(Sprite sprite, float x, float y) {
        super.baseLoading(sprite, BodyType.StaticBody, CATEGORY_SCENERY,
                (short) (CATEGORY_PLAYER | CATEGORY_MONSTER), 
                x, y, 16, 16);
    }

    @Override
    public void update(SpriteBatch batch) {
        this._graphics.update(batch, this.getPhysicsComponent().getPosition().x, this.getPhysicsComponent().getPosition().y);
    }
    
    @Override
    public void beginContact(Contact contact) {
        System.out.println("the collision begin on the wall side !");
    }

    @Override
    public void endContact(Contact contact) {
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
    }
    
    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
        
    }

}
