package ua.com.sinenko.things.security.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.com.sinenko.things.security.model.entity.Authority;

import java.util.List;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {
    Authority findByName(String name);

    List<Authority> findAllByNameIn(List<String> names);
}
