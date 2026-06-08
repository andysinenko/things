package com.synenko.things.tool.service;

import com.synenko.things.tool.dto.VendorMapper;
import com.synenko.things.tool.dto.VendorResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.synenko.things.tool.repository.VendorRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VendorService {
    private final VendorRepository vendorRepository;

    @Transactional(readOnly = true)
    public List<VendorResponse> getAllVendors() {
        var vendors = vendorRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
        return VendorMapper.entitiesToDtos(vendors);
    }
}
