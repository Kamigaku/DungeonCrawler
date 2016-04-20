package com.kamigaku.dungeoncrawler.comparator;

import com.kamigaku.dungeoncrawler.entity.IEntity;
import java.util.Comparator;

public class RenderingComparator implements Comparator<IEntity>{

    @Override
    public int compare(IEntity t, IEntity t1) {
        return -(t.getGraphicsComponent().getDepthAxis() - t1.getGraphicsComponent().getDepthAxis());
    }

}
