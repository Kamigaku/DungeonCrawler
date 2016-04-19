package com.kamigaku.dungeoncrawler.level.implementation;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kamigaku.dungeoncrawler.entity.implementation.Player;
import com.kamigaku.dungeoncrawler.entity.implementation.Wall;
import com.kamigaku.dungeoncrawler.level.ALevel;
import java.util.ArrayList;

public class TestLevel extends ALevel {
    
    private Player _player;
    private ArrayList<Wall> _walls;

    @Override
    public void init() {
        super.baseLoading();
        
        //Chargement des textures
        this.textureLoading();
        
        this._player = new Player(new Sprite((Texture)(this.assetManager.get("sprites/player.png", Texture.class))), 19f, 10f);
        
        this._walls = new ArrayList<Wall>();
        for(int i = 0; i < 10; i++) {
            this._walls.add(new Wall(new Sprite((Texture)(this.assetManager.get("sprites/wall.png", Texture.class))), 0f, (float)i));
        }
        
    }
    
    
    private void textureLoading() {
        this.assetManager.load("sprites/player.png", Texture.class);
        this.assetManager.load("sprites/wall.png", Texture.class);
        this.assetManager.finishLoading();
    }
    
    @Override
    public void render(SpriteBatch batch) {
        /*this.mapRenderer.setView(camera);
        this.mapRenderer.render();*/
        batch.setProjectionMatrix(this.camera.combined);
        this.world.step(1/60f, 6, 2);
        batch.end();
        //this.hud.draw();
        batch.begin();
        this._player.update(batch);
        for(int i = 0; i < this._walls.size(); i++) {
            this._walls.get(i).update(batch);
        }
        /*this.camera.translate(LevelManager.getLevelManager().mainPlayer.getBody().getPosition().x - this.camera.position.x,
        					  LevelManager.getLevelManager().mainPlayer.getBody().getPosition().y - this.camera.position.y);*/
        this.camera.update();
    }
    
    @Override
    public void dispose() {
        super.dispose();
    }

}
