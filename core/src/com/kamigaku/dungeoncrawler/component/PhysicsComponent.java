package com.kamigaku.dungeoncrawler.component;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Disposable;
import com.kamigaku.dungeoncrawler.constants.Constants;
import com.kamigaku.dungeoncrawler.singleton.LevelManager;
import java.awt.Point;

public class PhysicsComponent implements Disposable {
    
    private final Body _body;
    
    public float _forceX;
    public float _forceY;
    
    public PhysicsComponent(float x, float y, BodyType bodyType, short categoryBits, 
                            short maskBits, float width, float height) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = bodyType;
        bodyDef.position.set(x, y);
        this._body = LevelManager.getLevelManager().getLevel().addBody(bodyDef);
        
        PolygonShape collider = new PolygonShape();
        collider.setAsBox(width / Constants.TILE_WIDTH, height / Constants.TILE_HEIGHT);
        
        FixtureDef fDef = new FixtureDef();
        fDef.density = 0f;
        fDef.friction = 0f;
        fDef.restitution = 0f;
        fDef.shape = collider;
        fDef.filter.categoryBits = categoryBits;
        fDef.filter.maskBits = maskBits;
        
        this._body.createFixture(fDef);
        
        collider.dispose();
    }
    
    public void update() {
        this._body.setLinearVelocity(this._forceX, this._forceY);
    }
    
    public void addForceY(float forceY) {
        this._forceY = Math.min(Math.max(_forceY+forceY, -Constants.PLAYER_SPEED), Constants.PLAYER_SPEED);
    }
    
    public void addForceX(float forceX) {
        this._forceX = Math.min(Math.max(_forceX+forceX, -Constants.PLAYER_SPEED), Constants.PLAYER_SPEED);
    }
    
    public void setForceY(float forceY) {
        this._forceY = forceY;
    }
    
    public void setForceX(float forceX) {
        this._forceX = forceX;
    }
    
    public void setForceXY(float forceX, float forceY) {
        this.setForceX(forceX);
        this.setForceY(forceY);
    }
    
    public Body getBody() {
        return this._body;
    }
    
    public Vector2 getPosition() {
        return this._body.getPosition();
    }
    
    @Override
    public void dispose() {
    }

}
