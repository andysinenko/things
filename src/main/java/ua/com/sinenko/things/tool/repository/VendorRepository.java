package ua.com.sinenko.things.tool.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.com.sinenko.things.tool.entity.Vendor;

public interface VendorRepository extends JpaRepository<Vendor, Long> {
    Vendor findVendorByName(String name);
}
