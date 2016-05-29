/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kamigaku.dungeoncrawler.listener;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.Manifold;

/**
 *
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
public interface CollisionListener {
    
    public void beginContact(Contact contact);
    public void endContact(Contact contact);
    public void preSolve(Contact contact, Manifold oldManifold);
    public void postSolve(Contact contact, ContactImpulse impulse);
    
}
