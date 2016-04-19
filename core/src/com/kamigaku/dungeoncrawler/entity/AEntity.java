/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.kamigaku.dungeoncrawler.entity;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.kamigaku.dungeoncrawler.component.GraphicsComponent;
import com.kamigaku.dungeoncrawler.component.PhysicsComponent;



public abstract class AEntity implements IEntity {

    protected GraphicsComponent _graphics;
    protected PhysicsComponent _physics;
    
    // Initiliasation de différentes informations communes
    protected void baseLoading(Sprite sprite, float x, float y) {
        this._graphics = new GraphicsComponent(sprite);
        this._physics = new PhysicsComponent(x, y);
    }

    @Override
    public GraphicsComponent getGraphicsComponent() {
        return this._graphics;
    }

    @Override
    public PhysicsComponent getPhysicsComponent() {
        return this._physics;
    }
    
}
