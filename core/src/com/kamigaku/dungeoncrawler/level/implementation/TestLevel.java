package com.kamigaku.dungeoncrawler.level.implementation;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kamigaku.dungeoncrawler.entity.implementation.Player;
import com.kamigaku.dungeoncrawler.level.ALevel;

public class TestLevel extends ALevel {
    
    private Player _player;

    @Override
    public void init() {
        super.baseLoading();
        
        //Chargement des textures
        this.textureLoading();
        
        this._player = new Player(new Sprite((Texture)(this.assetManager.get("sprites/player.png", Texture.class))), 19f, 10f);
    }
    
    
    private void textureLoading() {
        this.assetManager.load("sprites/player.png", Texture.class);
        this.assetManager.finishLoading();
    }
    
    @Override
    public void render(SpriteBatch batch) {
        /*this.mapRenderer.setView(camera);
        this.mapRenderer.render();*/
        batch.setProjectionMatrix(this.camera.combined);
        this.world.step(1/60f, 6, 2);
        batch.end();
        batch.begin();
        this._player.update(batch);
        /*this.camera.translate(LevelManager.getLevelManager().mainPlayer.getBody().getPosition().x - this.camera.position.x,
        					  LevelManager.getLevelManager().mainPlayer.getBody().getPosition().y - this.camera.position.y);*/
        this.camera.update();
    }
    
    @Override
    public void dispose() {
        super.dispose();
    }

}
