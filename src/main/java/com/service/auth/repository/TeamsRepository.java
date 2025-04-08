package com.service.auth.repository;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.service.auth.model.Teams;

@Repository
public interface TeamsRepository extends JpaRepository<Teams, String> {

	Page<Teams> findAll(Specification<Teams> spec, Pageable pageable);

	List<Teams> findAll(Specification<Teams> spec);

	void deleteById(String name);
}
