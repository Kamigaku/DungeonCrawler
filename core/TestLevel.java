package com.kamigaku.dungeoncrawler.level.implementation;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kamigaku.dungeoncrawler.entity.implementation.Player;
import com.kamigaku.dungeoncrawler.level.ALevel;

public class TestLevel extends ALevel {
    
    private Player _player;

    public void TestLevel() {
        super.baseLoading();
        
        //Chargement des textures
        this.textureLoading();
        
        this._player = new Player(new Sprite((Texture)(this.getAssetManager().get("sprites/player.png", Texture.class))), 10f, 10f);
        System.out.println("ici");
    }
    
    private void textureLoading() {
        this.getAssetManager().load("sprites/player.png", Texture.class);
        this.getAssetManager().finishLoading();
    }
    
    @Override
    public void render(SpriteBatch batch) {
        System.out.println(_player);
        this._player.update(batch);
    }
    
    @Override
    public void dispose() {
        super.dispose();
    }

}
