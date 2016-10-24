package com.kamigaku.dungeoncrawler.level.implementation;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kamigaku.dungeoncrawler.comparator.RenderingComparator;
import com.kamigaku.dungeoncrawler.constants.Constants;
import com.kamigaku.dungeoncrawler.entity.IEntity;
import com.kamigaku.dungeoncrawler.entity.implementation.Mob;
import com.kamigaku.dungeoncrawler.entity.implementation.Player;
import com.kamigaku.dungeoncrawler.level.ALevel;
import com.kamigaku.dungeoncrawler.singleton.FightManager;
import com.kamigaku.dungeoncrawler.singleton.MobManager;
import java.awt.Point;
import java.util.ArrayList;

public class TestLevel extends ALevel {
    
    @Override
    public void init() {
                                                                                
        this.textureLoading();                                                  //Chargement des textures
        
        super.baseLoading();
       
        this._entities = new ArrayList<IEntity>();
        Point randomTile = this.map.getEntryRoom().getFirstFloorTilesPosition();
        this._mainPlayer = new Player(
                new Sprite((Texture)(this.assetManager.get("sprites/player.png", Texture.class))), 
                randomTile.x, randomTile.y);
        Point ra = this.map.getEntryRoom().getAllGround().get(5);
        Mob mob = new Mob(MobManager.getMobManager().getMob("Orc"), ra.x + (96 / Constants.VIRTUAL_HEIGHT), ra.y);
        //Mob mob2 = new Mob(MobManager.getMobManager().getMob("Orc"), ra.x + ((6 * 32) / Constants.VIRTUAL_HEIGHT), ra.y);
        Mob mob3 = new Mob(MobManager.getMobManager().getMob("Orc"), 0, 0);
        this._entities.add(this._mainPlayer);
        this._entities.add(mob);
        //this._entities.add(mob2);
        this._entities.add(mob3);
    }
    
    
    private void textureLoading() {
        this.assetManager = new AssetManager();
        // @TODO : load un dossier complet
        this.assetManager.load("sprites/player.png", Texture.class);
        this.assetManager.load("sprites/wall.png", Texture.class);
        this.assetManager.load("sprites/ground.png", Texture.class);
        this.assetManager.load("sprites/door.png", Texture.class);
        this.assetManager.load("sprites/walls.png", Texture.class);
        this.assetManager.load("sprites/ground_selector.png", Texture.class);
        this.assetManager.load("sprites/ground_highlighter.png", Texture.class);
        this.assetManager.load("sprites/default.png", Texture.class);
        this.assetManager.finishLoading();
    }
    
    @Override
    public void render(SpriteBatch batch) {                                     // Input that impact velocity
        if(FightManager.getFightManager().getFightStatus() != 
                FightManager.FightStatus.NONE) {                                 // Le combat à demarré
            FightManager.getFightManager().update();
        }
        updatePhysics();
        updateCamera(batch);
        updateGraphics(batch);
    }
    
    private void updatePhysics() {
        this.world.step(1/60f, 6, 2);                                           // World physics
        for(int i = 0; i < this._entities.size(); i++)                          // Update all the physics related bodies
            this._entities.get(i).updatePhysics();
    }
    
    private void updateCamera(SpriteBatch batch) {
        this.camera.position.set(
                  this._mainPlayer.getPhysicsComponent().getPosition().x,
                  this._mainPlayer.getPhysicsComponent().getPosition().y,
                  this.camera.position.z);                                      // Setting the camera position
        this.camera.update();                          
        batch.setProjectionMatrix(this.camera.combined);
    }
    
    private void updateGraphics(SpriteBatch batch) {
        this._entities.sort(new RenderingComparator());                         // Rendering on depthAxis
        for(int i = 0; i < this.layers.size(); i++)
            this.layers.get(i).render(batch);
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
