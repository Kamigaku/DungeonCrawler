package com.kamigaku.dungeoncrawler.entity;

import com.kamigaku.dungeoncrawler.skills.ISkill.SKILL_EFFECT;
import java.util.ArrayList;

public class Statistic {
    
    private final int _actionPoint;
    public int currentActionPoint;
    
    private final int _healthPoint;
    public int currentHealthPoint;
    
    private final int _defense;
    public int currentDefense;
    
    private final int _initiative;
    public int currentInitiative;
    
    private final int _level;
    
    public ArrayList<SKILL_EFFECT> status;
    
    public Statistic(int level, int actionPoint, int healthPoint, int initiative, int defense) {
        this._actionPoint = actionPoint;
        this.currentActionPoint = this._actionPoint;
        this._healthPoint = healthPoint;
        this.currentHealthPoint = this._healthPoint;
        this._initiative = initiative;
        this.currentInitiative = this._initiative;
        this.status = new ArrayList<SKILL_EFFECT>();
        this._defense = defense;
        this._level = level;
    }
    
    public Statistic(Statistic s) {
        this._actionPoint = s._actionPoint;
        this.currentActionPoint = s._actionPoint;
        this._healthPoint = s._healthPoint;
        this.currentHealthPoint = s._healthPoint;
        this._initiative = s._initiative;
        this.currentInitiative = s._initiative;
        this.status = new ArrayList<SKILL_EFFECT>();
        this._defense = s._defense;
        this._level = s._level;
    }
    
    public void reset() {
        this.currentActionPoint = _actionPoint;
        this.currentDefense = _defense;
        this.currentHealthPoint = _healthPoint;
        this.currentInitiative = _initiative;
    }
    
    // @TODO check value si < 0
    public void removeAP(int value) {
        this.currentActionPoint -= value;
    }    
    
    public void removeHP(int value) {
        int hpRemoved = value - this.currentDefense;
        removeDefense(value);
        System.out.println("[Statistic][removeHP] I have been removed " + value + " hp.");
        this.currentHealthPoint -= hpRemoved;
        if(this.currentHealthPoint < 0)
            this.currentHealthPoint = 0;
    }
    
    public void removeDefense(int value) {
        this.currentDefense -= value;
        this.currentDefense = this.currentDefense < 0 ? 0 : this.currentDefense;
    }
    
    public void removeStatus(int index) {
        this.status.remove(index);
    }
    
    public void removeStatus(SKILL_EFFECT skillEffect) {
        for (int i = status.size() - 1; i >= 0; i--) {
            if(status.get(i) == skillEffect)
                status.remove(i);            
        }
    }
    
    // @TODO check value si > _actionPoint
    public void addAP(int value) {
        this.currentActionPoint += value;
    }
    
    public void addHP(int value) {
        this.currentHealthPoint += value;
    }
    
    public void addDefense(int value) {
        this.currentDefense += value;
    }
    
    public void addStatus(SKILL_EFFECT skillEffect) {
        this.status.add(skillEffect);
    }

    @Override
    public String toString() {
        String phrase = "Level " + this._level + "\n" +
               "HP : " + this.currentHealthPoint + "/" + this._healthPoint + "\n" +
               "Defense : " + this.currentDefense + "\nAffected by :\n";
        if(this.status.isEmpty())
            phrase += "None";
        for(int i = 0; i < this.status.size(); i++) {
            if(i == 0)
                phrase += this.status.get(i).name();
            else
                phrase += ", " + this.status.get(i).name();
        }
        return phrase;
    }

    public void newTurn() {
        this.currentActionPoint = this._actionPoint;
    }
    
    
    
}
