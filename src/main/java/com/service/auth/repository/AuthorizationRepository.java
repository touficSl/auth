package com.service.auth.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.service.auth.config.Constants;
import com.service.auth.model.Authorization;

import jakarta.transaction.Transactional;

@Repository
public interface AuthorizationRepository extends JpaRepository<Authorization, Long> {

	List<Authorization> findByUserRole(String role);

	@Query("SELECT r FROM Authorization r WHERE r.userRole = :userrole AND r.enable = true AND api LIKE '%:api%'")
	List<Authorization> findByEnabledUserRoleAndLikeApi(@Param("userrole") String userrole, @Param("api") String api);

    @Modifying
    @Transactional
	void deleteByUserRole(String userRole);
    
	@Query("SELECT r FROM Authorization r WHERE r.userRole = :userrole AND r.enable = true")
	List<Authorization> findByEnabledUserRole(@Param("userrole") String role);

	@Query("SELECT r FROM Authorization r WHERE r.userRole = :userrole AND r.enable = true AND (menuauthid = :menuauthid OR menuauthid = '" + Constants.GLOBAL + "') AND api LIKE '%:api%'")
	List<Authorization> findByEnabledUserRoleAndLikeApiAndMenuauthid(@Param("userrole") String userrole, @Param("api") String api, @Param("menuauthid") String menuauthid);

	@Query("SELECT r FROM Authorization r WHERE r.userRole = :userrole AND r.enable = true AND (menuauthid = :menuauthid OR menuauthid = '" + Constants.GLOBAL + "')")
	List<Authorization> findByEnabledUserRoleAndMenuauthid(@Param("userrole") String userrole, @Param("menuauthid") String menuauthid);
}
