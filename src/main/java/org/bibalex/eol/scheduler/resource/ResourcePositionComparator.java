package org.bibalex.eol.scheduler.resource;

import java.util.Comparator;

public class ResourcePositionComparator implements Comparator<Resource>{

    @Override
    public int compare(Resource o1, Resource o2) {
        return (o1.getPosition() - o2.getPosition());
    }
}
