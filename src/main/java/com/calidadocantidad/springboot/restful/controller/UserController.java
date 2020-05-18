package com.calidadocantidad.springboot.restful.controller;

import java.util.Collection;

import javax.validation.Valid;
import javax.validation.constraints.Min;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.calidadocantidad.springboot.restful.dto.UserDTO;
import com.calidadocantidad.springboot.restful.service.UserService;

import io.swagger.v3.oas.annotations.Operation;

/**
 * @author jalv
 * Endpoints para usuarios
 */
@RestController
@RequestMapping("/api")	
@Validated
public class UserController {
	
	/**
	 * Acceso a lógica de usuarios
	 */
	private final UserService userService;

	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	/**
	 * Método con el que obtenemos todos los usuarios existentes
	 * @return Listado de usuarios
	 */
	@Operation(summary = "Búsqueda de usuarios", description = "Buscamos todos los usuarios")
	@GetMapping(value = "/v1/user")
	public Collection<UserDTO> getAll() {		
		return this.userService.getAll();
    } 
	
	/**
	 * Método con el que buscamos un usuario mediante el identificador facilitado 
	 * @param id Identificador del usuario
	 * @return Usuario encontrado
	 */
	@Operation(summary = "Búsqueda de un usuario", description = "Buscamos un usuarios a través de su identificador")
	@GetMapping(value = "/v1/user/{id}")
	@ResponseStatus(HttpStatus.OK)
	public UserDTO get(@PathVariable @Min(1) final Integer id) {
		return this.userService.get(id);				
    } 
	
	/**
	 * Método con el que creamos un nuevo usuario con los datos facilitados
	 * @param userDTO Datos del nuevo usuario
	 * @return Usuario creado
	 */
	@Operation(summary = "Creación de un usuario", description = "Creamos un nuevo usuario con los datos facilitados")
	@PostMapping(value = "/v1/user")
	@ResponseStatus(HttpStatus.CREATED)
    public UserDTO create(@Valid @RequestBody final UserDTO userDTO) {    	
		return this.userService.create(userDTO);
	}
	
	/**
	 * Método con el que actualizamos un usuario existente con los datos facilitados
	 * @param userDTO Datos para el usuario existente
	 * @return Usuario actualizado
	 */
	@Operation(summary = "Actualización de un usuario", description = "Actualizamos un usuario existente con los datos facilitados")
	@PutMapping(value = "/v1/user")
	@ResponseStatus(HttpStatus.OK)
    public UserDTO update(@Valid @RequestBody final UserDTO userDTO) {    	
		return this.userService.update(userDTO);
	}
	
	/**
	 * Método con el que eliminamos de forma permanente el usaurio con el identificador facilitado
	 * @param id Identificador del usuario
	 */
	@Operation(summary = "Eliminación de un usuario", description = "Eliminamos un usuario existente a través de su identificador")
	@DeleteMapping(value = "/v1/user/{id}")
	@ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable @Min(1) final Integer id) {
		this.userService.remove(id);
	}
	
}
