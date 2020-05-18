package com.calidadocantidad.springboot.restful.dto;

import java.io.Serializable;
import java.time.LocalDate;

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
 * Clase de intercambio para los datos de usuarios
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO implements Serializable {
	
	/**
	 * Identificador de la clase
	 */
	private static final long serialVersionUID = -8977848635156155650L;
	
	/**
	 * Identificador del usuario
	 */
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
