package ua.com.sinenko.things.security.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.com.sinenko.things.security.model.entity.JwtTokenEntity;
import ua.com.sinenko.things.security.model.entity.ThingsUser;

import java.util.List;

@Repository
public interface JwtTokenRepository extends JpaRepository<JwtTokenEntity, Long> {

    JwtTokenEntity findByToken(String token);

    JwtTokenEntity findByUser(ThingsUser user);

    List<JwtTokenEntity> findAllValidTokenByUserAndRevokedFalseAndExpiredFalse(ThingsUser user);

}
