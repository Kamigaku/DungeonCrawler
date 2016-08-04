package com.kamigaku.dungeoncrawler.level.implementation;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kamigaku.dungeoncrawler.comparator.RenderingComparator;
import com.kamigaku.dungeoncrawler.constants.Constants;
import com.kamigaku.dungeoncrawler.entity.IEntity;
import com.kamigaku.dungeoncrawler.entity.implementation.Player;
import com.kamigaku.dungeoncrawler.level.ALevel;
import java.awt.Point;
import java.util.ArrayList;

public class TestLevel extends ALevel {
    
    private ArrayList<IEntity> _entities;
    private Player _player;

    @Override
    public void init() {
                                                                                
        this.textureLoading();                                                  //Chargement des textures
        
        super.baseLoading();
       
        this._entities = new ArrayList<IEntity>();
        Point randomTile = this.map.getEntryRoom().getFirstFloorTilesPosition();
        this._player = new Player(
                new Sprite((Texture)(this.assetManager.get("sprites/player.png", Texture.class))), 
                randomTile.x, randomTile.y);
        this._entities.add(this._player);
    }
    
    
    private void textureLoading() {
        this.assetManager = new AssetManager();
        this.assetManager.load("sprites/player.png", Texture.class);
        this.assetManager.load("sprites/wall.png", Texture.class);
        this.assetManager.load("sprites/ground.png", Texture.class);
        this.assetManager.load("sprites/door.png", Texture.class);
        this.assetManager.finishLoading();
    }
    
    @Override
    public void render(SpriteBatch batch) {
        this._player.updateInput();                                             // Input that impatct velocity
        this.world.step(1/60f, 6, 2);                                           // World physics
        for(int i = 0; i < this._entities.size(); i++)                          // Update all the physics related bodies
            this._entities.get(i).updatePhysics();
        this.camera.position.set(
                              this._player.getPhysicsComponent().getPosition().x,
                              this._player.getPhysicsComponent().getPosition().y,
                              this.camera.position.z);                          // Setting the camera position
        this.camera.update();                                                   // Updating the camera position
        batch.setProjectionMatrix(this.camera.combined);
        this._entities.sort(new RenderingComparator());                         // Rendering on depthAxis
        this.map.render(batch);                                                 // Map graphics
        for(int i = 0; i < this._entities.size(); i++)                          // Entities graphics
            this._entities.get(i).updateGraphics(batch);
        batch.end();
        if(Constants.DEBUG)
            this.debugRenderer.render(this.world, this.camera.combined);
        this.hud.draw();
        batch.begin();
    }
    
    @Override
    public void dispose() {
        super.dispose();
        for(int i = 0; i < this._entities.size(); i++) {
            this._entities.get(i).dispose();
        }
    }

}
