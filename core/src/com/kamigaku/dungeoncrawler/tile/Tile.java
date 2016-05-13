package com.kamigaku.dungeoncrawler.tile;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.kamigaku.dungeoncrawler.component.GraphicsComponent;
import com.kamigaku.dungeoncrawler.component.PhysicsComponent;
import com.kamigaku.dungeoncrawler.listener.CollisionListener;


// IN WORK => a mettre dans AMapEntity
public abstract class Tile implements CollisionListener {
   
    protected PhysicsComponent _physics;
    protected GraphicsComponent _graphics;
    public boolean isCrossable;
    public float x;
    public float y;
    
    public PhysicsComponent getPhysicsComponent() {
        return this._physics;
    }
    
    public GraphicsComponent getGraphicsComponent() {
        return this._graphics;
    }
    
    public Vector2 getPosition() {
        return new Vector2(x, y);
    }
    
    public void postSolve(Contact contact, ContactImpulse impulse) throws Exception {
        throw new Exception("You have to redefine 'postSolve' in your tile extension!");
    }
    
}
