package ua.com.sinenko.things.book.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.com.sinenko.things.common.entity.StorageUnit;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static jakarta.persistence.GenerationType.SEQUENCE;

@MappedSuperclass
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "books")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Book extends StorageUnit {
    @Id
    @SequenceGenerator(
            name = "book_sequence",
            sequenceName = "book_sequence",
            allocationSize = 1)
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "book_sequence")
    @Column(name = "id", updatable = false)
    private Long id;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "books_authors",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    private Set<Author> authors = new HashSet<>();

    @Column(name = "genre")
    private String genre;

    @Column(name = "publisher")
    private String publisher;

    @Temporal(TemporalType.DATE)
    @Column(name = "year")
    private Date year;

    @Column(name = "name")
    private String name;

    @Column(name = "volume_number")
    private String volumeNumber;

    @Column(name = "series")
    private String series; //todo: convert to @ManyToOne -> BookSeries entity

    @Column(name = "description")
    private String description;
}
