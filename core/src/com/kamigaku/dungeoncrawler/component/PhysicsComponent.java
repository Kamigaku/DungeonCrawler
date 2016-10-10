package com.kamigaku.dungeoncrawler.component;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.utils.Disposable;
import com.kamigaku.dungeoncrawler.constants.Constants;
import com.kamigaku.dungeoncrawler.singleton.LevelManager;
import java.awt.Point;

public class PhysicsComponent implements Disposable {
    
    private Body _body;
    
    public float _forceX;
    public float _forceY;
    
    /**
     * Create a convex body
     * @param x The x position of the Body
     * @param y The y position of the Body
     * @param bodyType The type of the Body
     * @param category The body category
     * @param collideWith The categories that the body will collide with
     * @param vertices All the vertices
     */
    public PhysicsComponent(float x, float y, BodyType bodyType, short category,
                            short collideWith, float[] vertices) {
        this.bodyDefinition(bodyType, x, y);
        /*for(int i = 0; i < vertices.length; i++)
            vertices[i] = vertices[i] / Constants.TILE_WIDTH;*/
        PolygonShape collider = new PolygonShape();
        collider.set(vertices);
        this.bodyFixture(collider, category, collideWith);
        collider.dispose();
    }
    
    /**
     * Create a box body
     * @param x The x position of the Body
     * @param y The y position of the Body
     * @param bodyType The type of the Body
     * @param category The body category
     * @param collideWith The categories that the body will collide with
     * @param width The width of the box
     * @param height The height of the box
     */
    public PhysicsComponent(float x, float y, BodyType bodyType, short category, 
                            short collideWith, float width, float height) {
        this.bodyDefinition(bodyType, x, y);
        PolygonShape collider = new PolygonShape();
        collider.setAsBox(width / Constants.TILE_WIDTH, height / Constants.TILE_HEIGHT);
        this.bodyFixture(collider, category, collideWith);
        collider.dispose();
    }
    
    /**
     * Create a circle body
     * @param x The x position of the Body
     * @param y The y position of the Body
     * @param bodyType The type of the Body
     * @param category The body category
     * @param collideWith The categories that the body will collide with
     * @param radius The radius of the circle
     */
    public PhysicsComponent(float x, float y, BodyType bodyType, short category, 
                            short collideWith, float radius) {
        this.bodyDefinition(bodyType, x, y);
        PolygonShape collider = new PolygonShape();
        collider.setRadius(radius / Constants.TILE_WIDTH);
        this.bodyFixture(collider, category, collideWith);
        collider.dispose();
    }
    
    private void bodyDefinition(BodyType bodyType, float x, float y) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = bodyType;
        bodyDef.position.set(x, y);
        this._body = LevelManager.getLevelManager().getLevel().addBody(bodyDef);
    }
    
    private void bodyFixture(Shape collider, short category, short collideWith) {
        FixtureDef fDef = new FixtureDef();
        fDef.density = 0f;
        fDef.friction = 0f;
        fDef.restitution = 0f;
        fDef.shape = collider;
        fDef.filter.categoryBits = category;
        fDef.filter.maskBits = collideWith;
        this._body.createFixture(fDef);
    }
    
    public void update() {
        this._body.setLinearVelocity(this._forceX, this._forceY);
    }
    
    public void addForceY(float forceY) {
        this._forceY = Math.min(Math.max(_forceY+forceY, -Constants.PLAYER_SPEED), Constants.PLAYER_SPEED);
    }
    
    public void addForceX(float forceX) {
        this._forceX = Math.min(Math.max(_forceX+forceX, -Constants.PLAYER_SPEED), Constants.PLAYER_SPEED);
    }
    
    public void setForceY(float forceY) {
        this._forceY = forceY;
    }
    
    public void setForceX(float forceX) {
        this._forceX = forceX;
    }
    
    public void setForceXY(float forceX, float forceY) {
        this.setForceX(forceX);
        this.setForceY(forceY);
    }
    
    public Body getBody() {
        return this._body;
    }
    
    public Vector2 getPosition() {
        return this._body.getPosition();
    }
    
    public Point getPointPosition() {
        return new Point((int)this._body.getPosition().x, 
                         (int)this._body.getPosition().y);
    }
    
    @Override
    public void dispose() {
    }

}
