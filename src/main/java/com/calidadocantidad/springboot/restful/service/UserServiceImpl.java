package com.calidadocantidad.springboot.restful.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.calidadocantidad.springboot.restful.dto.UserDTO;
import com.calidadocantidad.springboot.restful.exception.UserNotFoundException;
import com.calidadocantidad.springboot.restful.model.User;
import com.calidadocantidad.springboot.restful.repository.UserRepository;
import com.calidadocantidad.springboot.restful.utils.ModelMapperUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * {@inheritDoc}
 */
@Slf4j
@Service	
public class UserServiceImpl implements UserService {
	
	/**
	 * Mensaje de log de obtención de todos los usuarios
	 */
	@Value("${userServiceImpl.getAll}")
	private String logGetAll;

	/**
	 * Mensaje de log de obtención de un usuario
	 */
	@Value("${userServiceImpl.get}")
	private String logGet;
	
	/**
	 * Mensaje de log de creación de un usuario
	 */
	@Value("${userServiceImpl.create}")
	private String logCreate;
	
	/**
	 * Mensaje de log de actualización de un usuario
	 */
	@Value("${userServiceImpl.update}")
	private String logUpdate;
	
	/**
	 * Mensaje de log de eliminación de un usuario
	 */
	@Value("${userServiceImpl.delete}")
	private String logDelete;
	
	/**
	 * Mensaje de excepcion para un usuario no encontrado
	 */
	@Value("${userServiceImpl.userNotFoundException}")
	private String messageUserNotFoundException;
	
	/**
	 * Mensaje de log para la conversión
	 */
	@Value("${userServiceImpl.convertDTO2Entity}")
	private String logConvertDTO2Entity;
	
	/**
	 * Acceso a las operaciones de persistencia
	 */
	private final UserRepository userRepository;
	
	/**
	 * Conversor DTO a entidad y viceversa
	 */
	private final ModelMapperUtils modelMapperUtils;

	@Autowired
	public UserServiceImpl(UserRepository userRepository,
						   ModelMapperUtils modelMapperUtils) {
		this.userRepository = userRepository;
		this.modelMapperUtils = modelMapperUtils;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public Collection<UserDTO> getAll() {	
		log.info(this.logGetAll);
		return this.modelMapperUtils.mapAll2List(this.userRepository.findAll(), UserDTO.class);
	}
	
	/**
	 * {@inheritDoc}
	 */
	public UserDTO get(final Integer id) {
		log.info(this.logGet, id);
		final User user = this.userRepository.findById(id)
							.orElseThrow(() -> new UserNotFoundException(this.messageUserNotFoundException));
		return this.modelMapperUtils.map(user, UserDTO.class);	
	}

	/**
	 * {@inheritDoc}
	 */
	public UserDTO create(final UserDTO userDTO) {		
		log.info(this.logCreate);
		final User user = this.modelMapperUtils.map(userDTO, User.class);
		return this.modelMapperUtils.map(this.userRepository.save(user), UserDTO.class);		
	}
	
	/**
	 * {@inheritDoc}
	 */
	public UserDTO update(final UserDTO userDTO) {
		
		User user = this.modelMapperUtils.map(userDTO, User.class);
		
		user = this.userRepository.findById(user.getId())
				.orElseThrow(() -> new UserNotFoundException(this.messageUserNotFoundException));
		
		log.info(this.logUpdate, user.getId());
		
		return this.modelMapperUtils.map(this.userRepository.save(user), UserDTO.class);
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void remove(final Integer id) {
		log.info(this.logDelete, id);
		this.userRepository.deleteById(id);
	} 
	
}