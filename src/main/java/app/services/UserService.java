package app.services;

import app.models.Role;
import app.models.User;
import app.models.enums.RoleEnum;
import app.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User save(User user, List<RoleEnum> roles) {
        user.setRoles(roles.stream().map(r->new Role(r.name())).collect(Collectors.toList()));
        return userRepository.save(user);
    }

    public List<User> saveAll(Iterable<User> users) {
        return userRepository.saveAll(users);
    }

    public boolean exists(User user){
        return userRepository.exists(Example.of(user));
    }

    public int countBookedTickets(long userId){
        return userRepository.countBookedTickets(userId);
    }

    public void remove(Long userId){
        userRepository.deleteById(userId);
    }

    public User getById(Long userId){
        return userRepository.getOne(userId);
    }

    public User getUserByEmail(String email){
        return userRepository.getUserByEmail(email);
    }

    public List<User> getAll(){
        return userRepository.findAll();
    }
}
