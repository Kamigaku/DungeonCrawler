package com.kamigaku.dungeoncrawler.component;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.kamigaku.dungeoncrawler.entity.AEntity;
import com.kamigaku.dungeoncrawler.singleton.LevelManager;

public class InputComponent {
    
    private final AEntity _entity;
    
    public InputComponent(AEntity entity) {
        this._entity = entity;
        LevelManager.getLevelManager().getLevel().addInputProcessor(new InputProcessor() {
			
            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                return false;
            }

            @Override
            public boolean touchDragged(int screenX, int screenY, int pointer) {
                return false;
            }

            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                return false;
            }

            @Override
            public boolean scrolled(int amount) {
                return false;
            }

            @Override
            public boolean mouseMoved(int screenX, int screenY) {
                return false;
            }

            @Override
            public boolean keyUp(int keycode) {
                if(keycode == Keys.Z)
                    _entity.getPhysicsComponent().setForceY(-50);
                if(keycode == Keys.S)
                    _entity.getPhysicsComponent().setForceY(50);
                if(keycode == Keys.Q)
                    _entity.getPhysicsComponent().setForceX(50);
                if(keycode == Keys.D)
                    _entity.getPhysicsComponent().setForceX(-50);
                return false;
            }

            @Override
            public boolean keyTyped(char character) {
                return false;
            }

            @Override
            public boolean keyDown(int keycode) {
                if(keycode == Keys.Z)
                    _entity.getPhysicsComponent().setForceY(50);
                if(keycode == Keys.S)
                    _entity.getPhysicsComponent().setForceY(-50);
                if(keycode == Keys.Q)
                    _entity.getPhysicsComponent().setForceX(-50);
                if(keycode == Keys.D)
                    _entity.getPhysicsComponent().setForceX(50);
                return false;
            }
            
        });
    }

    public void update() {
    }
    
}
