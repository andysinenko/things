package com.synenko.things.pdfbook.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "pdfbooks_authors", schema="things")
@AllArgsConstructor
@NoArgsConstructor
@Data@Builder
public class PdfAuthor {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pdf_authors_seq")
    @SequenceGenerator(name = "pdf_authors_seq", sequenceName = "things.pdf_authors_sequence", allocationSize = 1)
    private Long id;

    private String name;

    private String note;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted = false;
}
