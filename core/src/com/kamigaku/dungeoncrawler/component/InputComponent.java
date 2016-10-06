package com.kamigaku.dungeoncrawler.component;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.kamigaku.dungeoncrawler.constants.Constants;
import com.kamigaku.dungeoncrawler.entity.AEntity;
import com.kamigaku.dungeoncrawler.singleton.LevelManager;

public class InputComponent {
    
    private final AEntity _entity;
    public boolean updateInput = true;
    private final InputProcessor _ip;
    
    public InputComponent(AEntity entity) {
        this._entity = entity;
        this._ip = new InputProcessor() {
			
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
                float oldValue = LevelManager.getLevelManager().getCamera().zoom;
                LevelManager.getLevelManager().getCamera().zoom += ((float)amount / 5f);
                if(LevelManager.getLevelManager().getCamera().zoom < 0.2)
                    LevelManager.getLevelManager().getCamera().zoom = oldValue;
                return false;
            }

            @Override
            public boolean mouseMoved(int screenX, int screenY) {
                return false;
            }

            @Override
            public boolean keyUp(int keycode) {
                if(keycode == Keys.Z)
                    _entity.getPhysicsComponent().addForceY(-Constants.PLAYER_SPEED);
                if(keycode == Keys.S)
                    _entity.getPhysicsComponent().addForceY(Constants.PLAYER_SPEED);
                if(keycode == Keys.Q)
                    _entity.getPhysicsComponent().addForceX(Constants.PLAYER_SPEED);
                if(keycode == Keys.D)
                    _entity.getPhysicsComponent().addForceX(-Constants.PLAYER_SPEED);
                return false;
            }

            @Override
            public boolean keyTyped(char character) {
                return false;
            }

            @Override
            public boolean keyDown(int keycode) {
                if(keycode == Keys.Z)
                    _entity.getPhysicsComponent().addForceY(Constants.PLAYER_SPEED);
                if(keycode == Keys.S)
                    _entity.getPhysicsComponent().addForceY(-Constants.PLAYER_SPEED);
                if(keycode == Keys.Q)
                    _entity.getPhysicsComponent().addForceX(-Constants.PLAYER_SPEED);
                if(keycode == Keys.D)
                    _entity.getPhysicsComponent().addForceX(Constants.PLAYER_SPEED);
                return false;
            }
            
        };
        
        LevelManager.getLevelManager().getLevel().addInputProcessor(this._ip, true);
    }

    public InputProcessor getInputProcessor() {
        return _ip;
    }
    
}
