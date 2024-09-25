package ua.com.sinenko.things.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.com.sinenko.things.model.entity.Tool;

public interface ToolsRepository extends JpaRepository<Tool, Long> {
    
}
