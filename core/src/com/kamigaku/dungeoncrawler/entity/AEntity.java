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
    
    // Position stuff
    public float x;
    public float y;
    
    // Initiliasation de différentes informations communes
    protected void baseLoading(Sprite sprite, float x, float y) {
        this._graphics = new GraphicsComponent(sprite);
        this._physics = new PhysicsComponent(x, y);
        this.x = x;
        this.y = y;
    }
    
}
