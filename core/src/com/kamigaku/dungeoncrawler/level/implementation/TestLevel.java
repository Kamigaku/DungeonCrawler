package com.kamigaku.dungeoncrawler.level.implementation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kamigaku.dungeoncrawler.comparator.RenderingComparator;
import com.kamigaku.dungeoncrawler.entity.IEntity;
import com.kamigaku.dungeoncrawler.entity.implementation.Player;
import com.kamigaku.dungeoncrawler.level.ALevel;
import java.util.ArrayList;

public class TestLevel extends ALevel {
    
    private ArrayList<IEntity> _entities;
    private Player _player;

    @Override
    public void init() {
        //Chargement des textures
        this.textureLoading();
        
        super.baseLoading();
       
        this._entities = new ArrayList<IEntity>();
        //this._player = new Player("sprites/player.png", 1f, 2f);
        this._player = new Player(new Sprite((Texture)(this.assetManager.get("sprites/player.png", Texture.class))), 150f, 143f);
        this._entities.add(this._player);
    }
    
    
    private void textureLoading() {
        this.assetManager = new AssetManager();
        //this.assetManager.setLoader(null, null);
        this.assetManager.load("sprites/player.png", Texture.class);
        this.assetManager.load("sprites/wall.png", Texture.class);
        this.assetManager.load("sprites/ground.png", Texture.class);
        this.assetManager.finishLoading();
    }
    
    @Override
    public void render(SpriteBatch batch) {
        this.world.step(1/60f, 6, 2); 
        batch.setProjectionMatrix(this.camera.combined);
        this._entities.sort(new RenderingComparator()); // Rendering on depthAxis
        this.map.render(batch);
        for(int i = 0; i < this._entities.size(); i++) {
            this._entities.get(i).update(batch);
        }
        this.camera.position.set(this._player.getGraphicsComponent().getSprite().getX() + this._player.getGraphicsComponent().getSprite().getWidth() / 2,
                              this._player.getGraphicsComponent().getSprite().getY() + this._player.getGraphicsComponent().getSprite().getHeight() / 2,
                              this.camera.position.z);
        this.camera.update();
        batch.end();
        //this.debugRenderer.render(this.world, this.camera.combined);
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
