package com.kamigaku.dungeoncrawler.skills;

import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.kamigaku.dungeoncrawler.entity.IEntity;
import java.awt.Point;
import java.util.ArrayList;

public interface ISkill {
    
    public enum SKILL_SHAPE {
        CROSS, LEFT_LINE, RIGHT_LINE, FRONT_LINE, BACK_LINE, FRONT_AND_BACK_LINE, 
        LEFT_AND_RIGHT_LINE, CIRCLE
    }
    
    public enum SKILL_TARGET {
        NONE, ME, EVERYONE, TARGET
    }
    
    public enum SKILL_EFFECT {
        DAMAGE, DEFEND, HEAL, SLOW, STUN, ROOT
    }
    
    public enum SKILL_ORIENTATION {
        TOP, LEFT, RIGHT, BOT
    }
    
    ArrayList<Point> getRange(SKILL_ORIENTATION orientation);
    TextButton getTextButton();
    SKILL_TARGET getSkillTarget();
    int getApCost();
    String getSkillName();
    IEntity getCaster();
    SKILL_ORIENTATION getSkillOrientation();
    ArrayList<SkillEffect> getSkillEffects();
    void setSkillOrientation(SKILL_ORIENTATION skillOrientation);
    
}
