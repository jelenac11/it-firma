package tim13.webshop.shop.services;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import tim13.webshop.shop.dto.PlanForPSPDTO;
import tim13.webshop.shop.dto.ProductDTO;
import tim13.webshop.shop.exceptions.BaseException;
import tim13.webshop.shop.dto.PlanDTO;
import tim13.webshop.shop.mapper.PlanMapper;
import tim13.webshop.shop.model.Course;
import tim13.webshop.shop.model.Plan;
import tim13.webshop.shop.repositories.ICourseRepository;
import tim13.webshop.shop.repositories.IPlanRepository;

@Service
public class PlanService {

	@Autowired
	private IPlanRepository planRepository;

	@Autowired
	private ICourseRepository courseRepository;

	@Autowired
	private PlanMapper planMapper;

	private static final Logger logger = LoggerFactory.getLogger(PlanService.class);

	public List<PlanDTO> findAll() {
		logger.info("Reading plans from database");

		return planRepository.findAll().stream().map(plan -> planMapper.toDTO(plan)).collect(Collectors.toList());
	}

	public PlanDTO create(PlanDTO planDTO) throws BaseException {
		Plan plan = planMapper.toEntity(planDTO);

		Course course = courseRepository.getOne(plan.getCourse().getId());

		if (course == null) {
			logger.debug(String.format("Course with id %s doesn't exist.", plan.getCourse().getId()));

			throw new BaseException(HttpStatus.NOT_FOUND,
					String.format("Course with id %s doesn't exist.", plan.getCourse().getId()));
		}

		plan.setCourse(course);

		PlanForPSPDTO planForPSPDTO = createPlanDto(plan, course.getMerchant().getEmail());

		logger.info("Sending request for creating plan on paypal");

		String planId = sendRequestToCreatePlanOnPaypal(planForPSPDTO);

		plan.setPaypalPlanId(planId);

		logger.info("Saving plan in database");

		plan = planRepository.save(plan);

		return planMapper.toDTO(plan);
	}

	private String sendRequestToCreatePlanOnPaypal(PlanForPSPDTO planDTO) throws BaseException {
		RestTemplate rs = new RestTemplate();

		try {
			return rs.postForEntity("https://cf8e-79-101-213-141.ngrok.io/api/subscription/create-plan", planDTO, String.class)
					.getBody();
		} catch (HttpStatusCodeException e) {
			logger.debug(e.getResponseBodyAsString());

			throw new BaseException(HttpStatus.resolve(e.getRawStatusCode()), e.getResponseBodyAsString());
		}
	}

	private PlanForPSPDTO createPlanDto(Plan plan, String merchantEmail) {
		PlanForPSPDTO planDTO = new PlanForPSPDTO();

		planDTO.setAmount(plan.getPrice());
		planDTO.setDescription(plan.getDescription());
		planDTO.setMerchantEmail(merchantEmail);
		planDTO.setName(plan.getName());
		planDTO.setProduct(createProductDto(plan.getCourse().getName()));
		planDTO.setTypeOfPlan(plan.getTypeOfPlan());

		return planDTO;
	}

	private ProductDTO createProductDto(String productName) {
		ProductDTO productDTO = new ProductDTO();

		productDTO.setName(productName);
		productDTO.setType("DIGITAL");
		productDTO.setCategory("EDUCATIONAL_AND_TEXTBOOKS");

		return productDTO;
	}
}
