package com.kamigaku.dungeoncrawler.skills;

import com.kamigaku.dungeoncrawler.entity.IEntity;
import com.kamigaku.dungeoncrawler.skills.Skill.SKILL_SHAPE;
import java.awt.Point;
import java.util.ArrayList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public abstract class SkillBuilder {
    
    /*
    Actuellement : 
        - name : le nom du skill
        - apCost : le coût en AP
        - (optionnelle) shape : la forme du skill (voir l'enum dans Skill)
            > de pair avec range : le range du skill
        - (optionnelle) points : les points ou le sort va toucher (par rapport à joueur)
    
    A ajouter :
        - target (boolean) : à ajouter dans le test de validité de FightManager (if target && target présente == OK)
            > == true : doit cibler un joueur
            > == false : peut s'effectuer dans le vent
        - les effets (slow, givre, etc...)
        - type de sort : positif ou négatif
            > exemple si effet slow et positif, retire l'effet de slow
            > ou si damage et postif : heal
        - valeur de dégat
    
    */
    
    public static Skill createSkill(JSONObject skillJson, IEntity caster) {
        String name = (String) skillJson.get("name");
        int apCost = ((Long)skillJson.get("apCost")).intValue();
        ArrayList<Point> points = null;
        
        if(skillJson.containsKey("shape")) {
            SKILL_SHAPE shape = SKILL_SHAPE.valueOf((String) skillJson.get("shape"));
            if(skillJson.containsKey("range")) {
                int range = ((Long)skillJson.get("range")).intValue();        
                switch(shape) {
                    case BACK_LINE:
                        break;
                    case CROSS:
                        break;
                    case LEFT_LINE:
                        break;
                    case RIGHT_LINE:
                        break;
                    case FRONT_LINE:
                        break;
                    case FRONT_AND_BACK_LINE:
                        break;
                    case LEFT_AND_RIGHT_LINE:
                        break;
                    case CIRCLE:
                        break;
                    default:
                        throw new AssertionError(shape.name());
                }
            }
        }
        else if(skillJson.containsKey("points")) {
            JSONArray allPoints = (JSONArray) skillJson.get("points");
            points = new ArrayList<Point>(allPoints.size());
            for(int i = 0; i < allPoints.size(); i++) {
                JSONArray point = (JSONArray)allPoints.get(i);
                points.add(new Point(((Long)point.get(0)).intValue(), 
                                     ((Long)point.get(1)).intValue()));
            }
        }
        else { // cast sur soi-même
            System.out.println("Self cast");
        }
        if(points != null)
            return new Skill(caster, name, apCost, points);
        else 
            return new Skill(caster, name, apCost);
    }
    
}
