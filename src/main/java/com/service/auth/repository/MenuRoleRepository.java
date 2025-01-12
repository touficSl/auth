package com.service.auth.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.service.auth.model.MenuRole;

import jakarta.transaction.Transactional;

@Repository
public interface MenuRoleRepository extends JpaRepository<MenuRole, Long> {

    @Query("SELECT p FROM MenuRole p WHERE p.user_role = :user_role AND menu.parentId IS NULL AND menu.lang = :lang ORDER BY menu.order ASC")
	List<MenuRole> findByUserRoleNoParent(@Param("user_role") String userrole, @Param("lang") String lang);

    @Query("SELECT p FROM MenuRole p WHERE p.user_role = :user_role AND menu.parentId = :parentId AND menu.lang = :lang ORDER BY menu.order ASC")
    List<MenuRole> findByUserRoleAndParentId(@Param("user_role") String userrole, @Param("parentId") String parentId, @Param("lang") String lang);

    @Modifying
    @Transactional
    @Query("DELETE FROM MenuRole u WHERE u.user_role = :role")
    void deleteByUserRole(String role);
    
}
