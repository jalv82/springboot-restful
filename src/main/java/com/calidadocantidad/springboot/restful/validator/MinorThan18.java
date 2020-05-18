package com.calidadocantidad.springboot.restful.validator;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author jalv
 * Anotación que comprueba que  entre la fecha actual del sistema y la fecha facilitada
 * hayan transcurrido al menos 18 años
 */
@Target({FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = MinorThan18Validator.class)
@Documented
public @interface MinorThan18 {

    String message() default "{minorThan18.default}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
