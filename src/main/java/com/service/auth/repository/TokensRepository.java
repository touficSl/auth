package com.service.auth.repository;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.service.auth.model.Tokens;

@Repository
public interface TokensRepository extends JpaRepository<Tokens, String> {

	List<Tokens> findByUsername(String username);

	List<Tokens> findByRefreshtokenAndUsername(String refreshtoken, String username);

	List<Tokens> findByAccesstokenAndUsername(String accesstoken, String username);

    long count();

    Page<Tokens> findAll(Pageable pageable);

	Page<Tokens> findAll(Specification<Tokens> spec, Pageable pageable);

	List<Tokens> findAll(Specification<Tokens> spec);
}
