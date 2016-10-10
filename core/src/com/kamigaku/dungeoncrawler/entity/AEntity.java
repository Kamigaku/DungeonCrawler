package com.kamigaku.dungeoncrawler.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.kamigaku.dungeoncrawler.component.*;
import com.kamigaku.dungeoncrawler.item.IItem;
import com.kamigaku.dungeoncrawler.singleton.LevelManager;
import com.kamigaku.dungeoncrawler.skills.ISkill;
import com.kamigaku.dungeoncrawler.skills.Skill;
import com.kamigaku.dungeoncrawler.skills.SkillBuilder;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.*;
import org.json.simple.parser.*;



public abstract class AEntity implements IEntity {
    
    //@TODO : création d'un collider pour la souris afin d'afficher les informations lors de la collision avec un joueur
    
    protected GraphicsComponent _graphics;
    protected PhysicsComponent _physics;
    protected ArrayList<SensorComponent> _sensors;
    protected Statistic _statistic;
    protected ArrayList<IItem> _items;
    protected ArrayList<ISkill> _skills;
    
    
    /* GRAPHICS LOADING */
    
    protected void baseLoadGraphics(String sprite, float offsetX, float offsetY) {
        this._graphics = new GraphicsComponent(sprite, offsetX, offsetY);
    }
    
    protected void baseLoadGraphics(String sprite) {
        baseLoadGraphics(sprite, 0, 0);
    }
    
    protected void baseLoadGraphics(Sprite sprite, float offsetX, float offsetY) {
        this._graphics = new GraphicsComponent(sprite, offsetX, offsetY);
    }
    
    protected void baseLoadGraphics(Sprite sprite) {
        this._graphics = new GraphicsComponent(sprite, 0, 0);
    }
    
    /* PHYSICS LOADING */
    
    protected void baseLoadPhysics(BodyType bodyType, float x, float y, short category,
                                   short collideWith, float width, float height) {
        this._physics = new PhysicsComponent(x, y, bodyType, category, collideWith, 
                                            width, height);
        this._physics.getBody().setUserData(this);
    }
    
    protected void baseLoadPhysics(BodyType bodyType, float x, float y, short category,
                                   short collideWith, float[] vertices) {
        this._physics = new PhysicsComponent(x, y, bodyType, category, collideWith, 
                                            vertices);
        this._physics.getBody().setUserData(this);
    }
    
    protected void baseLoadPhysics(BodyType bodyType, float x, float y, short category,
                                   short collideWith, float radius) {
        this._physics = new PhysicsComponent(x, y, bodyType, category, collideWith, 
                                            radius);
        this._physics.getBody().setUserData(this);
    }

    /* SENSORS LOADING */
    
    protected void baseLoadSensor() {
        this._sensors = new ArrayList<SensorComponent>();
    }
    
    /* STATISTIC LOADING */
    
    protected void baseLoadStatistic(int actionPoint, int healthPoint) {
        this._statistic = new Statistic(actionPoint, healthPoint);
    }
    
    /* ITEM LOADING */
    
    protected void baseLoadItems() {
        this._items = new ArrayList<IItem>();
    }
    
    /* SKILLS LOADING */
    
    protected void baseLoadSkills() {
        this._skills = new ArrayList<ISkill>();
        JSONParser jParser = new JSONParser();
        try {
            JSONArray array = (JSONArray) jParser.parse(Gdx.files.internal("skills/skills.json").reader());
            System.out.println("There is right now " + array.size() + " skills.");
            for(int i = 0; i < array.size(); i++) {
                Skill s = SkillBuilder.createSkill((JSONObject) array.get(i), this);
                this._skills.add(s);
                LevelManager.getLevelManager().getLevel().getHUD().addActionCommand(s);
                
            }
        } catch (IOException ex) {
            Logger.getLogger(AEntity.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(AEntity.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void updateGraphics(SpriteBatch batch) {
        this._graphics.update(batch, this.getPhysicsComponent().getBody().getTransform().getPosition().x, 
                              this.getPhysicsComponent().getBody().getTransform().getPosition().y);
    }
    
    @Override
    public void updatePhysics() {
        this._physics.update();
        for(int i = 0;  i < this._sensors.size(); i++)
            this._sensors.get(i).update(this.getPhysicsComponent().getPosition().x, this.getPhysicsComponent().getPosition().y);
    }

    @Override
    public void addItem(IItem item) {
        this._items.add(item);
    }

    @Override
    public GraphicsComponent getGraphicsComponent() {
        return this._graphics;
    }

    @Override
    public PhysicsComponent getPhysicsComponent() {
        return this._physics;
    }
    
    @Override
    public ArrayList<SensorComponent> getSensorsComponent() {
        return this._sensors;
    }
    
    @Override
    public Statistic getStatistic() {
        return this._statistic;
    }
    
    @Override
    public void dispose() {
        this._physics.dispose();
        this._graphics.dispose();
    }
    
}
