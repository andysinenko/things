package ua.com.sinenko.things.tool.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ua.com.sinenko.things.tool.entity.Vendor;
import ua.com.sinenko.things.tool.repository.VendorRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VendorService {
    private final VendorRepository vendorRepository;

    public List<Vendor> getAllVendors() {
        return vendorRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
    }
}
