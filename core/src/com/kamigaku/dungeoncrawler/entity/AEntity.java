/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.kamigaku.dungeoncrawler.entity;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.kamigaku.dungeoncrawler.component.GraphicsComponent;
import com.kamigaku.dungeoncrawler.component.PhysicsComponent;
import com.kamigaku.dungeoncrawler.component.SensorComponent;
import java.util.ArrayList;



public abstract class AEntity implements IEntity {
    
    //@TODO : création d'un collider pour la souris afin d'afficher les informations lors de la collision avec un joueur
    
    protected GraphicsComponent _graphics;
    protected PhysicsComponent _physics;
    protected ArrayList<SensorComponent> _sensors;
    protected Statistic _statistic;
    
    // Initiliasation de différentes informations communes
    protected void baseLoading(Sprite sprite, BodyType bodyType, short categoryBits, 
                                short maskBits, float x, float y, float width, 
                                float height) {
        baseLoading(sprite, 0, 0, bodyType, categoryBits, maskBits, x, y, width, height);
    }
        
    protected void baseLoading(String sprite, BodyType bodyType, short categoryBits, 
                                short maskBits, float x, float y, float width, 
                                float height) {
        baseLoading(sprite, 0, 0, bodyType, categoryBits, maskBits, x, y, width, height);
    }
    
    protected void baseLoading(Sprite sprite, float offsetX, float offsetY,
                                BodyType bodyType, short categoryBits, 
                                short maskBits, float x, float y, float width, 
                                float height) {
        this._graphics = new GraphicsComponent(sprite, offsetX, offsetY);
        this._physics = new PhysicsComponent(x, y, bodyType, categoryBits, 
                                            maskBits, width, height);
        this._physics.getBody().setUserData(this);
        this._sensors = new ArrayList<SensorComponent>();
    }
    
    protected void baseLoading(String sprite, int offsetX, int offsetY,
                                BodyType bodyType, short categoryBits, 
                                short maskBits, float x, float y, float width, 
                                float height) {
        this._graphics = new GraphicsComponent(sprite, offsetX, offsetY);
        this._physics = new PhysicsComponent(x, y, bodyType, categoryBits, 
                                            maskBits, width, height);
        this._physics.getBody().setUserData(this);
        this._sensors = new ArrayList<SensorComponent>();
    }
    
    protected void initStatistic(int actionPoint, int healthPoint) {
        this._statistic = new Statistic(actionPoint, healthPoint);
    }
    
    @Override
    public void updateGraphics(SpriteBatch batch) {
        this._graphics.update(batch, this.getPhysicsComponent().getBody().getTransform().getPosition().x, 
                              this.getPhysicsComponent().getBody().getTransform().getPosition().y);
    }
    
    @Override
    public void updatePhysics() {
        this._physics.update();
        for(int i = 0;  i < this._sensors.size(); i++)
            this._sensors.get(i).update(this.getPhysicsComponent().getPosition().x, this.getPhysicsComponent().getPosition().y);
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
    public Statistic getStatistic() {
        return this._statistic;
    }
    
    @Override
    public void dispose() {
        this._physics.dispose();
        this._graphics.dispose();
    }
    
}
