package coms309.MeetMe;

// import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
// import org.springframework.context.annotation.Bean;

// import coms309.MeetMe.User.User;
// import coms309.MeetMe.User.UserRepository;


@SpringBootApplication
public class Main {

	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}

	// Create demo users
//	@Bean
//	CommandLineRunner initUser(UserRepository userRepository) {
//		return args -> {
//			User user1 = new User("John", "password");
//			userRepository.save(user1);
//		};
//	}
}
