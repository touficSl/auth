package com.service.auth.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.service.auth.model.Menu;

@Repository
public interface MenuRepository extends JpaRepository<Menu, String> {

    @Query("SELECT p FROM Menu p WHERE parentId = :parentId AND lang = :lang ORDER BY order ASC")
	List<Menu> findByParentIdAndLang(@Param("parentId") String parentId, @Param("lang") String lang);

    @Query("SELECT p FROM Menu p WHERE parentId IS NULL AND lang = :lang ORDER BY order ASC")
	List<Menu> findWithNoParent(@Param("lang") String lang);

    List<Menu> findByAuthId(String authId);
    
}
