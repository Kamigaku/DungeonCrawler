package com.kamigaku.dungeoncrawler.component;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.kamigaku.dungeoncrawler.singleton.LevelManager;

public class PhysicsComponent {
    
    private final Body _body;
    
    public float forceX;
    public float forceY;
    
    public PhysicsComponent(float x, float y) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.DynamicBody;
        bodyDef.position.set(x, y);
        this._body = LevelManager.getLevelManager().getLevel().addBody(bodyDef);
        PolygonShape collider = new PolygonShape();  
        collider.setAsBox(16, 16);
        FixtureDef fDef = new FixtureDef();
        fDef.density = 0f;
        fDef.friction = 0f;
        fDef.restitution = 0f;
        fDef.shape = collider;
        this._body.createFixture(fDef);
        collider.dispose();
    }
    
    public void update() {
        this._body.setLinearVelocity(this.forceX, this.forceY);
    }
    
    public Body getBody() {
        return this._body;
    }
    
    public Vector2 getPosition() {
        return this._body.getPosition();
    }

}
