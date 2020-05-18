package com.calidadocantidad.springboot.restful.utils;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

/**
 * Clase que nos permite mapear los DTOs en Entidades y viceversa:
 * <ul>
 * 	<li>De la vista al modelo se envían conjuntos por rendimiento de JPA</li>
 * 	<li>Del modelo a la vista se envían listados para poder tener un identificador cuando los iteramos en la vista</li>
 * </ul>
 * 
 * @author jalv
 */
@Component
public final class ModelMapperUtils {

	@Autowired
	private static ModelMapper modelMapper;

	static {
	    modelMapper = new ModelMapper();
	    modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
	}

	private ModelMapperUtils() {
	}
	
	/**
	 * Método genérico para mapear DTOs en Entidades y viceversa
	 * @param <D> Tipo de clase
	 * @param <T> Tipo de objeto
	 * @param entity Objeto origen
	 * @param outClass Clase destino
	 * @return Objeto mapeado
	 */
	public <D, T> D map(final T entity, Class<D> outClass) {
	    return modelMapper.map(entity, outClass);
	}

	/**
	 * Método genérico para mapear colecciones de DTOS en conjuntos de Entidades
	 * @param <D> Tipo de clase
	 * @param <T> Tipo de colección
	 * @param entityCollection Colección origen
	 * @param outCLass Clase destino
	 * @return Colección
	 */
	public <D, T> Set<D> mapAll2Set(final Collection<T> entityCollection, Class<D> outCLass) {
	    return entityCollection.stream()
					    		.map(entity -> this.map(entity, outCLass))
					    		.collect(Collectors.toSet());
	}
	
	/**
	 * Método genérico para mapear colecciones de Entidades en listas de DTOS
	 * @param <D> Tipo de clase
	 * @param <T> Tipo de colección
	 * @param entityCollection Colección origen
	 * @param outCLass Clase destino
	 * @return Colección
	 */
	public <D, T> List<D> mapAll2List(final Collection<T> entityCollection, Class<D> outCLass) {
		return entityCollection.stream()
								.map(entity -> this.map(entity, outCLass))
								.collect(Collectors.toList());
	}
	
	/**
	 * Método genérico para mapear Page de Entidades en Page de DTOs
	 * @param <D> Tipo de clase
	 * @param <T> Tipo de colección
	 * @param entityPage Page origen
	 * @param pageable Información de la paginación
	 * @param totalElements
	 * @param outCLass Clase destino
	 * @return Page
	 */
	public <D, T> PageImpl<D> mapAll2Page(final Page<T> entityPage, final Pageable pageable, final Long totalElements, Class<D> outCLass) {
		return new PageImpl<> (entityPage.stream()
										.map(entity -> this.map(entity, outCLass))
										.collect(Collectors.toList()), pageable, totalElements);
	}
	
}
