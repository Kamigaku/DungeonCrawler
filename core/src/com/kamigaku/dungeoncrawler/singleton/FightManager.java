package com.kamigaku.dungeoncrawler.singleton;

import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.kamigaku.dungeoncrawler.command.AttackCommand;
import com.kamigaku.dungeoncrawler.command.MoveCommand;
import com.kamigaku.dungeoncrawler.entity.IEntity;
import com.kamigaku.dungeoncrawler.entity.implementation.Player;
import com.kamigaku.dungeoncrawler.tile.Layer;
import com.kamigaku.dungeoncrawler.tile.Tile;
import com.kamigaku.dungeongenerator.dijkstra.Dijkstra;
import com.kamigaku.dungeongenerator.dijkstra.Rule;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;

public class FightManager {
    
    public enum FightStatus {
        NONE, BEGIN, BEGIN_PICKING, PICKING, FIGHT, END_PICKING, END
    };
        
    private static FightManager _fightManager;                                  // Singleton
    
    private FightStatus _fightStatus;
    private final HashMap<Integer, Tile> _ptr_groundTiles;
    private final Layer _ptr_groundSelector;
    private final ArrayList<IEntity> _ptr_entities;
    private final Player _ptr_mainPlayer;
    private final InputProcessor _ip;
    private final Dijkstra _dijkstra;
    
    public boolean entityAttackingPick = false;
    
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
        this._ptr_mainPlayer = LevelManager.getLevelManager().getLevel().getMainPlayer();
        this._ip = new InputProcessor() {
            @Override
            public boolean keyDown(int keycode) {
                if(keycode == Keys.ESCAPE)
                    setFightStatus(FightStatus.END);
                return false;
            }

            @Override
            public boolean keyUp(int keycode) {
                return false;
            }

            @Override
            public boolean keyTyped(char character) {
                return false;
            }

            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                Vector3 unprojected = LevelManager.getLevelManager().getCamera().unproject(new Vector3(screenX, screenY, 0));
                Point pointerPosition = new Point(Math.round(unprojected.x), Math.round(unprojected.y));
                if(button == Buttons.LEFT) {
                    if(entityAttackingPick) {
                        for(int i = 0; i < _ptr_entities.size(); i++) {
                            IEntity current = _ptr_entities.get(i);
                            if(current != _ptr_mainPlayer &&
                               current.getPhysicsComponent().getPosition().x == pointerPosition.x &&
                               current.getPhysicsComponent().getPosition().y == pointerPosition.y) {
                                _ptr_mainPlayer.addCommand(new AttackCommand(_ptr_mainPlayer, current, 1));
                                System.out.println("Attacking");
                                entityAttackingPick = false;
                                break;
                            }
                        }
                    }
                    else {
                        Point playerPos = new Point(Math.round(_ptr_mainPlayer.getPhysicsComponent().getPosition().x), 
                                                    Math.round(_ptr_mainPlayer.getPhysicsComponent().getPosition().y));
                        if((Math.abs(playerPos.x - pointerPosition.x) + Math.abs(playerPos.y - pointerPosition.y)) 
                                <= _ptr_mainPlayer.getStatistic().currentActionPoint) {
                            ArrayList<Point> path = _dijkstra.shortestPathFromTo(playerPos, pointerPosition);
                            if(path != null && path.size() <= _ptr_mainPlayer.getStatistic().currentActionPoint) {
                                _ptr_mainPlayer.addCommand(new MoveCommand(playerPos, pointerPosition, _ptr_mainPlayer, path.size()));
                            }
                            else {
                                System.out.println("Le point est inacessible");
                            }
                        }
                        else {
                            System.out.println("Le point est trop eloigné");
                        }
                    }
                }
                else if(button == Buttons.RIGHT) {
                    if(_fightManager.entityAttackingPick)
                        _fightManager.entityAttackingPick = false;
                }
                return false;
            }

            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                return false;
            }

            @Override
            public boolean touchDragged(int screenX, int screenY, int pointer) {
                return false;
            }

            @Override
            public boolean mouseMoved(int screenX, int screenY) {
                return false;
            }

            @Override
            public boolean scrolled(int amount) {
                float oldValue = LevelManager.getLevelManager().getCamera().zoom;
                LevelManager.getLevelManager().getCamera().zoom += ((float)amount / 5f);
                if(LevelManager.getLevelManager().getCamera().zoom < 0.2)
                    LevelManager.getLevelManager().getCamera().zoom = oldValue;
                return false;
            }
        };
        LevelManager.getLevelManager().getLevel().addInputProcessor(this._ip, false);
        this._dijkstra = new Dijkstra(LevelManager.getLevelManager().getLevel().getMap().getMap());
        this._dijkstra.addRule(new Rule(' ', ' ', true, false));
        this._dijkstra.createNodes(false);
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
            case PICKING:
                break;
            case FIGHT:
                System.out.println("Fetching all commands");
                try {
                Thread.sleep(10000);
                } catch(Exception e) {                    
                }
                this.setFightStatus(FightStatus.BEGIN_PICKING);
                break;
            case END_PICKING:
                LevelManager.getLevelManager().getLevel().setInputProcessor(this._ip, false);
                this.setFightStatus(FightStatus.FIGHT);
                LevelManager.getLevelManager().getLevel().getHUD().displayFightTables(false);
                break;
            case BEGIN_PICKING: // @TODO : clear toutes les commandes + tableau HUD, ce qui pourrait être sympa + tard
                // cela serait d'ajouter à la fin d'un Fight une nouvelle classe appelée "Turn", qui contiendra le résumé du
                // tour précédent, pour avoir un replay possible comme ça
                LevelManager.getLevelManager().getLevel().setInputProcessor(this._ip, true);
                this.setFightStatus(FightStatus.PICKING);
                LevelManager.getLevelManager().getLevel().getHUD().displayFightTables(true);
                break;                
            case BEGIN:
                for(int i = 1; i < this._ptr_entities.size(); i++) { // opti à trouver ici, pb quand le perso plus dans cercle au début du combat
                    Vector2 currentPosition = this._ptr_entities.get(i).getPhysicsComponent().getPosition();
                    this._ptr_entities.get(i).getPhysicsComponent().getBody().setTransform(
                            (int)Math.round(currentPosition.x), (int)Math.round(currentPosition.y), 0);
                    this._ptr_entities.get(i).getPhysicsComponent().getBody().setActive(false);
                    this._ptr_entities.get(i).getPhysicsComponent().setForceXY(0, 0);
                }
                this.setFightStatus(FightStatus.BEGIN_PICKING);
                LevelManager.getLevelManager().getLevel().setInputProcessor(
                        _ptr_mainPlayer.getInputComponent().getInputProcessor(), false);
                LevelManager.getLevelManager().getLevel().getHUD().displayFightTables(true);
                break;
            case END:
                this.setFightStatus(FightStatus.NONE);
                for(int i = 0; i < this._ptr_entities.size(); i++) {
                    this._ptr_entities.get(i).getPhysicsComponent().getBody().setActive(true);
                }
                LevelManager.getLevelManager().getLevel().setInputProcessor(this._ip, false);
                LevelManager.getLevelManager().getLevel().setInputProcessor(
                        _ptr_mainPlayer.getInputComponent().getInputProcessor(), true);
                LevelManager.getLevelManager().getLevel().getHUD().displayFightTables(false);
                break;
        }
    }
    
}
