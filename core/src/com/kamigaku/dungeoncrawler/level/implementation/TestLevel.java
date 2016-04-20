package com.kamigaku.dungeoncrawler.level.implementation;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kamigaku.dungeoncrawler.comparator.RenderingComparator;
import com.kamigaku.dungeoncrawler.entity.IEntity;
import com.kamigaku.dungeoncrawler.entity.implementation.Player;
import com.kamigaku.dungeoncrawler.entity.implementation.Wall;
import com.kamigaku.dungeoncrawler.level.ALevel;
import java.util.ArrayList;

public class TestLevel extends ALevel {
    
    private ArrayList<IEntity> _entities;
    private Player _player;

    @Override
    public void init() {
        super.baseLoading();
        
        //Chargement des textures
        this.textureLoading();
        
        this._entities = new ArrayList<IEntity>();
        this._player = new Player(new Sprite((Texture)(this.assetManager.get("sprites/player.png", Texture.class))), 1f, 2f);
        this._entities.add(this._player);
        
        for(int i = 0; i < 10; i++) {
            this._entities.add(new Wall(new Sprite((Texture)(this.assetManager.get("sprites/wall.png", Texture.class))), 0f, (float)i));
        }
        
    }
    
    
    private void textureLoading() {
        this.assetManager.load("sprites/player.png", Texture.class);
        this.assetManager.load("sprites/wall.png", Texture.class);
        this.assetManager.finishLoading();
    }
    
    @Override
    public void render(SpriteBatch batch) {
        batch.setProjectionMatrix(this.camera.combined);
        this.world.step(1/60f, 6, 2);
        this._entities.sort(new RenderingComparator()); // Rendering on depthAxis
        for(int i = 0; i < this._entities.size(); i++) {
            this._entities.get(i).update(batch);
        }
        this.camera.translate(this._player.getPhysicsComponent().getBody().getPosition().x - this.camera.position.x,
                              this._player.getPhysicsComponent().getBody().getPosition().y - this.camera.position.y);
        this.camera.update();
        batch.end();
        this.hud.draw();
        batch.begin();
    }
    
    @Override
    public void dispose() {
        super.dispose();
    }

}
