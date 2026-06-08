package com.synenko.things.pdfbook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.synenko.things.pdfbook.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
