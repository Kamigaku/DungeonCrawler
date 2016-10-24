package com.kamigaku.dungeoncrawler.entity.implementation;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.kamigaku.dungeoncrawler.component.SensorComponent;
import com.kamigaku.dungeoncrawler.constants.Constants;
import com.kamigaku.dungeoncrawler.entity.AEntity;
import com.kamigaku.dungeoncrawler.entity.IEntity;
import com.kamigaku.dungeoncrawler.ia.AgressiveIA;
import com.kamigaku.dungeoncrawler.ia.IA;
import com.kamigaku.dungeoncrawler.singleton.FightManager;
import com.kamigaku.dungeoncrawler.singleton.FightManager.FightStatus;
import com.kamigaku.dungeoncrawler.singleton.LevelManager;
import com.kamigaku.dungeoncrawler.utility.RayCastCallbackCustom;
import java.util.ArrayList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Mob extends AEntity {
    
    private boolean _idleSensor = false;
    private IA _ia;
    private int _expGiven = 0;

    
    private final int ACTIONPOINT = 5;
    private final int HEALTHPOINT = 5;
    private final int INITIATIVE = 3;
    
    public Mob(String name, String spritePath, JSONArray skillsJson, JSONObject statisticJson, int level, int expGiven) {
        this.setName(name);
        super.baseLoadGraphics(spritePath, 0, 0);
        int actionPoint = ((Long)statisticJson.get("actionPoint")).intValue();
        int healthPoint = ((Long)statisticJson.get("healthPoint")).intValue();
        int initiative = ((Long)statisticJson.get("initiative")).intValue();
        int defense = ((Long)statisticJson.get("defense")).intValue();
        super.baseLoadStatistic(level, actionPoint, healthPoint, initiative, defense);
        super.baseLoadSkills();
        this._expGiven = expGiven;
        for(int i = 0; i < skillsJson.size(); i++) {
            this.addSkill((String)skillsJson.get(i), false);
        }
    }
    
    public Mob(Mob m, int x, int y) {
        this.setName(m.getName());
        super.baseLoadGraphics(m.getGraphicsComponent().getSprite(), 0, -0.25f);
        super.baseLoadPhysics(BodyDef.BodyType.DynamicBody, x, y, Constants.CATEGORY_MONSTER, 
                (short) (Constants.CATEGORY_SCENERY), 8, 8);
        super.baseLoadSensor();
        super.baseLoadStatistic(m.getStatistic());
        super.addSensor(new SensorComponent(this, BodyDef.BodyType.DynamicBody, 
                Constants.CATEGORY_SENSOR, Constants.CATEGORY_PLAYER, 30f));
        this._ia = new AgressiveIA(this);
        super.baseLoadSkills();
        super.baseLoadCommands();
        for(int i = 0; i < m.getSkills().size(); i++) {
            this.addSkill(m.getSkills().get(i).getSkillName(), false);
        }
    }
    
    public void updateIA() {
        this._ia.setTurnOver(false);
        while(this.getStatistic().currentActionPoint > 0 && !this._ia.turnOver()) {
            this._ia.update();
        }
    }

    @Override
    public void beginContact(Contact contact) {
        if(FightManager.getFightManager().getFightStatus() == FightStatus.NONE && !this._idleSensor) {
            // ICI, faire un test sur toutes les entitées, prendre les positions de chacune de ses entitiés
            // (en excluant bien sûr les Players et l'entité même) et tester si un Raycast vers toutes les entités en
            // combat, si oui, ajouter dans les entités en combat
            FightManager.getFightManager().getFighters().clear();
            final ArrayList<IEntity> fighters = FightManager.getFightManager().getFighters();
            fighters.add(this);
            for(IEntity entity : LevelManager.getLevelManager().getLevel().getEntities()) {
                if(entity instanceof Player)
                    fighters.add(entity);
                else if(entity != this) {
                    for(IEntity fighter : fighters) {
                        Vector2 direction = entity.getPhysicsComponent().getPosition().sub(
                                                fighter.getPhysicsComponent().getPosition());
                        direction.nor();
                        float normX = direction.x;
                        float normY = direction.y;
                        direction = new Vector2(normX + fighter.getPhysicsComponent().getPosition().x,
                                                normY + fighter.getPhysicsComponent().getPosition().y);                        
                        RayCastCallbackCustom rc_custom = new RayCastCallbackCustom();                        
                        LevelManager.getLevelManager().getLevel().raycastWorld(rc_custom, entity.getPhysicsComponent().getPosition(), direction);                        
                        Fixture fixture = rc_custom.getSmallestFixture();
                        if(fixture != null && fixture.getBody().getUserData() == fighter) {
                            fighters.add(entity);
                            break;
                        }
                    }
                }
            }
            FightManager.getFightManager().setFightStatus(FightStatus.BEGIN);
        }
    }

    @Override
    public void endContact(Contact contact) {
        if(FightManager.getFightManager().getFightStatus() != FightStatus.NONE)
            this._idleSensor = true;
        else
            this._idleSensor = false;
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
    }

    
    
}
