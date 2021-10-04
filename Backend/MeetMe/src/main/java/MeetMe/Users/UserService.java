package MeetMe.Users;
import coms309.MeetMe.Users.UserRepository;
import coms309.MeetMe.Users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

public class UserService {
    @Autowired
    UserRepository userRepository;

    public User validateUser(User user){
        return userRepository.findByEmailAndPassword(user.getName(), user.getPassword());
    }
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }
    public Optional<User> getUserById(long id){
        return userRepository.findById(id);
    }

}
