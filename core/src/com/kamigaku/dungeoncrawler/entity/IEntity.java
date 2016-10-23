package com.kamigaku.dungeoncrawler.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kamigaku.dungeoncrawler.command.ICommand;
import com.kamigaku.dungeoncrawler.component.*;
import com.kamigaku.dungeoncrawler.item.IItem;
import com.kamigaku.dungeoncrawler.listener.CollisionListener;
import com.kamigaku.dungeoncrawler.skills.ISkill;
import java.util.ArrayList;


public interface IEntity extends CollisionListener {

        void updatePhysics();
        void updateGraphics(SpriteBatch batch);
        void addItem(IItem item);
        void addSensor(SensorComponent sensor);
        void addCommand(ICommand command);
        void addSkill(String name, boolean addToHUD);
        void removeCommand(int index);
        void popCommand();
        String getName();
	PhysicsComponent getPhysicsComponent();
        GraphicsComponent getGraphicsComponent();
        ArrayList<SensorComponent> getSensorsComponent();
        ArrayList<ISkill> getSkills();
        Statistic getStatistic();
        ArrayList<ICommand> getCommands();
        void dispose(); // Contact
        boolean isDead();
        
}
