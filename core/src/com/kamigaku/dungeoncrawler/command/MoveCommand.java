package com.kamigaku.dungeoncrawler.command;

import com.badlogic.gdx.math.Vector2;
import com.kamigaku.dungeoncrawler.entity.IEntity;
import java.awt.Point;

public class MoveCommand extends ACommand {

    private final Point _destination;
    private final Point _origin;
    
    public MoveCommand(Point origin, Point destination, IEntity entity, int ap) {
        this._destination = destination;
        this._origin = origin;
        this._target = entity;
        this._ap = ap;
    }
    
    @Override
    public void execute() {
        Vector2 tmpVector = new Vector2(_destination.x, _destination.y);
        this._target.getStatistic().removeAP(this._ap);
        this._target.getPhysicsComponent().getBody().setTransform(tmpVector, 0);
    }
    
    @Override
    public void reverse() {
        Vector2 tmpVector = new Vector2(_origin.x, _origin.y);
        this._target.getPhysicsComponent().getBody().setTransform(tmpVector, 0);
        this._target.getStatistic().addAP(this._ap);
    }

    @Override
    public void simulate() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public String toString() {
        return "Deplacement de (" + _origin.x + ";" + _origin.y + ") vers (" + _destination.x + ";" + _destination.y + ")";
    }
    
    
    
}
