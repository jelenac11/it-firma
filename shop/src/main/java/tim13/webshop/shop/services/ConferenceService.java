package tim13.webshop.shop.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import tim13.webshop.shop.dto.ConferenceDTO;
import tim13.webshop.shop.enums.ConferenceStatus;
import tim13.webshop.shop.model.Conference;
import tim13.webshop.shop.model.OrderItem;
import tim13.webshop.shop.repositories.IConferenceRepository;
import tim13.webshop.shop.repositories.IOrderItemRepository;

@Service
@EnableScheduling
public class ConferenceService {

	@Autowired
	private IConferenceRepository conferenceRepository;

	@Autowired
	private IOrderItemRepository orderItemRepository;

	@Autowired
	private WageService wageService;

	private static final Logger logger = LoggerFactory.getLogger(ConferenceService.class);

	public List<ConferenceDTO> findAll() {
		List<ConferenceDTO> forReturn = new ArrayList<ConferenceDTO>();
		List<Conference> conferences = conferenceRepository
				.findByStatusAndStartDateGreaterThan(ConferenceStatus.NOT_FINISHED, Long.valueOf(new Date().getTime()));
		for (Conference conf : conferences) {
			forReturn.add(new ConferenceDTO(conf));
		}
		logger.info("Reading conferences from database");
		return forReturn;
	}

	// @Scheduled(cron = "1 0 0 0 0 ?")
	@Scheduled(fixedRate = 60000)
	private void checkIsAnyConferenceFinished() {
		logger.trace("Cron job for checking conferences started.");

		logger.trace("Reading conferences from DB.");

		List<Conference> conferences = conferenceRepository
				.findByStatusAndEndDateLessThan(ConferenceStatus.NOT_FINISHED, Long.valueOf(new Date().getTime()));

		conferences.forEach(conference -> manipulateConference(conference));
	}

	private void manipulateConference(Conference conference) {
		logger.trace(String.format("Updating conference %s.", conference.getId()));

		changeStatusAndSaveConference(conference, ConferenceStatus.FINISHED);

		logger.trace("Reading order items from DB.");
		List<OrderItem> items = orderItemRepository.findByProductId(conference.getId());

		for (OrderItem item : items) {
			wageService.manipulateWage(item.getOrder().getTransaction().getFrom(), conference);
		}
	}

	private void changeStatusAndSaveConference(Conference conference, ConferenceStatus status) {
		conference.setStatus(status);

		conferenceRepository.save(conference);
	}
}
