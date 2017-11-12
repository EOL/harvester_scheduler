package org.bibalex.eol.scheduler.resource;

import java.util.Comparator;

/**
 * Created by sara.mustafa on 5/28/17.
 */
public class ResourcePositionComparator implements Comparator<Resource>{

    @Override
    public int compare(Resource o1, Resource o2) {
        return (o1.getPosition() - o2.getPosition());
    }
}
