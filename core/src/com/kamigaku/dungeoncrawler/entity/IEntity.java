package com.kamigaku.dungeoncrawler.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kamigaku.dungeoncrawler.command.ICommand;
import com.kamigaku.dungeoncrawler.component.*;
import com.kamigaku.dungeoncrawler.item.IItem;
import com.kamigaku.dungeoncrawler.listener.CollisionListener;
import java.util.ArrayList;


public interface IEntity extends CollisionListener {

        void updatePhysics();
        void updateGraphics(SpriteBatch batch);
        void addItem(IItem item);
        void addSensor(SensorComponent sensor);
        void addCommand(ICommand command);
        void removeCommand(int index);
	PhysicsComponent getPhysicsComponent();
        GraphicsComponent getGraphicsComponent();
        ArrayList<SensorComponent> getSensorsComponent();
        Statistic getStatistic();
        void dispose(); // Contact
        
}
