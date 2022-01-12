package tim13.webshop.shop.services;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import tim13.webshop.shop.dto.WageDTO;
import tim13.webshop.shop.enums.WageStatus;
import tim13.webshop.shop.exceptions.NotLoggedInException;
import tim13.webshop.shop.mapper.WageMapper;
import tim13.webshop.shop.model.Conference;
import tim13.webshop.shop.model.User;
import tim13.webshop.shop.model.Wage;
import tim13.webshop.shop.repositories.IWageRepository;

@Service
public class WageService {

	@Autowired
	private IWageRepository wageRepository;

	@Autowired
	private WageMapper wageMapper;

	private static final Logger logger = LoggerFactory.getLogger(WageService.class);

	public Wage getOne(Long id) {
		return wageRepository.getOne(id);
	}

	public List<WageDTO> getByUser() throws NotLoggedInException {
		User current = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (current == null) {
			throw new NotLoggedInException("You must login first. No logged in user found!");
		}

		logger.trace("Reading wages from DB.");

		return wageRepository.findByOwnerAndStatus(current, WageStatus.NOT_PAID).stream()
				.map(wage -> wageMapper.toDTO(wage)).collect(Collectors.toList());
	}

	public void manipulateWage(User buyer, Conference conference) {
		logger.trace("Creating wage.");
		
		double conferenceDurationInHours = (conference.getEndDate() - conference.getStartDate()) / (1000 * 60 * 60);

		Wage wage = new Wage();

		wage.setConference(conference.getName());
		wage.setDuration(conferenceDurationInHours);
		wage.setEndDay(conference.getEndDate());
		wage.setStartDay(conference.getStartDate());
		wage.setStatus(WageStatus.NOT_PAID);
		wage.setOwner(buyer);

		logger.trace("Saving wage in DB.");

		wageRepository.save(wage);
	}

	public void updateStatusById(Long id, WageStatus status) {
		logger.trace(String.format("Updating wage %s.", id));

		Wage wage = wageRepository.getOne(id);

		wage.setStatus(status);

		wageRepository.save(wage);
	}

}
