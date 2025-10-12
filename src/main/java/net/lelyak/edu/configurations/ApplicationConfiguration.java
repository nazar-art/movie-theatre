package net.lelyak.edu.configurations;

import net.lelyak.edu.dao.impl.AuditoriumDAO;
import net.lelyak.edu.dao.impl.EventDAO;
import net.lelyak.edu.dao.impl.TicketDAO;
import net.lelyak.edu.dao.impl.UserAccountDAO;
import net.lelyak.edu.dao.impl.UserDAO;
import net.lelyak.edu.repository.AuditoriumRepository;
import net.lelyak.edu.repository.EventRepository;
import net.lelyak.edu.repository.TicketRepository;
import net.lelyak.edu.repository.UserAccountRepository;
import net.lelyak.edu.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan({"net.lelyak.edu.repository", "net.lelyak.edu.service"})
@Import({DatabaseDAOConfiguration.class})
public class ApplicationConfiguration {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private EventDAO eventDAO;

    @Autowired
    private TicketDAO ticketDAO;

    @Autowired
    private AuditoriumDAO auditoriumDAO;

    @Autowired
    private UserAccountDAO userAccountDAO;

    @Bean
    public AuditoriumRepository auditoriumRepository() {
        AuditoriumRepository auditoriumRepository = new AuditoriumRepository();
        auditoriumRepository.setDao(auditoriumDAO);
        return auditoriumRepository;
    }

    @Bean
    public EventRepository eventRepository() {
        EventRepository eventRepository = new EventRepository();
        eventRepository.setDao(eventDAO);
        return eventRepository;
    }

    @Bean
    public UserRepository userRepository() {
        UserRepository userRepository = new UserRepository();
        userRepository.setDao(userDAO);
        return userRepository;
    }

    @Bean
    public TicketRepository ticketRepository() {
        TicketRepository ticketRepository = new TicketRepository();
        ticketRepository.setDao(ticketDAO);
        return ticketRepository;
    }

    @Bean
    public UserAccountRepository userAccountRepository() {
        UserAccountRepository userAccountRepository = new UserAccountRepository();
        userAccountRepository.setDao(userAccountDAO);
        return userAccountRepository;
    }

	/*@Bean
	public Application application() {
		return new Application();
	}*/
}
