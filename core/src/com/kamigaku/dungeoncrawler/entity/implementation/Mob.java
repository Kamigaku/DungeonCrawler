package com.kamigaku.dungeoncrawler.entity.implementation;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.kamigaku.dungeoncrawler.component.SensorComponent;
import com.kamigaku.dungeoncrawler.constants.Constants;
import com.kamigaku.dungeoncrawler.entity.AEntity;
import com.kamigaku.dungeoncrawler.singleton.FightManager;
import com.kamigaku.dungeoncrawler.singleton.FightManager.FightStatus;

public class Mob extends AEntity {
    
    private boolean _idleSensor = false;
    
    private final int ACTIONPOINT = 5;
    private final int HEALTHPOINT = 5;
        
    public Mob(Sprite sprite, float x, float y) {
        super.baseLoadGraphics(sprite, 0, -0.25f);
        super.baseLoadPhysics(BodyDef.BodyType.KinematicBody, x, y, Constants.CATEGORY_MONSTER, 
                (short) (Constants.CATEGORY_SCENERY | Constants.CATEGORY_PLAYER), 8, 8);
        super.baseLoadSensor();
        super.baseLoadStatistic(ACTIONPOINT, HEALTHPOINT);
        super.addSensor(new SensorComponent(this, BodyDef.BodyType.DynamicBody, 
                Constants.CATEGORY_MONSTER, Constants.CATEGORY_PLAYER, 30f));
    }
    
    public Mob(String sprite, float x, float y) {
        super.baseLoadGraphics(sprite, 0, -0.25f);
        super.baseLoadPhysics(BodyDef.BodyType.KinematicBody, x, y, Constants.CATEGORY_MONSTER, 
                (short) (Constants.CATEGORY_SCENERY | Constants.CATEGORY_PLAYER), 8, 8);
        super.baseLoadSensor();
        super.baseLoadStatistic(ACTIONPOINT, HEALTHPOINT);
        super.addSensor(new SensorComponent(this, BodyDef.BodyType.DynamicBody, 
                Constants.CATEGORY_MONSTER, Constants.CATEGORY_PLAYER, 30f));
    }

    @Override
    public void beginContact(Contact contact) {
        if(FightManager.getFightManager().getFightStatus() == FightStatus.NONE && !this._idleSensor)
            FightManager.getFightManager().setFightStatus(FightStatus.BEGIN);
    }

    @Override
    public void endContact(Contact contact) {
        if(FightManager.getFightManager().getFightStatus() != FightStatus.NONE)
            this._idleSensor = true;
        else
            this._idleSensor = false;
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
    }

    
    
}
