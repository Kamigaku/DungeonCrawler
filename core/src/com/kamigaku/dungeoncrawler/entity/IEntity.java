package com.kamigaku.dungeoncrawler.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.kamigaku.dungeoncrawler.component.GraphicsComponent;
import com.kamigaku.dungeoncrawler.component.PhysicsComponent;
import com.kamigaku.dungeoncrawler.component.SensorComponent;
import com.kamigaku.dungeoncrawler.listener.CollisionListener;
import java.util.ArrayList;


public interface IEntity extends CollisionListener {

	void update(SpriteBatch batch);
	PhysicsComponent getPhysicsComponent();
        GraphicsComponent getGraphicsComponent();
        ArrayList<SensorComponent> getSensorsComponent();
        void dispose();
        void postSolve(Contact contact, ContactImpulse impulse); // Contact
        
}
