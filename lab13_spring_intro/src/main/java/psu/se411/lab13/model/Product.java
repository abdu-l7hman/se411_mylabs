package psu.se411.lab13.model;

import java.math.BigDecimal;

import org.springframework.data.annotation.Id;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

/**
 * Product model. Price uses BigDecimal (step 13) to avoid the rounding
 * errors of Double (0.1 + 0.2 != 0.3). Validation rules from step 14.
 */
public record Product(
		@Id @Positive(message = "id must be a positive number") Integer id,

		@NotBlank(message = "name is required")
		@Size(max = 100, message = "name must be at most 100 characters") String name,

		@Size(max = 100, message = "description must be at most 100 characters") String description,

		@NotNull(message = "price is required")
		@DecimalMin(value = "0.0", inclusive = false, message = "price must be greater than 0")
		@Digits(integer = 8, fraction = 2, message = "price must have at most 8 digits and 2 decimals") BigDecimal price
) {
}
