package com.kamigaku.dungeoncrawler.tile;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.kamigaku.dungeoncrawler.component.GraphicsComponent;
import com.kamigaku.dungeoncrawler.component.PhysicsComponent;
import com.kamigaku.dungeoncrawler.constants.Constants;

public class Wall extends Tile {

    public Wall(Sprite sprite, int x, int y) {
        this._graphics = new GraphicsComponent(sprite, -0f, 0);
        this._physics = new PhysicsComponent(x, y, BodyType.StaticBody, Constants.CATEGORY_SCENERY,
                        (short) (Constants.CATEGORY_PLAYER | Constants.CATEGORY_MONSTER), 16f, 16f);
        this._physics.getBody().setUserData(this);
        this.x = x;
        this.y = y;
        this.isCrossable = false;
    }
    
    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.    
    }

    @Override
    public void beginContact(Contact contact) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void endContact(Contact contact) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
