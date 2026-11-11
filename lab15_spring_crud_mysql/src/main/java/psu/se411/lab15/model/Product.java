package psu.se411.lab15.model;

import java.math.BigDecimal;

import org.springframework.data.annotation.Id;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/** Product stored in the MySQL "product" table. Price matches DECIMAL(8,2). */
public record Product(
		@Id Integer id,

		@NotBlank(message = "name is required")
		@Size(max = 100, message = "name must be at most 100 characters") String name,

		@Size(max = 100, message = "description must be at most 100 characters") String description,

		@NotNull(message = "price is required")
		@DecimalMin(value = "0.0", inclusive = false, message = "price must be greater than 0")
		@Digits(integer = 6, fraction = 2, message = "price must have at most 6 digits and 2 decimals") BigDecimal price
) {
}
