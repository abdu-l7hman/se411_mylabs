package psu.se411.lab14.controller;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import psu.se411.lab14.exception.ObjectNotFoundException;
import psu.se411.lab14.model.Course;
import psu.se411.lab14.service.CourseService;

@RestController
@RequestMapping("/api/v1/courses")
public class CourseController {

	private final CourseService courseService;

	public CourseController(CourseService courseService) {
		this.courseService = courseService;
	}

	/** GET /api/v1/courses -> 200 with all courses */
	@GetMapping()
	public ResponseEntity<Iterable<Course>> getAllCourses() {
		return ResponseEntity.ok(courseService.getAllCourses());
	}

	/** GET /api/v1/courses/{id} -> 200, 404 if missing, 400 if id <= 0 */
	@GetMapping("/{id}")
	public ResponseEntity<Course> getCourseById(@PathVariable @Positive Long id) {
		return courseService.getCourseById(id)
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}

	/** POST /api/v1/courses -> 201 + Location header, 400 if invalid or if an id is sent */
	@PostMapping()
	public ResponseEntity<Void> createCourse(@Valid @RequestBody Course course, UriComponentsBuilder ucb) {
		if (course.id() != null) {
			return ResponseEntity.badRequest().build(); // the database chooses the id
		}
		Course created = courseService.createCourse(course);
		URI location = ucb.path("/api/v1/courses/{id}").buildAndExpand(created.id()).toUri();
		return ResponseEntity.created(location).build();
	}

	/** PUT /api/v1/courses/{id} -> 204, 404 if missing, 400 if invalid */
	@PutMapping("/{id}")
	public ResponseEntity<Void> updateCourse(@PathVariable @Positive Long id, @Valid @RequestBody Course course) {
		if (course.id() != null && !course.id().equals(id)) {
			return ResponseEntity.badRequest().build(); // body id must match the URL id
		}
		try {
			courseService.updateCourse(id, course);
			return ResponseEntity.noContent().build();
		} catch (ObjectNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}

	/** DELETE /api/v1/courses/{id} -> 204, 404 if missing */
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteCourse(@PathVariable @Positive Long id) {
		try {
			courseService.deleteCourse(id);
			return ResponseEntity.noContent().build();
		} catch (ObjectNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}
}
