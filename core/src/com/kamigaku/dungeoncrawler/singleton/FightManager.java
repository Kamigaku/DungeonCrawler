package com.kamigaku.dungeoncrawler.singleton;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.QueryCallback;
import com.kamigaku.dungeoncrawler.command.ICommand;
import com.kamigaku.dungeoncrawler.command.MoveCommand;
import com.kamigaku.dungeoncrawler.command.SkillCommand;
import com.kamigaku.dungeoncrawler.constants.Constants;
import com.kamigaku.dungeoncrawler.entity.IEntity;
import com.kamigaku.dungeoncrawler.entity.implementation.Mob;
import com.kamigaku.dungeoncrawler.entity.implementation.Player;
import com.kamigaku.dungeoncrawler.skills.ISkill;
import com.kamigaku.dungeoncrawler.skills.Skill;
import com.kamigaku.dungeoncrawler.tile.Layer;
import com.kamigaku.dungeoncrawler.tile.Tile;
import com.kamigaku.dungeoncrawler.utility.Timer;
import com.kamigaku.dungeongenerator.dijkstra.Dijkstra;
import com.kamigaku.dungeongenerator.dijkstra.Rule;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

public class FightManager {

    public Skill selectedSkill;
       
    public enum FightStatus {
        NONE, BEGIN, BEGIN_PICKING, PICKING, FIGHT, END_PICKING, END
    };
    
    private static FightManager _fightManager;                                  // Singleton
    
    private FightStatus _fightStatus;
    private final HashMap<Integer, Tile> _ptr_groundTiles;
    private final Layer _ptr_groundSelector;
    private final Layer _ptr_skillHighlighter;
    private final Player _ptr_mainPlayer;
    private final InputProcessor _ip;
    private final Dijkstra _dijkstra;
    
    private final ArrayList<IEntity> _fighters;
    
    private final Timer timer;
    private ArrayList<ICommand> _queueCommands;
    
    public static FightManager getFightManager() {
        if(_fightManager == null)
            _fightManager = new FightManager();
        return _fightManager;
    }
    
    public FightManager() {
        this.timer = new Timer(1);
        this._queueCommands = new ArrayList<ICommand>();
        this._fighters = new ArrayList<IEntity>();
        this._fightStatus = FightStatus.NONE;
        this._ptr_groundTiles = LevelManager.getLevelManager().getLevel().getLayer(Layer.GROUND).getTiles();
        this._ptr_groundSelector = LevelManager.getLevelManager().getLevel().getLayer(Layer.GROUND_SELECTOR);
        this._ptr_skillHighlighter = LevelManager.getLevelManager().getLevel().getLayer(Layer.SKILL_HIGHLIGHTER);
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
                    if(selectedSkill != null) { // Un skill est sélectionné et j'ai cliqué
                        selectedSkill.getCaster().addCommand(new SkillCommand(selectedSkill));
                        selectedSkill = null;
                        LevelManager.getLevelManager().getLevel().getLayer(Layer.SKILL_HIGHLIGHTER).removeAllTiles();
                    }
                    else { // Aucun skill n'est sélectionné
                        Point playerPos = new Point(Math.round(_ptr_mainPlayer.getPhysicsComponent().getPosition().x), 
                                                    Math.round(_ptr_mainPlayer.getPhysicsComponent().getPosition().y));
                        ArrayList<Point> path = _dijkstra.shortestPathFromTo(playerPos, pointerPosition);
                        if(path != null) {
                            for(int i = path.size() - 1; i >= 0; i--) {
                                if(i == path.size() - 1)
                                    _ptr_mainPlayer.addCommand(new MoveCommand(playerPos, path.get(i), _ptr_mainPlayer, 1));
                                else
                                    _ptr_mainPlayer.addCommand(new MoveCommand(path.get(i + 1), path.get(i), _ptr_mainPlayer, 1));
                            }
                        }
                        else
                            System.out.println("Le point est inaccessible");
                    }
                }
                else if(button == Buttons.RIGHT) {
                    selectedSkill = selectedSkill != null ? null : selectedSkill;
                    LevelManager.getLevelManager().getLevel().getLayer(Layer.SKILL_HIGHLIGHTER).removeAllTiles();
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
                if(selectedSkill != null) {
                    int widthScreen = Gdx.graphics.getWidth();
                    int heightScreen = Gdx.graphics.getHeight();
                    screenY = (heightScreen - (screenY + 1)) - (heightScreen / 2);
                    screenX = (screenX + 1) - (widthScreen / 2);
                    Vector2 v = new Vector2(screenX, screenY);
                    float angle = v.nor().angle();
                    if(angle <= 315 && angle > 225)                             // bas
                        selectedSkill.setSkillOrientation(ISkill.SKILL_ORIENTATION.BOT);
                    else if(angle <= 225 && angle > 135)                        // gauche
                        selectedSkill.setSkillOrientation(ISkill.SKILL_ORIENTATION.LEFT);
                    else if(angle <= 135 && angle > 45)                         // haut
                        selectedSkill.setSkillOrientation(ISkill.SKILL_ORIENTATION.TOP);
                    else                                                        // droite
                       selectedSkill.setSkillOrientation(ISkill.SKILL_ORIENTATION.RIGHT);
                }
                
                Vector3 mousePosition = LevelManager.getLevelManager().getCamera().
                                        unproject(new Vector3(screenX, screenY, 0));
                Vector2 mousePosition_bl = new Vector2(mousePosition.x - (1 / Constants.VIRTUAL_HEIGHT), mousePosition.y - (1 / Constants.VIRTUAL_HEIGHT));
                Vector2 mousePosition_tr = new Vector2(mousePosition.x + (1 / Constants.VIRTUAL_HEIGHT), mousePosition.y + (1 / Constants.VIRTUAL_HEIGHT));
                LevelManager.getLevelManager().getLevel().queryWorld(new QueryCallback() {
                    @Override
                    public boolean reportFixture(Fixture fixture) {
                        if(!fixture.isSensor() && fixture.getBody().getUserData() instanceof IEntity) {
                            IEntity currentOverlaps = (IEntity)fixture.getBody().getUserData();
                            LevelManager.getLevelManager().getLevel().getHUD().displayEntityStats(currentOverlaps);
                            return false;
                        }
                        LevelManager.getLevelManager().getLevel().getHUD().displayEntityStats(null);
                        return true;
                    }
                }, mousePosition_bl, mousePosition_tr);
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
        this._ptr_skillHighlighter.render = fightStatus != FightStatus.NONE;
    }
    
    public FightStatus getFightStatus() {
        return this._fightStatus;
    }
    
    public void update() {
        switch(this._fightStatus) {
            case PICKING:
                break;
            case FIGHT:
                timer.update();
                if(timer.getendCast()) {
                    if(_queueCommands.isEmpty()) {
                        for(int i = 0; i < this._fighters.size(); i++)
                            this._fighters.get(i).popCommand();
                        this._queueCommands = this.getQueueCommands();
                        if(this._queueCommands.isEmpty())
                            setFightStatus(FightStatus.BEGIN_PICKING);
                    }
                    else {
                        _queueCommands.get(0).execute();
                        testEntityDead();
                        _queueCommands.remove(0);
                    }
                    timer.setendCast(false);
                }
                break;
            case END_PICKING:
                LevelManager.getLevelManager().getLevel().setInputProcessor(this._ip, false);
                this.setFightStatus(FightStatus.FIGHT);
                for(IEntity entity : this._fighters) {
                    if(entity instanceof Player) {
                        for(int i = entity.getCommands().size() - 1; i >= 0; i--)
                            entity.getCommands().get(i).reverse();
                    }                        
                }
                for(IEntity entity : this._fighters) {
                    if(!entity.isDead()) {
                        if(entity instanceof Mob) {
                            ((Mob)entity).updateIA();
                            for(int i = entity.getCommands().size() - 1; i >= 0; i--)
                                entity.getCommands().get(i).reverse();
                        }
                    }
                }
                this._queueCommands = this.getQueueCommands();
                LevelManager.getLevelManager().getLevel().getHUD().displayFightTables(false);
                System.out.println("THE TURN IS OVER");
                break;
            case BEGIN_PICKING: // @TODO : clear toutes les commandes + tableau HUD, ce qui pourrait être sympa + tard
                // cela serait d'ajouter à la fin d'un Fight une nouvelle classe appelée "Turn", qui contiendra le résumé du
                // tour précédent, pour avoir un replay possible comme ça
                boolean stillHaveEnnemy = false;
                for(int i = 0; i < this._fighters.size(); i++) {
                    this._fighters.get(i).getPhysicsComponent().getBody().setActive(true);
                    this._fighters.get(i).getStatistic().newTurn();
                    if(this._fighters.get(i) instanceof Mob && !this._fighters.get(i).isDead())
                        stillHaveEnnemy = true;
                }
                if(!stillHaveEnnemy)
                    this.setFightStatus(FightStatus.END);
                else {
                    LevelManager.getLevelManager().getLevel().setInputProcessor(this._ip, true);
                    this.setFightStatus(FightStatus.PICKING);
                    LevelManager.getLevelManager().getLevel().getHUD().displayFightTables(true);
                    System.out.println("THE TURN BEGIN");
                }
                break;                
            case BEGIN:
                for(int i = 1; i < this._fighters.size(); i++) {
                    Vector2 currentPosition = this._fighters.get(i).getPhysicsComponent().getPosition();
                    this._fighters.get(i).getPhysicsComponent().getBody().setTransform(
                            (int)Math.round(currentPosition.x), (int)Math.round(currentPosition.y), 0);
                    this._fighters.get(i).getPhysicsComponent().getBody().setActive(false);
                    this._fighters.get(i).getPhysicsComponent().setForceXY(0, 0);
                }
                this.setFightStatus(FightStatus.BEGIN_PICKING);
                LevelManager.getLevelManager().getLevel().setInputProcessor(
                        _ptr_mainPlayer.getInputComponent().getInputProcessor(), false);
                LevelManager.getLevelManager().getLevel().getHUD().displayFightTables(true);
                break;
            case END:
                this.setFightStatus(FightStatus.NONE);
                for(int i = 0; i < this._fighters.size(); i++) {
                    if(this._fighters.get(i).isDead())
                        this._fighters.get(i).dispose();
                    else
                        this._fighters.get(i).getPhysicsComponent().getBody().setActive(true);
                }
                LevelManager.getLevelManager().getLevel().setInputProcessor(this._ip, false);
                LevelManager.getLevelManager().getLevel().setInputProcessor(
                        _ptr_mainPlayer.getInputComponent().getInputProcessor(), true);
                LevelManager.getLevelManager().getLevel().getHUD().displayFightTables(false);
                break;
        }
    }
    
    private ArrayList<ICommand> getQueueCommands() {
        this._fighters.sort(new Comparator<IEntity>() {
            @Override
            public int compare(IEntity o1, IEntity o2) {
                return o1.getStatistic().currentInitiative - o2.getStatistic().currentInitiative;
            }
            
        });
        ArrayList<ICommand> commands = new ArrayList<ICommand>();
        for(int i = 0; i < this._fighters.size(); i++) {
            IEntity curEntity = this._fighters.get(i);
            if(curEntity.getCommands().size() > 0)
                commands.add(curEntity.getCommands().get(0));
        }
        return commands;
    }
    
    private void testEntityDead() {
        for(int i = 0; i < this._fighters.size(); i++) {
            IEntity curEntity = this._fighters.get(i);
            if(curEntity.isDead()) {
                curEntity.getCommands().clear();
                for(int j = this._queueCommands.size() - 1; j >= 0; j--) {
                    if(this._queueCommands.get(j).getCaller() == curEntity)
                        this._queueCommands.remove(j);
                }
            }
        }
    }
    
    public ArrayList<IEntity> getFighters() {
        return this._fighters;
    }
    
}
