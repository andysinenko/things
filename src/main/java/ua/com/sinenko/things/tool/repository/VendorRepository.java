package ua.com.sinenko.things.tool.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.com.sinenko.things.tool.entity.Vendor;

@Repository
public interface VendorRepository extends JpaRepository<Vendor, Long> {
    Vendor findVendorByName(String name);
}
