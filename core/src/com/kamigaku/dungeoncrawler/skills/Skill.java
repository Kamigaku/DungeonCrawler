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
    private IEntity _caster;
    private final SKILL_TARGET _skillTarget;
    private final ArrayList<SkillEffect> _skillEffects;
    private SKILL_ORIENTATION _skillOrientation;
    
    private ArrayList<Point>[] _orientedRange;
    
    public Skill(Skill s, IEntity caster, boolean addToHUD) {
        this._caster = caster;
        this._skillName = s._skillName;
        this._apCost = s._apCost;
        this._skillTarget = s._skillTarget;
        this._skillEffects = s._skillEffects;
        this._skillOrientation = s._skillOrientation;
        this._textButton = s._textButton;
        if(addToHUD)
            this.addListenerToTextButton();
        this._orientedRange = s._orientedRange;
    }
    
    public Skill(IEntity caster, String skillName, int apCost, SKILL_TARGET focusATarget,
                 ArrayList<SkillEffect> skillEffects, ArrayList<Point> range) {
        this._caster = caster;
        this._skillName = skillName;
        this._apCost = apCost;
        this._skillTarget = focusATarget;
        this._skillEffects = skillEffects;
        this._skillOrientation = SKILL_ORIENTATION.TOP;
        this._textButton = new TextButton(skillName, LevelManager.getLevelManager().getLevel().getHUD().getSkin());
        if(this._caster != null)
            this.addListenerToTextButton();
        
        this._orientedRange = new ArrayList[4];
        this._orientedRange[0] = range;
        for(int i = 1; i < 4; i++)
            this._orientedRange[i] = new ArrayList<Point>(range.size());
        for(int i = 0; i < range.size(); i++) {
            this._orientedRange[SKILL_ORIENTATION.RIGHT.ordinal()].add(new Point(range.get(i).y, -range.get(i).x));
            this._orientedRange[SKILL_ORIENTATION.BOT.ordinal()].add(new Point(-range.get(i).x, -range.get(i).y));
            this._orientedRange[SKILL_ORIENTATION.LEFT.ordinal()].add(new Point(-range.get(i).y, -range.get(i).x));
        }
    }
    
    public Skill(IEntity caster, String skillName, int apCost, SKILL_TARGET focusATarget,
                 ArrayList<SkillEffect> skillEffects) {
        this._caster = caster;
        this._skillName = skillName;
        this._apCost = apCost;
        this._skillTarget = focusATarget;
        this._skillEffects = skillEffects;
        this._skillOrientation = SKILL_ORIENTATION.TOP;
        this._textButton = new TextButton(skillName, LevelManager.getLevelManager().getLevel().getHUD().getSkin());
        this._textButton.addListener(new ClickListener() {            
            @Override
            public void clicked(InputEvent event, float x, float y) {
                FightManager.getFightManager().selectedSkill = Skill.this;
            }
        });
    }
    
    private void addListenerToTextButton() {
        this._textButton.addListener(new ClickListener() {            
            @Override
            public void clicked(InputEvent event, float x, float y) {
                FightManager.getFightManager().selectedSkill = Skill.this;
            }

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                if(pointer == -1 && FightManager.getFightManager().selectedSkill == null) {
                    Layer l = LevelManager.getLevelManager().getLevel().getLayer(Layer.SKILL_HIGHLIGHTER);
                    for(int i = 0; i < _orientedRange[_skillOrientation.ordinal()].size(); i++) {
                        Point casterPosition = _caster.getPhysicsComponent().getPointPosition();
                        l.addTile(new GroundHighlighter("sprites/ground_highlighter.png", 
                                    casterPosition.x + _orientedRange[_skillOrientation.ordinal()].get(i).x,
                                    casterPosition.y + _orientedRange[_skillOrientation.ordinal()].get(i).y));
                    }
                }
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                if(pointer == -1)
                    if(FightManager.getFightManager().selectedSkill == null)
                        LevelManager.getLevelManager().getLevel().getLayer(Layer.SKILL_HIGHLIGHTER).removeAllTiles();
                    else if(FightManager.getFightManager().selectedSkill == Skill.this) {
                        LevelManager.getLevelManager().getLevel().getLayer(Layer.SKILL_HIGHLIGHTER).removeAllTiles();
                        Skill.this.setSkillOrientation(SKILL_ORIENTATION.TOP);
                    }
            }
            
        });
    }
    
    public void setCaster(IEntity caster) {
        this._caster = caster;
    }

    @Override
    public ArrayList<Point> getRange(SKILL_ORIENTATION skillOrientation) {
        return this._orientedRange[skillOrientation.ordinal()];
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

    @Override
    public ArrayList<SkillEffect> getSkillEffects() {
        return this._skillEffects;
    }

    @Override
    public SKILL_ORIENTATION getSkillOrientation() {
        return this._skillOrientation;
    }
    
    @Override
    public void setSkillOrientation(SKILL_ORIENTATION skillOrientation) {
        if(skillOrientation != this._skillOrientation) {
            this._skillOrientation = skillOrientation;
            Layer l = LevelManager.getLevelManager().getLevel().getLayer(Layer.SKILL_HIGHLIGHTER);
            l.removeAllTiles();
            for(int i = 0; i < _orientedRange[skillOrientation.ordinal()].size(); i++) {
                Point casterPosition = _caster.getPhysicsComponent().getPointPosition();
                l.addTile(new GroundHighlighter("sprites/ground_highlighter.png", 
                            casterPosition.x + _orientedRange[skillOrientation.ordinal()].get(i).x,
                            casterPosition.y + _orientedRange[skillOrientation.ordinal()].get(i).y));
            }
        }
    }
    
}
