package com.kamigaku.dungeoncrawler.command;

import com.kamigaku.dungeoncrawler.entity.IEntity;
import com.kamigaku.dungeoncrawler.singleton.LevelManager;
import com.kamigaku.dungeoncrawler.skills.*;
import com.kamigaku.dungeoncrawler.skills.ISkill.*;
import com.kamigaku.dungeoncrawler.utility.Utility;
import java.util.ArrayList;

public class SkillCommand extends ACommand {

    private final ISkill _skill;
    private final SKILL_ORIENTATION _orientation;
    private final IEntity _target;

    public SkillCommand(ISkill skill) {
        this._skill = skill;
        this._ap = skill.getApCost();
        this._orientation = _skill.getSkillOrientation();
        this._caller = this._skill.getCaster();
        this._target = null;
    }
    
    public SkillCommand(ISkill skill, IEntity target) {
        this._skill = skill;
        this._ap = skill.getApCost();
        this._orientation = _skill.getSkillOrientation();
        this._caller = this._skill.getCaster();
        this._target = target;
    }
    
    
    @Override
    public void execute() {
        // Lot of work here
        System.out.println("[SkillCommand][execute] Executing : " + _skill.getSkillName() + " from " + _skill.getCaster().getClass().getName());
        this._skill.getCaster().getStatistic().removeAP(this._skill.getApCost());
        ArrayList<IEntity> allEntities = LevelManager.getLevelManager().getLevel().getEntities();
        switch(_skill.getSkillTarget()) {
            case NONE: // ?
                break;
            case ME:
                for(int i = 0; i < this._skill.getSkillEffects().size(); i++)
                    this._skill.getSkillEffects().get(i).execute(this._skill.getCaster());
                break;
            case EVERYONE:
                for(IEntity entity : allEntities) {
                    if(Utility.isInRange(this._skill.getCaster().getPhysicsComponent().getPointPosition(),
                                             entity.getPhysicsComponent().getPointPosition(),
                                             this._skill.getRange(_orientation))) {
                        for(int i = 0; i < this._skill.getSkillEffects().size(); i++)
                            this._skill.getSkillEffects().get(i).execute(entity);
                    }
                }
                break;
            case TARGET:
                for(int i = 0; i < this._skill.getSkillEffects().size(); i++)
                    this._skill.getSkillEffects().get(i).execute(_target);
                break;
            default:
                throw new AssertionError(_skill.getSkillTarget().name());
        }
    }
    
    @Override
    public void reverse() {
        this._skill.getCaster().getStatistic().addAP(this._skill.getApCost());
    }

    @Override
    public void simulate() {
        System.out.println("[SkillCommand][simulate] Simulating : " + _skill.getSkillName() + " from " + _skill.getCaster().getClass().getName());
        this._skill.getCaster().getStatistic().removeAP(this._skill.getApCost());
    }

    @Override
    public String toString() {
        return "Using skill " + this._skill.getSkillName();
    }
    
    
    
}
