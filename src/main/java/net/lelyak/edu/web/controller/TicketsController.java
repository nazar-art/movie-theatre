package net.lelyak.edu.web.controller;

import net.lelyak.edu.entity.Event;
import net.lelyak.edu.entity.Ticket;
import net.lelyak.edu.entity.User;
import net.lelyak.edu.entity.UserAccount;
import net.lelyak.edu.repository.EventRepository;
import net.lelyak.edu.service.TicketService;
import net.lelyak.edu.service.UserAccountService;
import net.lelyak.edu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("tickets")
public class TicketsController {

    @Autowired
    private UserService userService;
    @Autowired
    private TicketService ticketService;
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private UserAccountService userAccountService;


    @RequestMapping(value = "/book/{id}", method = RequestMethod.POST)
    public String bookPlaceForEvent(@PathVariable("id") long id,
                                    @RequestParam("targetSeats") List<String> chosenSeatsStrings, Model model, Principal principal) {

        User customer = userService.getByEmail(principal.getName().trim());
        Event targetEvent = eventRepository.getById(id);

		/*Long[] chosenSeats = new Long[chosenSeatsStrings.size()];
		for (int i = 0; i < chosenSeatsStrings.size(); i++) {
			chosenSeats[i] = Long.parseLong(chosenSeatsStrings.get(i));
		}*/

        Ticket ticket = new Ticket();
        ticket.setEvent(targetEvent);
        ticket.setEvent_id(targetEvent.getId());
        ticket.setUser(customer);
        ticket.setUser_id(customer.getId());
        ticket.setName(chosenSeatsStrings.toString());

        boolean isBooked = ticketService.bookTicket(ticket);
        if (isBooked) {
            return "redirect:/tickets/my";
        }
        model.addAttribute("message", "Not enough money.. Please refill you account..");
        return "message";
    }

    // todo fix both below methods:
    @RequestMapping("/my")
    public String openMyTicketsPage(Model model, Principal principal) {
        User user = userService.getByEmail(principal.getName().trim());
        List<Ticket> ticketsForUser = ticketService.getTicketsForUser(user);
        model.addAttribute("tickets", ticketsForUser);
        UserAccount accountOfUser = userAccountService.getForUser(user);
        model.addAttribute("balance", accountOfUser.getBalance());
        return "tickets/userTickets";
    }

    @RequestMapping(path = "/my/get", produces = {"application/pdf"})
    public ModelAndView getTicketsAsPdfFile() {
        User user = userService.getAll().get(0);
        List<Ticket> ticketsForUser = ticketService.getTicketsForUser(user);
        return new ModelAndView("ticketsPdfView", "tickets", ticketsForUser);
    }

}
