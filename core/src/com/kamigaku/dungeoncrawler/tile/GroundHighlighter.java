package com.kamigaku.dungeoncrawler.tile;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.kamigaku.dungeoncrawler.component.GraphicsComponent;

public class GroundHighlighter extends Tile {

    public GroundHighlighter(String sprite, int x, int y) {
        this._graphics = new GraphicsComponent(sprite, -0f, 0f);
        this.x = x;
        this.y = y;
        this.isCrossable = true;
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
    
}
