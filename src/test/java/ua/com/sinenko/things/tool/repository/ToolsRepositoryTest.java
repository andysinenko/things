package ua.com.sinenko.things.tool.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ua.com.sinenko.things.tool.entity.Tool;
import ua.com.sinenko.things.tool.entity.ToolType;
import ua.com.sinenko.things.tool.entity.Vendor;

import java.time.LocalDate;


import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@DisplayName("Tools Repository Test")
class ToolsRepositoryTest {
    private final String toolName = "Saw";
    private final String vendorName = "Makita";
    private final String toolDescription = "description";

    @Autowired
    ToolsRepository toolsRepository;

    @Autowired
    TestEntityManager entityManager;

    @BeforeEach
    void beforeEach() {
        var vendor = Vendor.builder().name(vendorName).build();
        var tool = Tool.builder()
                .name(toolName)
                .type(ToolType.CIRCULAR_SAW)
                .description(toolDescription)
                .dateOfPurchasing(LocalDate.parse("2024-01-01"))
                .serialNumber("234-123456")
                .vendor(vendor)
                .build();
        vendor = entityManager.persist(vendor);
        tool.setVendor(vendor);
        tool = entityManager.persist(tool);

        System.out.println(tool);
    }

    @Test
    void findByType() {
        var tools = toolsRepository.findByType(ToolType.CIRCULAR_SAW);

        assertTrue(!tools.isEmpty());
        assertEquals(tools.get(0).getName(), toolName);
    }

    @Test
    void findByVendor() {
        var tools = toolsRepository.findToolsByVendor_Name(vendorName);

        System.out.println(tools);

        assertTrue(!tools.isEmpty());
        assertEquals(tools.get(0).getName(), toolName);
    }

    @Test
    void findByName() {
        var tools = toolsRepository.findByName(toolName);

        assertTrue(!tools.isEmpty());
        assertEquals(tools.get(0).getName(), toolName);
    }

    @Test
    void findByDescription() {
        var tools = toolsRepository.findByDescription(toolDescription);

        assertTrue(!tools.isEmpty());
        assertEquals(tools.get(0).getName(), toolName);
        assertEquals(tools.get(0).getDescription(), toolDescription);
    }
}