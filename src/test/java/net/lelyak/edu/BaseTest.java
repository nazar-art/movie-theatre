package net.lelyak.edu;

import net.lelyak.edu.aspects.CounterAspect;
import net.lelyak.edu.aspects.DiscountAspect;
import net.lelyak.edu.aspects.LuckyWinnerAspect;
import net.lelyak.edu.repository.AuditoriumRepository;
import net.lelyak.edu.repository.EventRepository;
import net.lelyak.edu.repository.TicketRepository;
import net.lelyak.edu.repository.UserRepository;
import net.lelyak.edu.service.AdminService;
import net.lelyak.edu.service.AuditoriumService;
import net.lelyak.edu.service.DiscountService;
import net.lelyak.edu.service.TicketService;
import net.lelyak.edu.service.UserService;
import net.lelyak.edu.tests.util.RandomTestObjectsGenerator;
import net.lelyak.edu.utils.TestNGListener;
import net.lelyak.edu.utils.datafactory.GeneratorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;

@Listeners({TestNGListener.class,
    org.uncommons.reportng.HTMLReporter.class,
    org.uncommons.reportng.JUnitXMLReporter.class})
@ContextConfiguration(locations = "file:**/spring-context.xml")
public abstract class BaseTest extends AbstractTestNGSpringContextTests {

    @Autowired
    protected AdminService adminService;
    @Autowired
    protected AuditoriumService auditoriumService;
    @Autowired
    protected DiscountService discountService;
    @Autowired
    protected UserService userService;
    @Autowired
    protected TicketService ticketService;

    @Autowired
    protected CounterAspect counterAspect;
    @Autowired
    protected DiscountAspect discountAspect;
    @Autowired
    protected LuckyWinnerAspect luckyWinnerAspect;

    @Autowired
    protected AuditoriumRepository auditoriumRepository;
    @Autowired
    protected EventRepository eventRepository;
    @Autowired
    protected TicketRepository ticketRepository;
    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected RandomTestObjectsGenerator randomTestGenerator;

    protected GeneratorRepository testGenerator = new GeneratorRepository();

    @BeforeMethod
    public void setUp() {
        clearAspectCounters();
    }

    @AfterMethod
    public void tearDown() {
        clearAspectCounters();
    }

    private void clearAspectCounters() {
        counterAspect.getCounterEventGetByName().clear();
        counterAspect.getCounterEventGetTicketPrice().clear();
        counterAspect.getCounterEventTicketBooked().clear();
        discountAspect.getDiscountCounter().clear();
    }
}
