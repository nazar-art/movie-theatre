package net.lelyak.edu.dao.impl;

import net.lelyak.edu.dao.BaseDAO;
import net.lelyak.edu.entity.Event;

import java.util.Arrays;

public class EventDAO extends BaseDAO<Event> {

    public static final String EVENT_TABLE_NAME = "t_event";
    public static final String[] eventFields = {"name", "airDate", "ticketPrice", "rating", "auditorium_id"};

    public EventDAO() {
        super(Event.class, EVENT_TABLE_NAME, Arrays.asList(eventFields));
    }
}

