package com.synenko.things.tool.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "vendors")
public class Vendor {
    @Id
    @SequenceGenerator(
            name = "vendors_sequence",
            sequenceName = "vendors_sequence",
            allocationSize = 1)
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "vendors_sequence")
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name="name", unique = true)
    private String name;

    @OneToMany(mappedBy = "vendor")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private final List<Tool> tool = new ArrayList<>();

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted = false;
}
