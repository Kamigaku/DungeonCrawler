package com.kamigaku.dungeoncrawler.singleton;

import com.badlogic.gdx.Gdx;
import com.kamigaku.dungeoncrawler.entity.AEntity;
import com.kamigaku.dungeoncrawler.skills.ISkill;
import com.kamigaku.dungeoncrawler.skills.Skill;
import com.kamigaku.dungeoncrawler.skills.SkillEffect;
import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class SkillManager {
    
    /*
    Actuellement : 
        - name : le nom du skill
        - apCost : le coût en AP
        - (optionnelle) shape : la forme du skill (voir l'enum dans Skill)
            > de pair avec range : le range du skill
        - (optionnelle) points : les points ou le sort va toucher (par rapport à joueur)
        - skillTarget : enum, voir ISkill
        - les effets (degats, defend, slow, givre, etc...)
        - valeur des dégats
    
    A ajouter :
        - temps de cast
    
    */
    
    private static SkillManager _skillManager;
    private HashMap<String, Skill> _skills;
    
    public static SkillManager getSkillManager() {
        if(_skillManager == null)
            _skillManager = new SkillManager();
        return _skillManager;
    }
    
    private SkillManager() {
        // load skill
        this._skills = new HashMap<String, Skill>();
        JSONParser jParser = new JSONParser();
        try {
            JSONArray array = (JSONArray) jParser.parse(Gdx.files.internal("skills/skills.json").reader());
            System.out.println("[SkillManager][constructor] Loading " + array.size() + " skills.");
            for(int i = 0; i < array.size(); i++) {
                JSONObject skillJson = (JSONObject) array.get(i);
                String name = (String) skillJson.get("name");
                int apCost = ((Long)skillJson.get("apCost")).intValue();
                boolean needTarget = false;
                ISkill.SKILL_TARGET skillTarget = ISkill.SKILL_TARGET.valueOf((String) skillJson.get("skillTarget"));
                ArrayList<Point> points = null;
                ArrayList<SkillEffect> skillEffects = new ArrayList<SkillEffect>();

                if(skillJson.containsKey("shape")) {
                    ISkill.SKILL_SHAPE shape = ISkill.SKILL_SHAPE.valueOf((String) skillJson.get("shape"));
                    if(skillJson.containsKey("range")) {
                        points = new ArrayList<Point>();
                        int range = ((Long)skillJson.get("range")).intValue(); 
                        int x = 1;
                        int y = 1;
                        if(skillTarget == ISkill.SKILL_TARGET.ME || skillTarget == ISkill.SKILL_TARGET.EVERYONE) {
                            x = 0;
                            y = 0;
                        }
                        switch(shape) {
                            case BACK_LINE:
                                for(; y <= range; y++) points.add(new Point(0, -y));
                                break;
                            case CROSS:
                                for(; x <= range; x++) {
                                    points.add(new Point(-x, 0));
                                    points.add(new Point(x, 0));
                                }
                                for(; y <= range; y++) {
                                    points.add(new Point(0, -y));
                                    points.add(new Point(0, y));
                                }
                                break;
                            case LEFT_LINE:
                                for(; x <= range; x++) points.add(new Point(-x, 0));
                                break;
                            case RIGHT_LINE:
                                for(; x <= range; x++) points.add(new Point(x, 0));
                                break;
                            case FRONT_LINE:
                                for(; y <= range; y++) points.add(new Point(0, y));
                                break;
                            case FRONT_AND_BACK_LINE:
                                for(; y <= range; y++) {
                                    points.add(new Point(0, -y));
                                    points.add(new Point(0, y));
                                }
                                break;
                            case LEFT_AND_RIGHT_LINE:
                                for(; x <= range; x++) {
                                    points.add(new Point(-x, 0));
                                    points.add(new Point(x, 0));
                                }
                                break;
                            case CIRCLE:
                                points.add(new Point(-range, 0));
                                points.add(new Point(range, 0));
                                for(x = -range + 1; x < range; x++) {
                                    if(x == 0) {
                                        if(skillTarget == ISkill.SKILL_TARGET.ME || skillTarget == ISkill.SKILL_TARGET.EVERYONE)
                                            points.add(new Point(x, 0));
                                    }
                                    else
                                        points.add(new Point(x, 0));
                                    for(y = 1; y <= range - Math.abs(x); y++) {
                                        points.add(new Point(x, y));
                                        points.add(new Point(x, -y));
                                    }
                                }
                                break;
                            default:
                                throw new AssertionError(shape.name());
                        }
                    }
                }
                else if(skillJson.containsKey("points")) {
                    JSONArray allPoints = (JSONArray) skillJson.get("points");
                    points = new ArrayList<Point>(allPoints.size());
                    for(int j = 0; j < allPoints.size(); j++) {
                        JSONArray point = (JSONArray)allPoints.get(j);
                        points.add(new Point(((Long)point.get(0)).intValue(), 
                                             ((Long)point.get(1)).intValue()));
                    }
                }

                JSONArray skillEffectsJson = (JSONArray) skillJson.get("skillEffects");
                for(int j = 0; j < skillEffectsJson.size(); j++) {
                    JSONArray aSkillEffect = (JSONArray)skillEffectsJson.get(j);
                    skillEffects.add(new SkillEffect(ISkill.SKILL_EFFECT.valueOf((String) aSkillEffect.get(0)),
                                                    ((Long)aSkillEffect.get(1)).intValue()));
                }
                
                if(points != null)
                    this._skills.put(name, new Skill(null, name, apCost, skillTarget, skillEffects, points));
                else 
                    this._skills.put(name, new Skill(null, name, apCost, skillTarget, skillEffects));
                
            }
        } catch (IOException ex) {
            Logger.getLogger(AEntity.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(AEntity.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Skill getSkill(String name) {
        return this._skills.containsKey(name) ? this._skills.get(name) : null;
    }
    
}
