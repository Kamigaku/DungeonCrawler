/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kamigaku.libgdx.extension;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import java.util.LinkedHashMap;

/**
 *
 * @author Kamigaku
 */
public class InputMultiplexerToggle implements InputProcessor {

    private LinkedHashMap<InputProcessor, Boolean> _processors = new LinkedHashMap<InputProcessor, Boolean>(4);

    public InputMultiplexerToggle () {
        Gdx.input.setInputProcessor(this);
    }

    /**
     * Include all the processors set to activated by default
     * @param processors The processors
     */
    public InputMultiplexerToggle (InputProcessor... processors) {
        for (int i = 0; i < processors.length; i++) 
            this._processors.put(processors[i], true);
        Gdx.input.setInputProcessor(this);
    }

    public void addProcessor (boolean activated, InputProcessor processor) {
        if (processor == null) throw new NullPointerException("processor cannot be null");
        this._processors.put(processor, activated);
    }
    
    public void setProcessor(boolean activated, InputProcessor processor) {
        this._processors.replace(processor, activated);
    }

    public void removeProcessor (InputProcessor processor) {
        this._processors.remove(processor);
    }

    /** @return the number of processors in this multiplexer */
    public int size () {
        return this._processors.size();
    }

    public void clear () {
        this._processors.clear();
    }

    public void setProcessors (LinkedHashMap<InputProcessor, Boolean> processors) {
        this._processors = processors;
    }

    public LinkedHashMap<InputProcessor, Boolean> getProcessors () {
            return this._processors;
    }

    @Override
    public boolean keyDown (int keycode) {
        for(InputProcessor ip : _processors.keySet())
            if(_processors.get(ip) && ip.keyDown(keycode)) return true;
        return false;
    }

    @Override
    public boolean keyUp (int keycode) {
        for(InputProcessor ip : _processors.keySet())
            if(_processors.get(ip) && ip.keyUp(keycode)) return true;
        return false;
    }

    @Override
    public boolean keyTyped (char character) {
        for(InputProcessor ip : _processors.keySet())
            if(_processors.get(ip) && ip.keyTyped(character)) return true;
        return false;
    }

    @Override
    public boolean touchDown (int screenX, int screenY, int pointer, int button) {
        for(InputProcessor ip : _processors.keySet())
            if(_processors.get(ip) && ip.touchDown(screenX, screenY, pointer, button)) return true;
        return false;
    }

    @Override
    public boolean touchUp (int screenX, int screenY, int pointer, int button) {
        for(InputProcessor ip : _processors.keySet())
            if(_processors.get(ip) && ip.touchUp(screenX, screenY, pointer, button)) return true;
        return false;
    }

    @Override
    public boolean touchDragged (int screenX, int screenY, int pointer) {
        for(InputProcessor ip : _processors.keySet())
            if(_processors.get(ip) && ip.touchDragged(screenX, screenY, pointer)) return true;
        return false;
    }

    @Override
    public boolean mouseMoved (int screenX, int screenY) {
        for(InputProcessor ip : _processors.keySet())
            if(_processors.get(ip) && ip.mouseMoved(screenX, screenY)) return true;
        return false;
    }

    @Override
    public boolean scrolled (int amount) {
        for(InputProcessor ip : _processors.keySet())
            if(_processors.get(ip) && ip.scrolled(amount)) return true;
        return false;
    }
    
}
