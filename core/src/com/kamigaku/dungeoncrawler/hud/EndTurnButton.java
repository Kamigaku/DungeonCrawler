package com.kamigaku.dungeoncrawler.hud;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.kamigaku.dungeoncrawler.singleton.FightManager;

public class EndTurnButton extends TextButton {

    public EndTurnButton(String text, Skin skin) {
        super(text, skin);
        this.addListener(new ClickListener() {
            
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(FightManager.getFightManager().getFightStatus() == FightManager.FightStatus.PICKING)
                    FightManager.getFightManager().setFightStatus(FightManager.FightStatus.END_PICKING);
            }
            
        });
    }
    
}
