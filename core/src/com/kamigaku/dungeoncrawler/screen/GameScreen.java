/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.kamigaku.dungeoncrawler.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.kamigaku.dungeoncrawler.constants.Constants;
import com.kamigaku.dungeoncrawler.level.ILevel;
import com.kamigaku.dungeoncrawler.singleton.LevelManager;
import com.kamigaku.dungeoncrawler.singleton.ScreenManager;

public class GameScreen implements Screen {
	
    public GameScreen(ILevel level) {
        LevelManager.getLevelManager().setLevel(level);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 1, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        ScreenManager.game.batch.begin();
        LevelManager.getLevelManager().render(ScreenManager.game.batch);
        ScreenManager.game.batch.end();
    }

    @Override
    public void resize(int width, int height) {
        LevelManager.getLevelManager().getLevel().getCamera().setToOrtho(false, 
                Constants.VIRTUAL_HEIGHT * width / (float)height, 
                Constants.VIRTUAL_HEIGHT);
        ScreenManager.game.batch.setProjectionMatrix(
                LevelManager.getLevelManager().getLevel().getCamera().combined);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        LevelManager.getLevelManager().dispose();
    }

}
