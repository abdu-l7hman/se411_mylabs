package psu.se411.lab14.model;

import org.springframework.data.annotation.Id;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record Course(
		@Id Long id,

		@NotBlank(message = "Course code cannot be empty")
		@Size(max = 20, message = "Course code cannot be longer than 20 characters") String code,

		@NotBlank(message = "Name cannot be empty")
		@Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters") String name,

		@Size(max = 100, message = "Description cannot be longer than 100 characters") String description,

		@NotNull(message = "Credits are required")
		@Min(value = 1, message = "Credits should be from 1 to 10")
		@Max(value = 10, message = "Credits should be from 1 to 10") Integer credits
) {
}
