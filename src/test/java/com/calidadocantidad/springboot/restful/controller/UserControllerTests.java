/**
 * 
 */
package com.calidadocantidad.springboot.restful.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.Month;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.calidadocantidad.springboot.restful.dto.UserDTO;
import com.calidadocantidad.springboot.restful.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

/**
 * @author jalv
 * Integration tests of controller
 */
@SpringBootTest
@DisplayName("Integration tests of controller")
@Tag("Controller")
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
class UserControllerTests {

	@MockBean
	private UserService userService;

	@Autowired
    private MockMvc mockMvc;
	
	@Autowired
    private ObjectMapper objectMapper;
	
	private final Integer id = 3;
	private final String uri = "/api/v1/user/";
	private final String uriWithId = this.uri.concat(id.toString());
	
	/**
	 * Petición con los datos de creación de un usuario
	 */
	private String requestCreate;
	
	/**
	 * Petición con los datos de actualización de un usuario
	 */
	private String requestUpdate;
	
	/**
	 * Método con el que cargamos el script sql en bbdd y preparamos datos
	 * @throws Excepción en caso de error con el tratamiento de json
	 */
	@BeforeAll
    public void init() throws JsonProcessingException {
		
		final UserDTO userDTO = UserDTO.builder()
										.id(this.id)
										.name("Tux")
										.birthdate(LocalDate.of(1991, Month.AUGUST.getValue(), 21))
										.build();
		
		// Solucionamos el problema con la serrialización de LocalDate 
		this.objectMapper.registerModule(new JavaTimeModule())
						.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
										
		this.requestCreate = this.objectMapper.writeValueAsString(userDTO);					
		
		final UserDTO userDTOUpdateName = userDTO;
		userDTOUpdateName.setName("Tux Jr");
		this.requestUpdate = objectMapper.writeValueAsString(userDTOUpdateName);
    }

	/**
	 * Método con el que comprobamos la correcta obtención de todos los usuarios existentes
	 * Se ejecutará el 1/5
	 * @throws Excepción en caso de error
	 */
	@Test
	@Order(1)
	@DisplayName("Get all users. Expected OK.")
	public void getAllUsers_ok() throws Exception {
				
		this.mockMvc.perform(MockMvcRequestBuilders
								.get(this.uri)
								.contentType(MediaType.APPLICATION_JSON)
								.accept(MediaType.APPLICATION_JSON))
					      		.andDo(print())
					      		.andExpect(status().isOk());
	}
	
	/**
	 * Método con el que comprobamos la correcta obtención un usuario
	 * Se ejecutará el 2/5
	 * @throws Excepción en caso de error
	 */
	@Test
	@Order(2)
	@DisplayName("Get user by id. Expected OK.")
	public void getUser_ok() throws Exception {
				
		this.mockMvc.perform(MockMvcRequestBuilders
								.get(this.uriWithId)
								.contentType(MediaType.APPLICATION_JSON)
								.accept(MediaType.APPLICATION_JSON))
					      		.andDo(print())
					      		.andExpect(status().isOk());	
		
	}
	
	/**
	 * Método con el que comprobamos la correcta creación de un usuario
	 * Se ejecutará el 3/5
	 * @throws Exception Excepción en caso de error
	 */
	@Test
	@Order(3)
	@DisplayName("Create new user. Expected OK.")
	public void createUser_ok() throws Exception {
				
		this.mockMvc.perform(MockMvcRequestBuilders
				.post(this.uri)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(this.requestCreate))
	      		.andDo(print())
	      		.andExpect(status().isCreated());		
		
	}
	
	/**
	 * Método con el que comprobamos la correcta actualización de un usuario existente
	 * Se ejecutará el 4/5
	 * @throws Exception Excepción en caso de error
	 */
	@Test
	@Order(4)
	@DisplayName("Update user. Expected OK.")
	public void updateUser_ok() throws Exception {
				
		this.mockMvc.perform(MockMvcRequestBuilders
								.put(this.uri)
								.content(this.requestUpdate)
								.contentType(MediaType.APPLICATION_JSON)
								.accept(MediaType.APPLICATION_JSON))
					      		.andDo(print())
					      		.andExpect(status().isOk());		
	}
	
	/**
	 * Método con el que comprobamos la correcta eliminación de un usuario existente
	 * Se ejecutará el 5/5
	 * @throws Exception Excepción en caso de error
	 */
	@Test
	@Order(5)
	@DisplayName("Delete user. Expected OK.")
	public void deleteUser_ok() throws Exception {

		this.mockMvc.perform(MockMvcRequestBuilders
								.delete(this.uriWithId)
								.contentType(MediaType.APPLICATION_JSON)
								.accept(MediaType.APPLICATION_JSON))
								.andDo(print())
								.andExpect(status().isOk());  		
	}
	
}
