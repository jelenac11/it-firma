package tim13.webshop.shop.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tim13.webshop.shop.dto.ConferenceDTO;
import tim13.webshop.shop.model.Conference;
import tim13.webshop.shop.repositories.IConferenceRepository;

@Service
public class ConferenceService {

	@Autowired
	private IConferenceRepository conferenceRepository;

	public List<ConferenceDTO> findAll() {
		List<ConferenceDTO> forReturn = new ArrayList<ConferenceDTO>();
		List<Conference> conferences = conferenceRepository.findAll();
		for (Conference conf : conferences) {
			forReturn.add(new ConferenceDTO(conf));
		}
		return forReturn;
	}

}
