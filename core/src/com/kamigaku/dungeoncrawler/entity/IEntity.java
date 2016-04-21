package com.kamigaku.dungeoncrawler.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kamigaku.dungeoncrawler.component.*;
import java.util.ArrayList;


public interface IEntity {

	void update(SpriteBatch batch);
	PhysicsComponent getPhysicsComponent();
        GraphicsComponent getGraphicsComponent();
        ArrayList<SensorComponent> getSensorsComponent();
        void dispose();
        
}
