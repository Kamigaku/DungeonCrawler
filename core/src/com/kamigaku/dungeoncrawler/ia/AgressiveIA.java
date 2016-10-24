package com.kamigaku.dungeoncrawler.ia;

import com.kamigaku.dungeoncrawler.command.MoveCommand;
import com.kamigaku.dungeoncrawler.command.SkillCommand;
import com.kamigaku.dungeoncrawler.entity.IEntity;
import com.kamigaku.dungeoncrawler.singleton.FightManager;
import com.kamigaku.dungeoncrawler.singleton.LevelManager;
import com.kamigaku.dungeoncrawler.skills.ISkill;
import com.kamigaku.dungeoncrawler.utility.Utility;
import com.kamigaku.dungeongenerator.dijkstra.Dijkstra;
import com.kamigaku.dungeongenerator.dijkstra.Rule;
import java.awt.Point;
import java.util.ArrayList;

public class AgressiveIA implements IA {

    private final IEntity _origin;
    private boolean turnOver = false;
    
    public AgressiveIA(IEntity origin) {
        this._origin = origin;
    }
    
    @Override
    public void update() {
        // trouve la cible la plus proche, se deplace vers elle et essaye de l'attaquer
        IEntity closestEntity = getClosestEntity();
        if(closestEntity != null) {
            Point originPos = this._origin.getPhysicsComponent().getPointPosition();
            Point closestPos = closestEntity.getPhysicsComponent().getPointPosition();
            for(int i = 0; i < this._origin.getSkills().size(); i++) {
                ISkill curSkill = this._origin.getSkills().get(i);
                for(ISkill.SKILL_ORIENTATION so : ISkill.SKILL_ORIENTATION.values()) {
                    if(curSkill.getRange(so) != null) {
                        if(Utility.isInRange(originPos, closestPos, curSkill.getRange(so))) {
                            if(curSkill.getApCost() <= this._origin.getStatistic().currentActionPoint) {
                                curSkill.setSkillOrientation(so);
                                if(curSkill.getSkillTarget() == ISkill.SKILL_TARGET.TARGET)
                                    this._origin.addCommand(new SkillCommand(curSkill, closestEntity)); // @TODO faire qqchose de mieux
                                else
                                    this._origin.addCommand(new SkillCommand(curSkill));
                                return;
                            }
                        }
                    }   
                }
            }
            Dijkstra d = new Dijkstra(LevelManager.getLevelManager().getLevel().getMap().getMap());
            d.addRule(new Rule(' ', ' ', true, false));
            d.createNodes(false);
            ArrayList<Point> path = d.shortestPathFromTo(originPos, closestPos);
            this._origin.addCommand(new MoveCommand(originPos, path.get(path.size() - 1), _origin, 1));
        }
        else {
            this.turnOver = true;
        }
    }
    
    private IEntity getClosestEntity() {
        IEntity closestEntity = null;
        double closestDistance = Double.MAX_VALUE;
        for(int i = 0; i < FightManager.getFightManager().getFighters().size(); i++) {
            if(FightManager.getFightManager().getFighters().get(i) != this._origin) {
                double curDistance = FightManager.getFightManager().getFighters().get(i).getPhysicsComponent().
                                    getPointPosition().distance(
                                    _origin.getPhysicsComponent().getPointPosition());
                if((closestEntity == null || curDistance < closestDistance) && curDistance > 1) {
                    closestEntity = FightManager.getFightManager().getFighters().get(i);
                    closestDistance = curDistance;
                }
            }
        }
        return closestEntity;
    }
    
    @Override
    public boolean turnOver() {
        return this.turnOver;
    }
    
    @Override
    public void setTurnOver(boolean value) {
        this.turnOver = value;
    }
    
}
