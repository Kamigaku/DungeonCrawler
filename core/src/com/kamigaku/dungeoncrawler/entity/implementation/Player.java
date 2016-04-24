/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.kamigaku.dungeoncrawler.entity.implementation;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.kamigaku.dungeoncrawler.component.InputComponent;
import com.kamigaku.dungeoncrawler.component.SensorComponent;
import com.kamigaku.dungeoncrawler.entity.AEntity;

public class Player extends AEntity {
    
    private final InputComponent _input;

    public Player(Sprite sprite, float x, float y) {
        super.baseLoading(sprite, BodyType.DynamicBody, CATEGORY_PLAYER,
                (short) (CATEGORY_SCENERY | CATEGORY_MONSTER),
                x, y, 8, 8);
        this._input = new InputComponent(this);
        this._sensors.add(new SensorComponent(this, BodyType.DynamicBody, CATEGORY_PLAYER, CATEGORY_SCENERY, 20f));
    }

    @Override
    public void update(SpriteBatch batch) {
        this._input.update();
        this._physics.update();
        this._graphics.update(batch, this.getPhysicsComponent().getPosition().x, this.getPhysicsComponent().getPosition().y + 8);
        for(int i = 0;  i < this._sensors.size(); i++) {
            this._sensors.get(i).update(this.getPhysicsComponent().getPosition().x, this.getPhysicsComponent().getPosition().y);
        }
    }

    @Override
    public void beginContact(Contact contact) {
        System.out.println("the collision begin on the client side !");
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
