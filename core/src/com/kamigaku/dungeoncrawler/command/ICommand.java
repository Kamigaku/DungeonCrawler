package com.kamigaku.dungeoncrawler.command;

import com.kamigaku.dungeoncrawler.entity.IEntity;

public interface ICommand {
    
    void execute(IEntity entity);
    
}
