package com.calidadocantidad.springboot.restful.exception;

/**
 * @author jalv
 * Excepción personalizada cuando no se encuentra un usuario
 */
public class UserNotFoundException extends RuntimeException {

	/**
	 * Identificador de la clase
	 */
	private static final long serialVersionUID = 5501181391095374852L;
	
	/**
	 * Contructor con el mensaje personalizado
	 * @param message Mensaje personalizado
	 */
	public UserNotFoundException(final String message) {
		super(message);
	}
	

}
