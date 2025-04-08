package com.service.auth.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.service.auth.model.Roles;

@Repository
public interface RoleRepository extends JpaRepository<Roles, String> {

	Roles findByUserRole(String role);

	Page<Roles> findAll(Specification<Roles> spec, Pageable pageable);

	List<Roles> findAll(Specification<Roles> spec);

	List<Roles> findByParentrole(String user_role);

	List<Roles> findByParentroleAndTeam(String user_role, String team);

	List<Roles> findByTeam(String name);

    @Query("SELECT DISTINCT r.team FROM Roles r WHERE r.parentrole = :parentrole ORDER BY r.team ASC ")
	List<String> finddistinctteams(@Param("parentrole") String parentrole);
}
