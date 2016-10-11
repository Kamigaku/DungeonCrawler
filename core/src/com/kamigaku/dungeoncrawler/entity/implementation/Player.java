package com.kamigaku.dungeoncrawler.entity.implementation;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.BodyDef;
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
    
    private final int ACTIONPOINT = 5;
    private final int HEALTHPOINT = 10;

    public Player(Sprite sprite, float x, float y) {
        super.baseLoadGraphics(sprite, 0, -0.25f);
        super.baseLoadPhysics(BodyDef.BodyType.KinematicBody, x, y, Constants.CATEGORY_PLAYER,
                (short) (Constants.CATEGORY_SCENERY | Constants.CATEGORY_MONSTER), 8, 8);
        super.baseLoadSensor();
        super.baseLoadStatistic(ACTIONPOINT, HEALTHPOINT);
        super.baseLoadItems();
        super.baseLoadSkills();
        super.addSensor(new SensorComponent(this, BodyType.DynamicBody, Constants.CATEGORY_PLAYER, 
                                                Constants.CATEGORY_SCENERY, 20f));
        super.baseLoadCommands();
        this._input = new InputComponent(this);
    }
    
    public Player(String sprite, float x, float y) {
        super.baseLoadGraphics(sprite, 0, -0.25f);
        super.baseLoadPhysics(BodyDef.BodyType.KinematicBody, x, y, Constants.CATEGORY_PLAYER,
                (short) (Constants.CATEGORY_SCENERY | Constants.CATEGORY_MONSTER), 8, 8);
        super.baseLoadSensor();
        super.baseLoadStatistic(ACTIONPOINT, HEALTHPOINT);
        super.baseLoadItems();
        super.baseLoadSkills();
        super.addSensor(new SensorComponent(this, BodyType.DynamicBody, Constants.CATEGORY_PLAYER, 
                                                Constants.CATEGORY_SCENERY, 20f));
        super.baseLoadCommands();
        this._input = new InputComponent(this);
    }

    @Override
    public void beginContact(Contact contact) {
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
    
    public InputComponent getInputComponent() {
        return this._input;
    }

}
