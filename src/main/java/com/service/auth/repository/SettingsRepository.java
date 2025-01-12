package com.service.auth.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.service.auth.model.Settings;

@Repository
public interface SettingsRepository extends JpaRepository<Settings, Long> {

	List<Settings> findByIsdefault(boolean isdefault);
}
