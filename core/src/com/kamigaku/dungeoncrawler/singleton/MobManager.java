/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kamigaku.dungeoncrawler.singleton;

import com.badlogic.gdx.Gdx;
import com.kamigaku.dungeoncrawler.entity.AEntity;
import com.kamigaku.dungeoncrawler.entity.implementation.Mob;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Kamigaku
 */
public class MobManager {
    
    private static MobManager _mobManager;
    private HashMap<String, Mob> _mobDefinition;
    
    public static MobManager getMobManager() {
        if(_mobManager == null)
            _mobManager = new MobManager();
        return _mobManager;
    }
    
    private MobManager() {
        this._mobDefinition = new HashMap<String, Mob>();
        JSONParser jParser = new JSONParser();
        try {
            JSONArray array = (JSONArray) jParser.parse(Gdx.files.internal("mobs/mobs.json").reader());
            System.out.println("[MobManager][constructor] Loading " + array.size() + " mobs.");
            for(int i = 0; i < array.size(); i++) {
                JSONObject mobJson = (JSONObject) array.get(i);
                
                // Name
                String name = (String) mobJson.get("name");
                
                // Sprite
                String spritePath = "sprites/default.png";
                if(mobJson.containsKey("sprite"))
                    spritePath = (String) mobJson.get("sprite");
                
                // Level
                int level = 1;
                if(mobJson.containsKey("level"))
                    level = ((Long)mobJson.get("level")).intValue();
                
                // Statistic
                JSONObject statisticJson = (JSONObject) mobJson.get("statistic");
                
                // Skills
                JSONArray skillsJson = (JSONArray) mobJson.get("skills");
                
                // Experience given
                int expGiven = ((Long)mobJson.get("expGiven")).intValue();
                
                this._mobDefinition.put(name, new Mob(name, spritePath, skillsJson, statisticJson, level, expGiven));
            }
        } catch (IOException ex) {
            Logger.getLogger(AEntity.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(AEntity.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Mob getMob(String name) {
        return this._mobDefinition.containsKey(name) ? this._mobDefinition.get(name) : null;
    }
    
}
