package psu.se411.lab14.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import psu.se411.lab14.exception.ObjectNotFoundException;
import psu.se411.lab14.model.Course;
import psu.se411.lab14.repository.CourseRepository;

@Service
public class CourseService {

	private final CourseRepository courseRepository;

	public CourseService(CourseRepository courseRepository) {
		this.courseRepository = courseRepository;
	}

	public Iterable<Course> getAllCourses() {
		return courseRepository.findAll();
	}

	public Optional<Course> getCourseById(Long id) {
		return courseRepository.findById(id);
	}

	public Course createCourse(Course course) {
		return courseRepository.save(course);
	}

	public void updateCourse(Long id, Course course) {
		if (!courseRepository.existsById(id)) {
			throw new ObjectNotFoundException("Course " + id + " not found");
		}
		courseRepository.save(new Course(id, course.code(), course.name(), course.description(), course.credits()));
	}

	public void deleteCourse(Long id) {
		if (!courseRepository.existsById(id)) {
			throw new ObjectNotFoundException("Course " + id + " not found");
		}
		courseRepository.deleteById(id);
	}
}
