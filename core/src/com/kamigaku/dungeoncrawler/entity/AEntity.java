/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.kamigaku.dungeoncrawler.entity;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.kamigaku.dungeoncrawler.component.GraphicsComponent;
import com.kamigaku.dungeoncrawler.component.PhysicsComponent;
import com.kamigaku.dungeoncrawler.component.SensorComponent;
import java.util.ArrayList;



public abstract class AEntity implements IEntity {
    
    protected final short CATEGORY_PLAYER = 0x0001;  // 0000000000000001 in binary
    protected final short CATEGORY_MONSTER = 0x0002; // 0000000000000010 in binary
    protected final short CATEGORY_SCENERY = 0x0004; // 0000000000000100 in binary

    protected GraphicsComponent _graphics;
    protected PhysicsComponent _physics;
    protected ArrayList<SensorComponent> _sensors;
    
    // Initiliasation de différentes informations communes
    protected void baseLoading(Sprite sprite, BodyType bodyType, short categoryBits, 
                                short maskBits, float x, float y, float width, 
                                float height) {
        this._graphics = new GraphicsComponent(sprite);
        this._physics = new PhysicsComponent(x, y, bodyType, categoryBits, maskBits, 
                                            width, height);
        this._sensors = new ArrayList<SensorComponent>();
    }

    @Override
    public GraphicsComponent getGraphicsComponent() {
        return this._graphics;
    }

    @Override
    public PhysicsComponent getPhysicsComponent() {
        return this._physics;
    }
    
    @Override
    public ArrayList<SensorComponent> getSensorsComponent() {
        return this._sensors;
    }
    
    @Override
    public void dispose() {
        this._physics.dispose();
        this._graphics.dispose();
    }
    
}