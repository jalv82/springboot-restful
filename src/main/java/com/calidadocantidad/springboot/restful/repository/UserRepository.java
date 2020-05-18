package com.calidadocantidad.springboot.restful.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.calidadocantidad.springboot.restful.model.User;

/**
 * @author jalv
 * Operaciones de persistencia con bbdd
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

}
