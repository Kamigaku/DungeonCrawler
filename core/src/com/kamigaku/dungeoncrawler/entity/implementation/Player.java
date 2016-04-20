/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.kamigaku.dungeoncrawler.entity.implementation;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Shape;
import com.kamigaku.dungeoncrawler.component.InputComponent;
import com.kamigaku.dungeoncrawler.entity.AEntity;

public class Player extends AEntity {
    
    private final InputComponent _input;
    private Shape circle;

    public Player(Sprite sprite, float x, float y) {
        super.baseLoading(sprite, BodyType.DynamicBody, x, y, 8, 8);
        this._input = new InputComponent(this);
    }

    @Override
    public void update(SpriteBatch batch) {
        this._input.update();
        this._physics.update();
        this._graphics.update(batch, this.getPhysicsComponent().getPosition().x, this.getPhysicsComponent().getPosition().y + 8);
    }

}
