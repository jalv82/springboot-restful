package com.calidadocantidad.springboot.restful.validator;

import java.time.LocalDate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author jalv
 * Validación que comprueba que entre la fecha actual del sistema y la fecha facilitada
 * hayan transcurrido al menos 18 años
 */
public class MinorThan18Validator implements ConstraintValidator<MinorThan18, LocalDate> {

	@Override
	public boolean isValid(final LocalDate value, final ConstraintValidatorContext context) {
		return ((LocalDate.now().minusYears(18).isAfter(value))
				|| (LocalDate.now().minusYears(18).isEqual(value)));
	}
}
