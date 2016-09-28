package com.kamigaku.dungeoncrawler.singleton;

import com.badlogic.gdx.math.Vector2;
import com.kamigaku.dungeoncrawler.constants.Constants;
import com.kamigaku.dungeoncrawler.entity.IEntity;
import com.kamigaku.dungeoncrawler.tile.Layer;
import com.kamigaku.dungeoncrawler.tile.Tile;
import java.util.ArrayList;
import java.util.HashMap;
import jdk.nashorn.internal.runtime.regexp.joni.ast.ConsAltNode;

public class FightManager {
    
    public enum FightStatus {
        NONE, BEGIN, FIGHT, END
    };
        
    private static FightManager _fightManager;                                  // Singleton
    
    private FightStatus _fightStatus;
    private final HashMap<Integer, Tile> _ptr_groundTiles;
    private final Layer _ptr_groundSelector;
    private final ArrayList<IEntity> _ptr_entities;
    
    public static FightManager getFightManager() {
        if(_fightManager == null)
            _fightManager = new FightManager();
        return _fightManager;
    }
    
    public FightManager() {
        this._fightStatus = FightStatus.NONE;
        this._ptr_groundTiles = LevelManager.getLevelManager().getLevel().getLayer(Layer.GROUND).getTiles();
        this._ptr_groundSelector = LevelManager.getLevelManager().getLevel().getLayer(Layer.GROUND_SELECTOR);
        this._ptr_entities = LevelManager.getLevelManager().getLevel().getEntities();
    }
    
    public void setFightStatus(FightStatus fightStatus) {
        this._fightStatus = fightStatus;
        this._ptr_groundSelector.render = fightStatus != FightStatus.NONE;
    }
    
    public FightStatus getFightStatus() {
        return this._fightStatus;
    }
    
    public void update() {
        switch(this._fightStatus) {
            case FIGHT:
                System.out.println("FIGHTING");
                break;
            case BEGIN:
                for(int i = 0; i < this._ptr_entities.size(); i++) {
                    Vector2 currentPosition = this._ptr_entities.get(i).getPhysicsComponent().getPosition();
                    int closestX = Math.round(currentPosition.x);
                    int closestY = Math.round(currentPosition.y);
                    System.out.println("Putting it at : " + closestX + " / " + closestY);
                    this._ptr_entities.get(i).getPhysicsComponent().getBody().setTransform(closestX, closestY, 0);
                    this._ptr_entities.get(i).getPhysicsComponent().setForceXY(0f, 0f);
                }
                this._fightStatus = FightStatus.FIGHT;
                break;
            case END:
                for(int i = 0; i < this._ptr_entities.size(); i++) {
                    this._ptr_entities.get(i).getPhysicsComponent().setForceXY(0, 0);                    
                }
                this._fightStatus = FightStatus.NONE;
                break;
        }
    }
    
}
