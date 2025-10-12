package net.lelyak.edu.web.controller;

import net.lelyak.edu.entity.Event;
import net.lelyak.edu.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.List;

@Controller
public class MainPageController {

    @Autowired
    private EventRepository eventRepository;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView index() {
        ModelAndView indexModelAndView = new ModelAndView("index");
        List<Event> all = (List<Event>) eventRepository.getAll();
        indexModelAndView.addObject("events", all);
        return indexModelAndView;
    }

    @RequestMapping(value = "/witherror", method = {RequestMethod.GET})
    public void openPageWithException() throws IOException {
        throw new IOException("some Error occurred in this controller");
    }

}
