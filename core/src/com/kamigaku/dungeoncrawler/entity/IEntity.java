package com.kamigaku.dungeoncrawler.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kamigaku.dungeoncrawler.component.*;


public interface IEntity {

	void update(SpriteBatch batch);
	PhysicsComponent getPhysicsComponent();
        GraphicsComponent getGraphicsComponent();
        void dispose();
        
}
