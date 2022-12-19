package tech.hiphone.commons.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tech.hiphone.commons.domain.Authority;

public interface AuthorityRepository extends JpaRepository<Authority, String> {
}
