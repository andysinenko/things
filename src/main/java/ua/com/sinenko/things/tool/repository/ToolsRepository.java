package ua.com.sinenko.things.tool.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.com.sinenko.things.tool.entity.Tool;

import java.util.List;

@Repository
public interface ToolsRepository extends JpaRepository<Tool, Long> {

    List<Tool> findByType(String type);

    List<Tool> findByVendor(String vendor);

    List<Tool> findByName(String name);

    List<Tool> findByDescription(String description);
}
