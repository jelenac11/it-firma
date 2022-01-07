package tim13.webshop.shop.schedule;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import tim13.webshop.shop.model.User;
import tim13.webshop.shop.repositories.IUserRepository;

@Controller
public class ScheduleController {

	@Autowired
	private IUserRepository userRepository;

    @Async
    @Scheduled(cron = "0 0 3 */5 * ?")
    public void deactivateInactiveUsers() {
        List<User> inactiveUsers = userRepository.findByLastSignInDateLessThan(LocalDateTime.now().minusDays(90));
        if (!inactiveUsers.isEmpty()) {
            inactiveUsers.forEach(user -> {
                user.setEnabled(false);
            });
            userRepository.saveAll(inactiveUsers);
        }
    }
}
