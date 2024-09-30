package ua.com.sinenko.things.book.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.SEQUENCE;

@MappedSuperclass
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "authors")
public class Author {
    @Id
    @SequenceGenerator(
            name = "author_sequence",
            sequenceName = "author_sequence",
            allocationSize = 1)
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "author_sequence")
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "author_name")
    private String authorName;

    @ManyToMany(mappedBy = "authors")
    private Book book;
}
