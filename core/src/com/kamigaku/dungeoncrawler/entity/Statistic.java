package com.kamigaku.dungeoncrawler.entity;

public class Statistic {
    
    private int _actionPoint;
    public int currentActionPoint;
    
    private int _healthPoint;
    public int currentHealthPoint;
    
    private int _initiative;
    
    public Statistic(int actionPoint, int healthPoint) {
        this._actionPoint = actionPoint;
        this.currentActionPoint = this._actionPoint;
        this._healthPoint = healthPoint;
        this.currentHealthPoint = this._healthPoint;
    }
    
    public void reset() {
        this.currentActionPoint = _actionPoint;
    }
    
    // @TODO check value si < 0
    public void removeAP(int value) {
        this.currentActionPoint -= value;
    }    
    
    public void removeHP(int value) {
        this.currentHealthPoint -= value;
    }
    
    // @TODO check value si > _actionPoint
    public void addAP(int value) {
        this.currentActionPoint += value;
    }
    
    public void addHP(int value) {
        this.currentHealthPoint += value;
    }
    
}
