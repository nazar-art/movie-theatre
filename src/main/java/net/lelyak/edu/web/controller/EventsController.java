package net.lelyak.edu.web.controller;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import net.lelyak.edu.entity.Auditorium;
import net.lelyak.edu.entity.Event;
import net.lelyak.edu.entity.enums.Rating;
import net.lelyak.edu.repository.AuditoriumRepository;
import net.lelyak.edu.repository.EventRepository;
import net.lelyak.edu.utils.LocalDateConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("events")
public class EventsController {

    @Autowired
    private AuditoriumRepository auditoriumRepository;
    @Autowired
    private EventRepository eventRepository;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public String openAllEvents(Model model) {
        model.addAttribute("events", eventRepository.getAll());
        return "events/allEvents";
    }

    @RequestMapping(value = "/loadFromFile", method = RequestMethod.POST)
    public String loadEventsFromXml(@RequestParam("fileWithEvents") MultipartFile file) throws IOException {
        XStream xStream = new XStream(new StaxDriver());
        @SuppressWarnings("unchecked")
        List<Event> events = (List<Event>) xStream.fromXML(file.getInputStream());
        for (Event event : events) {
            eventRepository.put(event);
        }
        return "redirect:/events/all";
    }

    @RequestMapping(value = "/all/download", method = RequestMethod.GET)
    public void getEventsInXml(HttpServletResponse response) throws IOException {
        List<Event> all = (List<Event>) eventRepository.getAll();
        XStream xStream = new XStream(new StaxDriver());
        response.setHeader("content-type", "application/xml");
        String xml = xStream.toXML(all);
        response.getWriter().write(xml);
    }

    @RequestMapping(path = "/add", method = RequestMethod.POST)
    public String addEvent(@RequestParam Map<String, String> allRequestParams) {
        Event newEvent = new Event();
        // get auditorium id from request
        String auditoryIdString = allRequestParams.get("auditorium");
        Long auditoryId = Long.parseLong(auditoryIdString);
        Auditorium auditorium = auditoriumRepository.getById(auditoryId);
        newEvent.setAuditorium(auditorium);
        // get name
        String name = allRequestParams.get("name");
        newEvent.setName(name);
        // get base price
        String basePriceString = allRequestParams.get("basePrice");
        double basePrice = Double.valueOf(basePriceString);
        newEvent.setTicketPrice(basePrice);

        // get event date
        String start = allRequestParams.get("start");
        LocalDate startDate = LocalDateConverter.convert(start);
        newEvent.setAirDate(Date.valueOf(startDate));

        // event rating
        String ratingString = allRequestParams.get("rating");
        Rating rating = Rating.valueOf(ratingString);
        newEvent.setEnumRating(rating);

        long eventId = eventRepository.put(newEvent);
        return "redirect:/event/" + eventId;
    }

    @RequestMapping("book/{eventId}")
    public String openBookTicketForEventPage(@PathVariable("eventId") Long eventId, Model model) {
        Event event = eventRepository.getById(eventId);
        model.addAttribute("event", event);
        return "events/book";
    }

    @RequestMapping("manage")
    public ModelAndView openManageEvents() {
        ModelAndView openManageEventsModelAndView = new ModelAndView("events/manage");
        List<Event> allEvents = (List<Event>) eventRepository.getAll();
        openManageEventsModelAndView.addObject("allEvents", allEvents);
        return openManageEventsModelAndView;
    }

    @RequestMapping("manage/add")
    public ModelAndView openAddEventPage() {
        ModelAndView openAddEventPageModelAndView = new ModelAndView("events/addEvent");
        openAddEventPageModelAndView.addObject("ratingOptions", Rating.values());
        openAddEventPageModelAndView.addObject("auditoriums", auditoriumRepository.getAll());
        return openAddEventPageModelAndView;
    }
}
