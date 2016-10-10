/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kamigaku.dungeoncrawler.hud;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.kamigaku.dungeoncrawler.singleton.FightManager;

/**
 *
 * @author Kamigaku
 */
public class AttackButton extends TextButton {
    
    public AttackButton(String text, Skin skin) {
        super(text, skin);
        this.addListener(new ClickListener() {
            
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //FightManager.getFightManager().entityAttackingPick = true;
            }
            
        });
    }
    
    
    
}
