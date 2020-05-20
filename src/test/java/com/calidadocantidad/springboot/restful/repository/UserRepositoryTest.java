package com.calidadocantidad.springboot.restful.repository;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;

import java.time.LocalDate;
import java.time.Month;
import java.util.Optional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
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
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import com.calidadocantidad.springboot.restful.model.User;

import lombok.extern.slf4j.Slf4j;

/**
 * @author jalv
 * Tests of model
 */
@Slf4j
@SpringBootTest
@DisplayName("Tests of model")
@Tag("Model")
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserRepositoryTest {

	@Autowired
    private PlatformTransactionManager platformTransactionManager;
	
	@Autowired
    private UserRepository userRepository;
	
	private TransactionTemplate transactionTemplate;
	private User user;
	
	@BeforeAll
	void start() {
		this.user = User.builder()
				.name("Tux")
				.birthdate(LocalDate.of(1991, Month.AUGUST.getValue(), 21))
				.build();
	}
	
	@AfterAll
	void end() {
		this.userRepository.delete(this.user);
	}
	
	@BeforeEach
    public void setUp() {
        this.transactionTemplate = new TransactionTemplate(platformTransactionManager);
    }
	
	@Test
	@Order(0)
	@DisplayName("Save user. Expected OK.")
	void saveUser_ok(final TestReporter testReporter) {

		// given
		this.transactionTemplate.execute(status ->  this.userRepository.save(this.user));
		
		// when
	    final Optional<User> userBBDDOptional = transactionTemplate.execute(status -> this.userRepository.findById(this.user.getId()));
        
        // then
        final User userBBDD = userBBDDOptional.orElse(null);
        assertThat(userBBDD, is(not(nullValue())));
        assertThat(userBBDD.getId(), is(this.user.getId()));
        assertThat(userBBDD.getName(), is(this.user.getName()));
        assertThat(userBBDD.getBirthdate(), is(this.user.getBirthdate()));
        
        // logs
        testReporter.publishEntry(userBBDD.toString());
        log.info(userBBDD.toString());
        
	}

	@Test
	@DisplayName("Update user's name. Expected OK.")
	@Order(1)
	void updateUserName_ok(final TestReporter testReporter) {
		
		// given
		this.user.setName("Duke");
		this.transactionTemplate.execute(status ->  this.userRepository.save(this.user));
		
		// when
	    final Optional<User> userBBDDOptional = transactionTemplate.execute(status -> this.userRepository.findById(this.user.getId()));
        
        // then
        final User userBBDD = userBBDDOptional.orElse(null);
        assertThat(userBBDD, is(not(nullValue())));
        assertThat(userBBDD.getId(), is(this.user.getId()));
        assertThat(userBBDD.getName(), is(this.user.getName()));
        assertThat(userBBDD.getBirthdate(), is(this.user.getBirthdate()));
        
        // logs
        testReporter.publishEntry(userBBDD.toString());
        log.info(userBBDD.toString());
		
	}
	
	@Test
	@DisplayName("Delete user. Expected OK.")
	@Order(2)
	void deleteUser_ok(final TestReporter testReporter) {
		
		// given
		final Optional<User> user2Delete = this.userRepository.findById(this.user.getId());
		
		// when
	    this.userRepository.deleteById(user2Delete.orElse(null).getId());
        
        // then
        assertThat(this.userRepository.count(), is(0L));
        
        // logs
        testReporter.publishEntry("Delete user ok");
        log.info("Delete user ok");
		
	}
}