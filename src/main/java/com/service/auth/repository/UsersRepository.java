package com.service.auth.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.service.auth.model.Users;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {
  Optional<Users> findByUsername(String username);

  long count();
  Page<Users> findAll(Pageable pageable);

  List<Users> findAll(Specification<Users> spec);
  Page<Users> findAll(Specification<Users> spec, Pageable pageable);

  List<Users> findByUserrole(String userrole);
  
}
