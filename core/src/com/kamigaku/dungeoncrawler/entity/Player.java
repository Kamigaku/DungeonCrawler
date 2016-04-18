/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.kamigaku.dungeoncrawler.entity;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * 
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
public class Player extends AEntity {

    public Player(Sprite sprite, float x, float y) {
        super.baseLoading(sprite, x, y);
    }

    @Override
    public void update(SpriteBatch batch) {
        this._graphics.update(batch, 0, 0);
    }

}
