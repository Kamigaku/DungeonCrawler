package com.kamigaku.dungeoncrawler.component;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.kamigaku.dungeoncrawler.constants.Constants;
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
                System.out.println(amount);
                float oldValue = LevelManager.getLevelManager().getCamera().zoom;
                LevelManager.getLevelManager().getCamera().zoom += ((float)amount / 5f);
                System.out.println(LevelManager.getLevelManager().getCamera().zoom);
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
                    _entity.getPhysicsComponent().setForceY(-Constants.PLAYER_SPEED);
                if(keycode == Keys.S)
                    _entity.getPhysicsComponent().setForceY(Constants.PLAYER_SPEED);
                if(keycode == Keys.Q)
                    _entity.getPhysicsComponent().setForceX(Constants.PLAYER_SPEED);
                if(keycode == Keys.D)
                    _entity.getPhysicsComponent().setForceX(-Constants.PLAYER_SPEED);
                LevelManager.getLevelManager().getCamera().update();
                return false;
            }

            @Override
            public boolean keyTyped(char character) {
                return false;
            }

            @Override
            public boolean keyDown(int keycode) {
                if(keycode == Keys.Z)
                    _entity.getPhysicsComponent().setForceY(Constants.PLAYER_SPEED);
                if(keycode == Keys.S)
                    _entity.getPhysicsComponent().setForceY(-Constants.PLAYER_SPEED);
                if(keycode == Keys.Q)
                    _entity.getPhysicsComponent().setForceX(-Constants.PLAYER_SPEED);
                if(keycode == Keys.D)
                    _entity.getPhysicsComponent().setForceX(Constants.PLAYER_SPEED);
                return false;
            }
            
        });
    }

    public void update() {
    }
    
}
