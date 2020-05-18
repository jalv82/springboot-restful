package com.calidadocantidad.springboot.restful.service;

import java.util.Collection;

import com.calidadocantidad.springboot.restful.dto.UserDTO;

/**
 * @author jalv
 * Operaciones de lógica de usuarios
 */
public interface UserService {
	
	/**
	 * Método con el que obtenemos todos los usuarios existentes
	 * @return Listado de usuarios
	 */
	Collection<UserDTO> getAll();
	
	/**
	 * Método con el que buscamos un usuario mediante el identificador facilitado 
	 * @param id Identificador del usuario
	 * @return Usuario encontrado
	 */
	UserDTO get(final Integer id);
	
	/**
	 * Método con el que creamos un nuevo usuario con los datos facilitados
	 * @param userDTO Datos del nuevo usuario
	 * @return Usuario creado
	 */
	UserDTO create(final UserDTO userDTO);
	
	/**
	 * Método con el que actualizamos un usuario existente con los datos facilitados
	 * @param userDTO Datos para el usuario existente
	 * @return Usuario actualizado
	 */
	UserDTO update(final UserDTO userDTO);
	
	/**
	 * Método con el que eliminamos de forma permanente el usaurio con el identificador facilitado
	 * @param id Identificador del usuario
	 */
	void remove(final Integer id);

}
