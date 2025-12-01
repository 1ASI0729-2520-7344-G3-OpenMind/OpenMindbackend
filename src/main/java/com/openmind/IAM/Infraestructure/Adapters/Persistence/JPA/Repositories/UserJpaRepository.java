package com.openmind.IAM.Infraestructure.Adapters.Persistence.JPA.Repositories;

import com.openmind.IAM.Infraestructure.Adapters.Persistence.JPA.Entites.UserJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<UserJpaEntity, Long> {
    Optional<UserJpaEntity> findByEmail(String email);
    boolean existsByEmail(String email);
}
