package com.service.auth.repository;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.service.auth.model.MenuAuthorization;

@Repository
public interface MenuAuthorizationRepository extends JpaRepository<MenuAuthorization, Long> {

	  List<MenuAuthorization> findAll(Specification<MenuAuthorization> spec);
	

}
