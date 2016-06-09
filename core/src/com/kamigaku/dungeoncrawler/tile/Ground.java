package com.kamigaku.dungeoncrawler.tile;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.kamigaku.dungeoncrawler.component.GraphicsComponent;

public class Ground extends Tile {
    
    public Ground(Sprite sprite, float x, float y) {
        this._graphics = new GraphicsComponent(sprite, 0f, 0f);
        this.x = x;
        this.y = y;
        this.isCrossable = true;
    }
    
    public Ground(String sprite, float x, float y) {
        this._graphics = new GraphicsComponent(sprite, -0f, 0f);
        this.x = x;
        this.y = y;
        this.isCrossable = true;
    }
    
    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
        
    }

    @Override
    public void beginContact(Contact contact) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void endContact(Contact contact) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
