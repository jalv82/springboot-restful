package com.calidadocantidad.springboot.restful.exception;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;

import lombok.extern.slf4j.Slf4j;

/**
 * @author jalv
 * Gestión global de excepciones
 */
@Slf4j
@RestControllerAdvice
public class GlobalControllerExceptionHandler  {
	
	/**
	 * Mensaje de log de usuario no encontrado
	 */
	@Value("${globalControllerExceptionHandler.handleUserNotFound}")
	private String logHandleUserNotFound;
	
	/**
	 * Mensaje de log de argumento ilegal
	 */
	@Value("${globalControllerExceptionHandler.handleIllegalArgument}")
	private String logHandleIllegalArgument;
	
	/**
	 * Mensaje de log de restricción no cumplida
	 */ 
	@Value("${globalControllerExceptionHandler.handleViolationException}")
	private String logHandleViolationException;
	
	/**
	 * Mensaje de log de argumento no válido
	 */
	@Value("${globalControllerExceptionHandler.handleMethodArgumentNotValid}")
	private String logHndleMethodArgumentNotValid;
	
	/**
	 * Método con el que asociamos las excepciones que deben 'saltar' cuando no se encuentre un usuario
	 * @param e Excepción
	 * @return Mensaje de error
	 */
	@ExceptionHandler({UserNotFoundException.class, EmptyResultDataAccessException.class})
    public ResponseEntity<MessageError> handleUserNotFound(final Exception e) {
		
		log.error(this.logHandleUserNotFound, e);
		
		return new ResponseEntity<>(
				this.getMessageError(e, HttpStatus.NOT_FOUND), 
				HttpStatus.NOT_FOUND);	
	}
	
	/**
	 * Método con el que asociamos las excepciones que deben 'saltar' cuando se encuentren argumentos no permitidos
	 * @param e Excepción
	 * @return Mensaje de error
	 */
	@ExceptionHandler({IllegalArgumentException.class, InvalidFormatException.class})
	public ResponseEntity<MessageError> handleIllegalArgument(final Exception e) {
		
		log.error(this.logHandleIllegalArgument, e);
		
		return new ResponseEntity<>(
				this.getMessageError(e, HttpStatus.NOT_ACCEPTABLE), 
				HttpStatus.NOT_ACCEPTABLE);
	}
	
	/**
	 * Método con el que asociamos las excepciones que deben 'saltar' cuando no se respetan las resticciones
	 * @param e Excepción
	 * @return Mensaje de error
	 * @throws IOException Excepción que se lanza
	 */
	@ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<MessageError> handleViolationException(final ConstraintViolationException e) throws IOException {
		
		log.error(this.logHandleViolationException, e);
		
		return new ResponseEntity<>(
				this.getMessageError(e, HttpStatus.BAD_REQUEST), 
				HttpStatus.BAD_REQUEST);
    }
	
	/**
	 * Método con el que asociamos las excepciones que deben 'saltar' cuando se encuentren argumentos no permitidos
	 * @param e Excepción
	 * @return Mensaje de error
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	protected ResponseEntity<MessageError> handleMethodArgumentNotValid(final MethodArgumentNotValidException e) {
		
		log.error(this.logHndleMethodArgumentNotValid, e);
		
		return new ResponseEntity<>(
				this.getMessageError(e, HttpStatus.NOT_ACCEPTABLE), 
				HttpStatus.NOT_ACCEPTABLE);	
	}
	
	/**
	 * Método con el que generamos el mensaje de error
	 * @param e Excepción genérica
	 * @param enumeration Código y descripción de la respuesta http
	 * @return Mensaje de error
	 */
	private MessageError getMessageError(final Exception e, final Enum<?> enumeration) {
		final MessageError messageException = new MessageError();
		messageException.setMessage(e.getMessage());
		messageException.setHttpStatus(enumeration.toString());
		messageException.setTimestamp(LocalDateTime.now());
		return messageException;
	}
	
	/**
	 * Método con el que generamos el mensaje de error
	 * @param e Excepción de tipo MethodArgumentNotValidException
	 * @param enumeration Código y descripción de la respuesta http
	 * @return Mensaje de error
	 */
	private MessageError getMessageError(final MethodArgumentNotValidException e, final Enum<?> enumeration) {
		
		// Obtenemos todos los errores
		final Collection<String> errors = e.getBindingResult()
				.getFieldErrors()
				.stream()
				.map(FieldError::getDefaultMessage)
				.collect(Collectors.toList());
		
		final MessageError messageException = new MessageError();
		messageException.setMessage(errors.toString());
		messageException.setHttpStatus(enumeration.toString());
		messageException.setTimestamp(LocalDateTime.now());
		return messageException;
	}

}
