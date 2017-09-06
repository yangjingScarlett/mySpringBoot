package com.yang.springboot.f_test;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Yangjing
 */
public interface DogRepository extends JpaRepository<Dog, Long> {
}
