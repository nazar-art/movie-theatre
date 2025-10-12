package net.lelyak.edu.web.controller;

import com.google.common.collect.Lists;
import net.lelyak.edu.entity.User;
import net.lelyak.edu.entity.UserAccount;
import net.lelyak.edu.entity.enums.UserRole;
import net.lelyak.edu.service.UserAccountService;
import net.lelyak.edu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.PostConstruct;

@Controller
public class AuthController {

    @Autowired
    private UserAccountService userAccountService;
    @Autowired
    private UserService userService;
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init() {
        passwordEncoder = new BCryptPasswordEncoder();
    }

    @RequestMapping("login")
    public String loginPage() {
        return "login";
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String openRegisterPage(Model model) {
        model.addAttribute("newUser", new User());
        model.addAttribute("availableRoles", Lists.newArrayList(UserRole.BOOKING_MANAGER));
        return "signUp";
    }

    @RequestMapping(value = "logout")
    public String logout() {
        SecurityContextHolder.clearContext();
        return "/login";
    }

    @RequestMapping(path = "/register", method = RequestMethod.POST)
    public String createUser(@ModelAttribute("newUser") User user) {
        if (userService.isUserExist(user)) {
            return "redirect:/register?userExist";
        }

        if (user.getRole() == null) {
            user.setRole(UserRole.REGISTERED_USER.toString());
        } else {
            user.setRole(user.getRole());
        }
        user.setEnabled(true);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        long newUserId = userService.register(user);
        UserAccount userAccount = new UserAccount();
        userAccount.setBalance(2000D);
        userAccount.setUser_id(newUserId);
        userAccountService.register(userAccount);
        return "redirect:/";
    }

    @RequestMapping("/denied")
    public String accessDeniedPage() {
        return "denied";
    }
}
