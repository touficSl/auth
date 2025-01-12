package com.service.auth.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.service.auth.model.Authorization;

import jakarta.transaction.Transactional;

@Repository
public interface AuthorizationRepository extends JpaRepository<Authorization, Long> {

	List<Authorization> findByUserRole(String role);

	@Query("SELECT r FROM Authorization r WHERE r.userRole = :userrole AND api LIKE '%:api%'")
	List<Authorization> findByUserRoleAndLikeApi(@Param("userrole") String userrole, @Param("api") String api);

    @Modifying
    @Transactional
	void deleteByUserRole(String userRole);

}
