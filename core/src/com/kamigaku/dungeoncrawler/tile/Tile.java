package com.kamigaku.dungeoncrawler.tile;

import com.badlogic.gdx.math.Vector2;
import com.kamigaku.dungeoncrawler.component.GraphicsComponent;
import com.kamigaku.dungeoncrawler.component.PhysicsComponent;
import com.kamigaku.dungeoncrawler.listener.CollisionListener;


public abstract class Tile implements CollisionListener {
   
    protected PhysicsComponent _physics;
    protected GraphicsComponent _graphics;
    public boolean isCrossable;
    public int x;
    public int y;
    
    public PhysicsComponent getPhysicsComponent() {
        return this._physics;
    }
    
    public GraphicsComponent getGraphicsComponent() {
        return this._graphics;
    }
    
    public Vector2 getPosition() {
        return new Vector2(x, y);
    }
        
}
