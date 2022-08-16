package com.chf.commons.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chf.commons.domain.Authority;

public interface AuthorityRepository extends JpaRepository<Authority, String> {
}
