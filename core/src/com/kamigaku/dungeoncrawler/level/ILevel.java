package com.kamigaku.dungeoncrawler.level;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.kamigaku.dungeoncrawler.component.PhysicsComponent;
import com.kamigaku.dungeoncrawler.entity.IEntity;
import com.kamigaku.dungeoncrawler.entity.implementation.Player;
import com.kamigaku.dungeoncrawler.hud.HUD;
import com.kamigaku.dungeoncrawler.tile.Layer;
import com.kamigaku.dungeongenerator.Map;
import java.util.ArrayList;

public interface ILevel {

    void init();
    void render(SpriteBatch batch);
    void dispose();
    void addInputProcessor(InputProcessor ip, boolean activated);
    void setInputProcessor(InputProcessor ip, boolean activated);
    AssetManager getAssetManager();
    OrthographicCamera getCamera();
    Map getMap();
    Layer getLayer(String layerTitle);
    HUD getHUD();
    ArrayList<IEntity> getEntities();
    Player getMainPlayer();
    Body addBody(BodyDef bodyDef);
    void removeBody(PhysicsComponent physics);
	
}
