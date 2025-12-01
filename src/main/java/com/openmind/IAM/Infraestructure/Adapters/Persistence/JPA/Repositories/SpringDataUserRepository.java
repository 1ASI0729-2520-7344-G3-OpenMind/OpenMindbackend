package com.openmind.IAM.Infraestructure.Adapters.Persistence.JPA.Repositories;

import com.openmind.IAM.Domain.Model.Aggregates.User;
import com.openmind.IAM.Domain.Model.ValueObjects.Email;
import com.openmind.IAM.Domain.Model.ValueObjects.HashedPassword;
import com.openmind.IAM.Domain.Repositories.UserRepository;
import com.openmind.IAM.Infraestructure.Adapters.Persistence.JPA.Entites.UserJpaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class SpringDataUserRepository implements UserRepository {
    private final UserJpaRepository jpaRepository;

    @Override
    public void save(User user) {
        UserJpaEntity entity = new UserJpaEntity();
        entity.setEmail(user.getEmail().value());
        entity.setPassword(user.getPassword().value());
        entity.setName(user.getName());
        jpaRepository.save(entity);
    }

    @Override
    public Optional<User> findByEmail(Email email) {
        return jpaRepository.findByEmail(email.value()).map(this::toDomain);
    }

    @Override
    public boolean existsByEmail(Email email) {
        return jpaRepository.existsByEmail(email.value());
    }

    private User toDomain(UserJpaEntity e){
        return User.builder().id(e.getId()).email(e.getEmail()).hashedPassword(e.getPassword()).name(e.getName()).build();
    }
}
