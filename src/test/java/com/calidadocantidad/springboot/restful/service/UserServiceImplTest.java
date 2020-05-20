package com.calidadocantidad.springboot.restful.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.Month;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.TestReporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.EmptyResultDataAccessException;

import com.calidadocantidad.springboot.restful.dto.UserDTO;
import com.calidadocantidad.springboot.restful.model.User;
import com.calidadocantidad.springboot.restful.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;


/**
 * @author jalv
 * Integration test of business logic
 */
@Slf4j
@SpringBootTest
@DisplayName("Integration tests of business logic")
@Tag("Service")
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserServiceImplTest {

	@MockBean
	private UserRepository userRepository;

	@Autowired
	private UserService userService;

	private Integer id;
	private User user;
	private Optional<User> userOptional;
	private UserDTO userDTO;
	
	@BeforeAll
	void start() {
		this.id = 3;		
		this.user = User.builder()
				.id(this.id)
				.name("Tux")
				.birthdate(LocalDate.of(1991, Month.AUGUST.getValue(), 21))
				.build();
		this.userOptional = Optional.of(this.user);
		this.userDTO = UserDTO.builder()
				.id(this.id)
				.name("Tux")
				.birthdate(LocalDate.of(1991, Month.AUGUST.getValue(), 21))
				.build();
	}
	
	@Test
	@Order(0)
	@DisplayName("Find user by id. Expected OK.")
	void findUserById_ok(final TestReporter testReporter) {
		
		// given
		when(this.userRepository.findById(this.id)).thenReturn(this.userOptional);
		
		// when
		final UserDTO userFromService = this.userService.get(this.id);
		
		// then
		final User userFromRepository = this.userOptional.orElse(null);
		assertThat(userFromService, is(not(nullValue())));
		assertThat(userFromService.getId(), is(userFromRepository.getId()));
		assertThat(userFromService.getName(), is(userFromRepository.getName()));
		assertThat(userFromService.getBirthdate(), is(userFromRepository.getBirthdate()));
        
		// verification
		verify(this.userRepository, times(1)).findById(this.id);

		// logs
        testReporter.publishEntry(userFromService.toString());
        log.info(userFromService.toString());
        
	}

	@Test
	@Order(1)
	@DisplayName("Save user. Expected OK.")
	void saveUser_ok(final TestReporter testReporter) {
		
		// given
		when(this.userRepository.save(this.user)).thenReturn(this.user);
		
		// when
		final UserDTO userFromService = this.userService.create(this.userDTO);
		
		// then
		assertThat(userFromService, is(not(nullValue())));
		assertThat(userFromService.getId(), is(this.user.getId()));
		assertThat(userFromService.getName(), is(this.user.getName()));
		assertThat(userFromService.getBirthdate(), is(this.user.getBirthdate()));
        
		// verification
		verify(this.userRepository, times(1)).save(this.user);

		// logs
        testReporter.publishEntry(userFromService.toString());
        log.info(userFromService.toString());
        
	}

	@Test
	@Order(2)
	@DisplayName("Delete nonexistent user. Expected OK.")
	void deleteNonexistentUser_ok(final TestReporter testReporter) {
		
		final Integer idUserNonexistent = -1;
		
		// given
		doThrow(EmptyResultDataAccessException.class).when(this.userRepository).deleteById(idUserNonexistent);

		// when
		assertThrows(EmptyResultDataAccessException.class, () -> this.userService.remove(idUserNonexistent));
		
		// verification
		verify(this.userRepository, times(1)).deleteById(idUserNonexistent);
		
		// logs
		testReporter.publishEntry("Delete nonexistent user ok");
		log.info("Delete nonexistent user ok");
		
	}

	@Test
	@Order(3)
	@DisplayName("Delete user. Expected OK.")
	void deleteUser_ok(final TestReporter testReporter) {
		
		// when
		this.userService.remove(this.id);
		
		// verification
		verify(this.userRepository, times(1)).deleteById(this.id);
		
		// logs
		testReporter.publishEntry("Delete user ok");
		log.info("Delete user ok");
		
	}
	
}
