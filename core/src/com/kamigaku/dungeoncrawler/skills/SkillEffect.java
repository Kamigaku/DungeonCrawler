package com.kamigaku.dungeoncrawler.skills;

import com.kamigaku.dungeoncrawler.entity.IEntity;
import com.kamigaku.dungeoncrawler.skills.ISkill.SKILL_EFFECT;
import java.time.Duration;

public class SkillEffect {

    private final SKILL_EFFECT _skillEffect;
    private final int _value;
    // @TODO : add percentage, durée
    
    public SkillEffect(SKILL_EFFECT skillEffect, int value) {
        this._skillEffect = skillEffect;
        this._value = value;
    }
    
    public void execute(IEntity target) {
        switch(_skillEffect) {
            case DAMAGE:
                target.getStatistic().removeHP(_value);
                break;
            case DEFEND:
                target.getStatistic().addDefense(_value);
                break;
            case HEAL:
                target.getStatistic().addHP(_value);
                break;
            case SLOW: // attention, pas très bien ça
            case STUN:
            case ROOT:
                target.getStatistic().addStatus(_skillEffect);
                break;
            default:
                throw new AssertionError(_skillEffect.name());
        }
    }
    
    public void reverse(IEntity target) {
        switch(_skillEffect) {
            case DAMAGE:
                target.getStatistic().addHP(_value);
                break;
            case DEFEND:
                target.getStatistic().removeDefense(_value);
                break;
            case HEAL:
                target.getStatistic().removeHP(_value);
                break;
            case SLOW: // attention, pas très bien ça
            case STUN:
            case ROOT:
                target.getStatistic().removeStatus(_skillEffect);
                break;
            default:
                throw new AssertionError(_skillEffect.name());
            
        }
    }
    
}
