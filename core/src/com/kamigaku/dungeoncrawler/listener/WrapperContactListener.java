package com.kamigaku.dungeoncrawler.listener;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.kamigaku.dungeoncrawler.entity.IEntity;

public class WrapperContactListener implements ContactListener {

    @Override
    public void beginContact(Contact contact) {
        ((IEntity)contact.getFixtureA().getBody().getUserData()).beginContact(contact);
        ((IEntity)contact.getFixtureB().getBody().getUserData()).beginContact(contact);
    }

    @Override
    public void endContact(Contact contact) {
        ((IEntity)contact.getFixtureA().getBody().getUserData()).endContact(contact);
        ((IEntity)contact.getFixtureB().getBody().getUserData()).endContact(contact);
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
        ((IEntity)contact.getFixtureA().getBody().getUserData()).preSolve(contact, oldManifold);
        ((IEntity)contact.getFixtureB().getBody().getUserData()).preSolve(contact, oldManifold);
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
        ((IEntity)contact.getFixtureA().getBody().getUserData()).postSolve(contact, impulse);
        ((IEntity)contact.getFixtureB().getBody().getUserData()).postSolve(contact, impulse);
    }

}
