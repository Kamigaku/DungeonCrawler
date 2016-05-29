package com.kamigaku.dungeoncrawler.level;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.kamigaku.dungeoncrawler.component.PhysicsComponent;

public interface ILevel {

    void init();
    void render(SpriteBatch batch);
    void dispose();
    void addInputProcessor(InputProcessor ip);
    AssetManager getAssetManager();
    OrthographicCamera getCamera();
    Body addBody(BodyDef bodyDef);
    void removeBody(PhysicsComponent physics);
	
}
