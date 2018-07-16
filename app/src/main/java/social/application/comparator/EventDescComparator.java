package social.application.comparator;

import java.util.Comparator;

import social.application.entity.Event;

/**
 * Created by Chappy on 2018.07.17..
 */

public class EventDescComparator implements Comparator<Event> {

    @Override
    public int compare(Event event1, Event event2) {
        return event2.getId().compareTo(event1.getId());
    }
}
