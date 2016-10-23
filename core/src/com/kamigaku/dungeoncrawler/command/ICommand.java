package com.kamigaku.dungeoncrawler.command;

import com.kamigaku.dungeoncrawler.entity.IEntity;

public interface ICommand {
    
    void execute();
    void reverse();
    void simulate();
    void setPrevious(ICommand command);
    void setNext(ICommand command);
    ICommand getPrevious();
    ICommand getNext();
    int getApCost();
    IEntity getCaller();
    
}
