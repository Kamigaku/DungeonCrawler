/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kamigaku.dungeoncrawler.utility;

import com.badlogic.gdx.Gdx;

/**
 *
 * @author Kamigaku
 */
public class Timer {
    
    public float whenCast;
    private boolean endCast;
    private float cd;

    public Timer(float cd) {
            this.cd = cd;
            this.whenCast = 0;
            endCast = true;
    }

    public void update() {
            float delta = Gdx.graphics.getDeltaTime();
            if(!endCast) {
                    whenCast += delta;
            }
            if(whenCast > cd)
                    endCast = true;
    }

    public boolean getendCast() {
            return endCast;
    }

    public void setendCast(boolean b) {
            this.endCast = b;
            resetCast();
    }

    public void resetCast() {
            this.whenCast = 0;
    }
    
}
