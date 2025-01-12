package com.service.auth.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.service.auth.model.Roles;

@Repository
public interface RoleRepository extends JpaRepository<Roles, String> {

	Roles findByUserRole(String role);

	Page<Roles> findAll(Specification<Roles> spec, Pageable pageable);

	List<Roles> findAll(Specification<Roles> spec);
}
