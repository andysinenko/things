package com.synenko.things.book.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.GenerationType.SEQUENCE;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "authors", schema="things")
public class Author implements Serializable {
    @Id
    @SequenceGenerator(
            name = "author_sequence",
            sequenceName = "author_sequence",
            schema = "things",
            allocationSize = 1)
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "author_sequence")
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "note")
    private String note;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToMany(mappedBy = "authors")
    private final List<Book> books = new ArrayList<>();

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted = false;
}
