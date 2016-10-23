package com.kamigaku.dungeoncrawler.component;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.kamigaku.dungeoncrawler.constants.Constants;
import com.kamigaku.dungeoncrawler.entity.IEntity;
import com.kamigaku.dungeoncrawler.singleton.LevelManager;

public class SensorComponent {
    
    private Body _body;
    
    /** Constructor for a Box type Sensor
     * @param entity The concerned entity
     * @param bodyType Define the type of body (kinematic, dynamic or static)
     * @param height Height of the box
     * @param categoryBits The category of the collider
     * @param maskBits With what collide the collider
     * @param width Width of the box
    **/
    public SensorComponent(IEntity entity, BodyType bodyType, float width, 
                            short categoryBits, short maskBits, float height) {
        initBody(entity, bodyType);
        PolygonShape collider = new PolygonShape();
        collider.setAsBox(width / (Constants.TILE_WIDTH / 2), height / (Constants.TILE_HEIGHT / 2));
        defineFixtureDef(collider, categoryBits, maskBits);
    }
    
    /** Constructor for a Circle type Sensor 
     * @param entity The concerned entity
     * @param bodyType Define the type of body (kinematic, dynamic or static) 
     * @param categoryBits The category of the collider
     * @param maskBits With what collide the collider
     * @param radius Radius of the circle
    **/
    public SensorComponent(IEntity entity, BodyType bodyType, short categoryBits, 
                            short maskBits, float radius) {
        initBody(entity, bodyType);
        CircleShape collider = new CircleShape();
        collider.setRadius(radius / (Constants.TILE_WIDTH / 2));
        defineFixtureDef(collider, categoryBits, maskBits);
    }
    
    /** Constructor for a Polygon type Sensor 
     * @param entity The concerned entity
     * @param bodyType Define the type of body (kinematic, dynamic or static)
     * @param categoryBits The category of the collider
     * @param maskBits With what collide the collider
     * @param vertices Vertices of the polygon (format x, y)
    **/
    public SensorComponent(IEntity entity, BodyType bodyType, short categoryBits, 
                            short maskBits, Vector2[] vertices) {
        initBody(entity, bodyType);
        PolygonShape collider = new PolygonShape();
        collider.set(vertices);
        defineFixtureDef(collider, categoryBits, maskBits);
    }
    
    private void initBody(IEntity entity, BodyType bodyType) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = bodyType;
        bodyDef.position.set((entity.getPhysicsComponent().getPosition().x * Constants.TILE_WIDTH),
                            (entity.getPhysicsComponent().getPosition().y * Constants.TILE_HEIGHT));
        this._body = LevelManager.getLevelManager().getLevel().addBody(bodyDef);
        this._body.setUserData(entity);
    }
    
    private void defineFixtureDef(Shape collider, short categoryBits, short maskBits) {
        FixtureDef fDef = new FixtureDef();
        fDef.density = 0f;
        fDef.friction = 0f;
        fDef.restitution = 0f;
        fDef.shape = collider;
        fDef.isSensor = true;
        fDef.filter.categoryBits = categoryBits;
        fDef.filter.maskBits = maskBits;
        this._body.createFixture(fDef);
        collider.dispose();
    }
    
    public void update(float x, float y) {
        this._body.setTransform(x, y, 0);
    }
    
    public void dispose() {
        LevelManager.getLevelManager().getLevel().removeBody(this._body);
    }       

}