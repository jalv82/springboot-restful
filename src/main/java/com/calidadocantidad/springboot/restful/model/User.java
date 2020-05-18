package com.calidadocantidad.springboot.restful.model;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.calidadocantidad.springboot.restful.validator.MinorThan18;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author jalv
 * Entidad de usuarios
 */
@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {

	/**
	 * Identificador de la clase
	 */
	private static final long serialVersionUID = -7328893335306501204L;
	
	/**
	 * Identificador del usuario
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_SEQ")
	@SequenceGenerator(sequenceName = "user_seq", initialValue = 3, allocationSize = 1, name = "USER_SEQ")	
	private Integer id;
	
	/**
	 * Nombre del usuario
	 */
	@NotEmpty(message = "{user.name.notEmpty}")
	@Size(min = 3, max = 50, message = "{user.name.size}")
	private String name;
	
	/**
	 * Fecha de nacimiento
	 */
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	@MinorThan18(message = "{user.name.minorThan18}")
	private LocalDate birthdate;
}
