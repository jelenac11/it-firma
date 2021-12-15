package tim13.webshop.shop.services;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tim13.webshop.shop.dto.CourseDTO;
import tim13.webshop.shop.model.Course;
import tim13.webshop.shop.repositories.ICourseRepository;

@Service
public class CourseService {

	@Autowired
	private ICourseRepository courseRepository;

	private static final Logger logger = LoggerFactory.getLogger(CourseService.class);

	public List<CourseDTO> findAll() {
		List<CourseDTO> forReturn = new ArrayList<CourseDTO>();
		List<Course> courses = courseRepository.findAll();
		for (Course c : courses) {
			forReturn.add(new CourseDTO(c));
		}
		logger.info("Reading courses from database");
		return forReturn;
	}

}
