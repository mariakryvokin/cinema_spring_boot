package app.controllers;

import app.models.User;
import app.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
public class UserRestController {

    @Autowired
    private UserService userService;

    @GetMapping("/all")
    public List<User> getPageOfUser(@RequestParam("page") int page, @RequestParam("size") int size){
        Page<User> users = userService.getPageOfAll(page,size);
        List<User> resultUserList = users.getContent().stream().map(u -> User.builder().id(u.getId())
                .email(u.getEmail())
                .lastName(u.getLastName())
                .birthday(u.getBirthday())
                .firstName(u.getFirstName())
                .roles(u.getRoles()).tickets(u.getTickets()).build()).collect(Collectors.toList());
        return resultUserList;
    }

}
