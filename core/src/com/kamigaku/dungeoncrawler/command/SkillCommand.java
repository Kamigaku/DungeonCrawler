/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kamigaku.dungeoncrawler.command;

import com.kamigaku.dungeoncrawler.entity.IEntity;
import com.kamigaku.dungeoncrawler.skills.ISkill;
import com.kamigaku.dungeoncrawler.skills.ISkill.SKILL_TARGET;

/**
 *
 * @author Kamigaku
 */
public class SkillCommand extends ACommand {

    private final ISkill _skill;
    
    public SkillCommand(ISkill skill, IEntity target) {
        this._skill = skill;
        this._target = target;
        this._ap = skill.getApCost();
    }
    
    public SkillCommand(ISkill skill) {
        this._skill = skill;
        this._ap = skill.getApCost();
    }
    
    @Override
    public void execute() {
        // Lot of work here
        this._skill.getCaster().getStatistic().removeAP(this._skill.getApCost());
        SKILL_TARGET skillTarget = this._skill.getSkillTarget();
        if(skillTarget == SKILL_TARGET.ENNEMY || skillTarget== SKILL_TARGET.ALLY) { 
            // j'attaque la cible selectionnée
        }
        else {
            // multi-target, je vais recup le terrain et testé
        }
    }

    @Override
    public void reverse() {
        // Lot of work here
        this._skill.getCaster().getStatistic().addAP(this._skill.getApCost());
        SKILL_TARGET skillTarget = this._skill.getSkillTarget();
        if(skillTarget == SKILL_TARGET.ENNEMY || skillTarget== SKILL_TARGET.ALLY) { 
            // j'attaque la cible selectionnée
        }
        else {
            // multi-target
        }
    }

    @Override
    public void simulate() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String toString() {
        return "Using skill " + this._skill.getSkillName();
    }
    
    
    
}
