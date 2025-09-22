package ua.com.sinenko.things.pdfbook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.com.sinenko.things.pdfbook.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
