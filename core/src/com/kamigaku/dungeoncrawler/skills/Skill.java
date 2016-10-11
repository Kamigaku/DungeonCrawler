package com.kamigaku.dungeoncrawler.skills;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.kamigaku.dungeoncrawler.entity.IEntity;
import com.kamigaku.dungeoncrawler.singleton.FightManager;
import com.kamigaku.dungeoncrawler.singleton.LevelManager;
import com.kamigaku.dungeoncrawler.tile.GroundHighlighter;
import com.kamigaku.dungeoncrawler.tile.Layer;
import java.awt.Point;
import java.util.ArrayList;

public class Skill implements ISkill {
       
    private final TextButton _textButton;
    
    private final String _skillName;
    private final int _apCost;
    private final IEntity _caster;
    private final SKILL_TARGET _skillTarget;
    private ArrayList<Point> _range;
    
    public Skill(IEntity caster, String skillName, int apCost, SKILL_TARGET focusATarget,
                 ArrayList<Point> range) {
        this._caster = caster;
        this._skillName = skillName;
        this._apCost = apCost;
        this._skillTarget = focusATarget;
        this._textButton = new TextButton(skillName, LevelManager.getLevelManager().getLevel().getHUD().getSkin());
        this._textButton.addListener(new ClickListener() {            
            @Override
            public void clicked(InputEvent event, float x, float y) {
                FightManager.getFightManager().selectedSkill = Skill.this;
            }

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                if(pointer == -1) {
                    Layer l = LevelManager.getLevelManager().getLevel().getLayer(Layer.SKILL_HIGHLIGHTER);
                    for(int i = 0; i < _range.size(); i++) {
                        Point casterPosition = _caster.getPhysicsComponent().getPointPosition();
                        l.addTile(new GroundHighlighter("sprites/ground_highlighter.png", 
                                    casterPosition.x + _range.get(i).x,
                                    casterPosition.y + _range.get(i).y));
                    }
                }
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                if(pointer == -1) 
                    LevelManager.getLevelManager().getLevel().getLayer(Layer.SKILL_HIGHLIGHTER).removeAllTiles();
            }
            
        });
        this._range = range;
    }
    
    public Skill(IEntity caster, String skillName, int apCost, SKILL_TARGET focusATarget) {
        this._caster = caster;
        this._skillName = skillName;
        this._apCost = apCost;
        this._skillTarget = focusATarget;
        this._textButton = new TextButton(skillName, LevelManager.getLevelManager().getLevel().getHUD().getSkin());
        this._textButton.addListener(new ClickListener() {            
            @Override
            public void clicked(InputEvent event, float x, float y) {
                FightManager.getFightManager().selectedSkill = Skill.this;
            }
        });
    }

    @Override
    public ArrayList<Point> getRange() {
        return this._range;
    }
    
    @Override
    public TextButton getTextButton() {
        return this._textButton;
    }

    @Override
    public SKILL_TARGET getSkillTarget() {
        return this._skillTarget;
    }

    @Override
    public int getApCost() {
        return this._apCost;
    }

    @Override
    public String getSkillName() {
        return this._skillName;
    }

    @Override
    public IEntity getCaster() {
        return this._caster;
    }
    
}
