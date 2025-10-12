package net.lelyak.edu.web.controller;

import net.lelyak.edu.entity.Event;
import net.lelyak.edu.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("event")
public class EventController {

    @Autowired
    private EventRepository eventRepository;

    @RequestMapping("/{eventId}")
    public ModelAndView openEventPage(@PathVariable("eventId") Long eventId) {
        ModelAndView openEventPageModelAndView = new ModelAndView("events/event");
        Event eventById = eventRepository.getById(eventId);
        openEventPageModelAndView.addObject("event", eventById);
        return openEventPageModelAndView;
    }
}
