package com.kamigaku.dungeoncrawler.utility;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.RayCastCallback;

public class RayCastCallbackCustom implements RayCastCallback {

    private Fixture _smallestFixture = null;
    private float _smallestFraction = Float.MAX_VALUE;
    
    public RayCastCallbackCustom() {
    }

    @Override
    public float reportRayFixture(Fixture fixture, Vector2 point, Vector2 normal, float fraction) {
        if(fraction < _smallestFraction) {
            _smallestFraction = fraction;
            _smallestFixture = fixture;
        }
        return -1;
    }
    
    public Fixture getSmallestFixture() {
        return this._smallestFixture;
    }
    
}
