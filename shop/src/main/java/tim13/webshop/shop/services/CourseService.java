package tim13.webshop.shop.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tim13.webshop.shop.dto.CourseDTO;
import tim13.webshop.shop.model.Course;
import tim13.webshop.shop.repositories.ICourseRepository;

@Service
public class CourseService {

	@Autowired
	private ICourseRepository courseRepository;

	public List<CourseDTO> findAll() {
		List<CourseDTO> forReturn = new ArrayList<CourseDTO>();
		List<Course> courses = courseRepository.findAll();
		for (Course c : courses) {
			forReturn.add(new CourseDTO(c));
		}
		return forReturn;
	}

}
