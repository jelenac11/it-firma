package tim13.webshop.shop.mapper;

import org.springframework.stereotype.Component;

import tim13.webshop.shop.dto.CourseDTO;
import tim13.webshop.shop.model.Course;

@Component
public class CourseMapper {

	public Course toEntity(CourseDTO courseDTO) {
		Course course = new Course();

		course.setId(courseDTO.getId());
		course.setDescription(courseDTO.getDescription());
		course.setEndDate(courseDTO.getEndDate());
		course.setLocation(courseDTO.getLocation());
		course.setName(courseDTO.getName());
		course.setPrice(courseDTO.getPrice());
		course.setStartDate(courseDTO.getStartDate());

		return course;
	}

	public CourseDTO toDTO(Course course) {
		CourseDTO courseDTO = new CourseDTO();

		courseDTO.setId(course.getId());
		courseDTO.setDescription(course.getDescription());
		courseDTO.setEndDate(course.getEndDate());
		courseDTO.setLocation(course.getLocation());
		courseDTO.setName(course.getName());
		courseDTO.setPrice(course.getPrice());
		courseDTO.setStartDate(course.getStartDate());

		return courseDTO;
	}
}
