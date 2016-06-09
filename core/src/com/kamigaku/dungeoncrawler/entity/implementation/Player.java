package com.kamigaku.dungeoncrawler.entity.implementation;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.kamigaku.dungeoncrawler.component.InputComponent;
import com.kamigaku.dungeoncrawler.component.SensorComponent;
import com.kamigaku.dungeoncrawler.constants.Constants;
import com.kamigaku.dungeoncrawler.entity.AEntity;

public class Player extends AEntity {
    
    private final InputComponent _input;

    public Player(Sprite sprite, float x, float y) {
        super.baseLoading(sprite, 0, -0.25f, BodyType.DynamicBody, Constants.CATEGORY_PLAYER,
                (short) (Constants.CATEGORY_SCENERY | Constants.CATEGORY_MONSTER),
                x, y, 8, 8);
        this._input = new InputComponent(this);
        this._sensors.add(new SensorComponent(this, BodyType.DynamicBody, Constants.CATEGORY_PLAYER, 
                                                Constants.CATEGORY_SCENERY, 20f));
    }
    
    public Player(String sprite, float x, float y) {
        super.baseLoading(sprite, 0, 0, BodyType.DynamicBody, Constants.CATEGORY_PLAYER,
                (short) (Constants.CATEGORY_SCENERY | Constants.CATEGORY_MONSTER),
                x, y, 8, 8);
        this._input = new InputComponent(this);
        this._sensors.add(new SensorComponent(this, BodyType.DynamicBody, Constants.CATEGORY_PLAYER, 
                                                Constants.CATEGORY_SCENERY, 20f));
    }
    
    public void updateInput() {
        this._input.update();
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
