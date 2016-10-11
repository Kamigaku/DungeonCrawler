package com.kamigaku.dungeoncrawler.skills;

import com.kamigaku.dungeoncrawler.entity.IEntity;
import static com.kamigaku.dungeoncrawler.skills.ISkill.*;
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
        - skillTarget : enum, voir ISkill
    
    A ajouter :
        - les effets (slow, givre, etc...)
        - type de sort : positif ou négatif
            > exemple si effet slow et positif, retire l'effet de slow
            > ou si damage et postif : heal
        - valeur des dégats
    
    */
    
    public static Skill createSkill(JSONObject skillJson, IEntity caster) {
        String name = (String) skillJson.get("name");
        int apCost = ((Long)skillJson.get("apCost")).intValue();
        SKILL_TARGET skillTarget = SKILL_TARGET.valueOf((String) skillJson.get("skillTarget"));;
        ArrayList<Point> points = null;
        
        if(skillJson.containsKey("shape")) {
            SKILL_SHAPE shape = SKILL_SHAPE.valueOf((String) skillJson.get("shape"));
            if(skillJson.containsKey("range")) {
                points = new ArrayList<Point>();
                int range = ((Long)skillJson.get("range")).intValue(); 
                int x = 1;
                int y = 1;
                if(skillTarget == SKILL_TARGET.ME || skillTarget == SKILL_TARGET.EVERYONE) {
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
                                if(skillTarget == SKILL_TARGET.ME || skillTarget == SKILL_TARGET.EVERYONE)
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
            for(int i = 0; i < allPoints.size(); i++) {
                JSONArray point = (JSONArray)allPoints.get(i);
                points.add(new Point(((Long)point.get(0)).intValue(), 
                                     ((Long)point.get(1)).intValue()));
            }
        }
        else { // cast sur soi-même
        }
        if(points != null)
            return new Skill(caster, name, apCost, skillTarget, points);
        else 
            return new Skill(caster, name, apCost, skillTarget);
    }
    
}
