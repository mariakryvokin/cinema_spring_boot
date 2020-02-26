package app.controllers;

import app.models.Event;
import app.models.User;
import app.models.enums.RoleEnum;
import app.services.TicketService;
import app.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Arrays;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private TicketService ticketService;
    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/main")
    public String main(Model model, Principal principal) {
        model.addAttribute("id", principal.getName());
        return "/user/main";
    }

    @PostMapping("/save")
    public String saveUser(@ModelAttribute("user") User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userService.save(user, Arrays.asList(RoleEnum.RESGISTERED_USER));
        return "/user/main";
    }

    @PostMapping(value = "/cart", headers = "accept=application/pdf")
    public Model doCart(@ModelAttribute User user, Model model) {
        if (user != null && user.getEmail() != null) {
            User registeredUser = userService.getUserByEmail(user.getEmail());
            if (registeredUser != null) {
                model.addAttribute("ticketsToBeBought", ticketService.getCartTicketsByUserIdAndOrderId(registeredUser.getId(), null));
            }
        }
        return model;
    }

    @GetMapping("/cart")
    public String cart(Model model, @ModelAttribute User user) {
        model.addAttribute("user", new User());
        model.addAttribute("event", new Event());
        return "cartForm";
    }
}
