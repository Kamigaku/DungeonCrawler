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

public class PhysicsComponent implements Disposable {
    
    private final Body _body;
    
    private float _forceX;
    private float _forceY;
    
    public PhysicsComponent(float x, float y, BodyType bodyType, short categoryBits, 
                            short maskBits, float width, float height) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = bodyType;
        bodyDef.position.set(x * Constants.TILE_WIDTH, y * Constants.TILE_HEIGHT);
        
        this._body = LevelManager.getLevelManager().getLevel().addBody(bodyDef);
        
        PolygonShape collider = new PolygonShape();
        collider.setAsBox(width, height);
        
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
    
    public void setForceY(float forceY) { // @TODO : changer les valeurs par des constantes
        this._forceY += forceY;
        if(this._forceY > Constants.PLAYER_SPEED) this._forceY = Constants.PLAYER_SPEED;
        else if(this._forceY < -Constants.PLAYER_SPEED) this._forceY = -Constants.PLAYER_SPEED;
    }
    
    public void setForceX(float forceX) { // @TODO : changer les valeurs par des constantes
        this._forceX += forceX;
        if(this._forceX > Constants.PLAYER_SPEED) this._forceX = Constants.PLAYER_SPEED;
        else if(this._forceX < -Constants.PLAYER_SPEED) this._forceX = -Constants.PLAYER_SPEED;
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
