package tim13.webshop.shop.services;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tim13.webshop.shop.dto.ConferenceDTO;
import tim13.webshop.shop.model.Conference;
import tim13.webshop.shop.repositories.IConferenceRepository;

@Service
public class ConferenceService {

	@Autowired
	private IConferenceRepository conferenceRepository;

	private static final Logger logger = LoggerFactory.getLogger(ConferenceService.class);

	public List<ConferenceDTO> findAll() {
		List<ConferenceDTO> forReturn = new ArrayList<ConferenceDTO>();
		List<Conference> conferences = conferenceRepository.findAll();
		for (Conference conf : conferences) {
			forReturn.add(new ConferenceDTO(conf));
		}
		logger.info("Reading conferences from database");
		return forReturn;
	}

}
