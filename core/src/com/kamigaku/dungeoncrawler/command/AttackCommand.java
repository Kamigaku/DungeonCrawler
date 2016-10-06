/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kamigaku.dungeoncrawler.command;

import com.kamigaku.dungeoncrawler.entity.IEntity;

/**
 *
 * @author Kamigaku
 */
public class AttackCommand extends ACommand {
    
    private IEntity _origin;
    
    public AttackCommand(IEntity origin, IEntity target, int ap) {
        this._origin = origin;
        this._target = target;
        this._ap = ap;
    }

    @Override
    public void execute() {
        this._origin.getStatistic().removeAP(this._ap);
        this._target.getStatistic().removeHP(2); // @TODO remplacer par la valeur d'attaque
    }

    @Override
    public void reverse() {
        this._origin.getStatistic().addAP(this._ap);
        this._target.getStatistic().addHP(2); // @TODO remplacer par la valeur d'attaque
    }

    @Override
    public String toString() {
        return "Attacking target at " + this._target.getPhysicsComponent().getPosition();
    }
}
