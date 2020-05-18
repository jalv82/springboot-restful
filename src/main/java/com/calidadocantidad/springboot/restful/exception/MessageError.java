package com.calidadocantidad.springboot.restful.exception;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

/**
 * @author jalv
 * Información personalizada de los errores
 */
@Data
public class MessageError {

	/**
	 * Fecha y hora cuando ocurre la excepción
	 */
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss dd-MM-yyyy")
	private LocalDateTime timestamp;
	
	/**
	 * Código de la respuesta http
	 */
	private String httpStatus;
	
	/**
	 * Mensaje de la excepción
	 */
	private String message;
}
