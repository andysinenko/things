package com.synenko.things.security.model.service;

import com.synenko.things.security.model.dto.AuthorityDto;
import com.synenko.things.security.model.entity.Authority;
import com.synenko.things.security.model.repository.AuthorityRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthoritiesService {
    private static final Logger logger = LoggerFactory.getLogger(AuthoritiesService.class);

    private final AuthorityRepository authorityRepository;

    public List<AuthorityDto> getAuthorities() {
        boolean hasRecords = authorityRepository.count() > 0;
        if(hasRecords) {
            return authorityRepository.
                    findAllByOrderByNameAsc().stream()
                    .sorted(Comparator.comparing(Authority::getName))
                    .map(a -> {
                        return AuthorityDto.builder()
                                .id(a.getId())
                                .name(a.getName())
                                .build();
                    })
                    .toList();
        }
        return Collections.emptyList();
    }
}
