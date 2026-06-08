package com.synenko.things.pdfbook.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "pdfbooks_categories", schema="things")
@AllArgsConstructor
@NoArgsConstructor
@Data@Builder
public class Category {
    @Id
    private Long id;

    private String name;
}
