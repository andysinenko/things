package ua.com.sinenko.things.book.entity;

import jakarta.persistence.*;
import lombok.*;
import ua.com.sinenko.things.place.entity.Place;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static jakarta.persistence.GenerationType.SEQUENCE;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "books", schema = "things")
public class Book {
    @Id
    @SequenceGenerator(
            name = "books_sequence",
            sequenceName = "books_sequence",
            schema = "things",
            allocationSize = 1)
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "books_sequence")
    @Column(name = "id", updatable = false)
    private Long id;

    @Temporal(TemporalType.DATE)
    @Column(name = "publication_year")
    private LocalDate year;

    @Column(name = "title")
    private String title;

    @Column(name = "volume_number")
    private String volumeNumber;

    @Column(name = "description")
    private String description;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "books_authors",
            schema = "things",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    private Set<Author> authors = new HashSet<>();

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "genre_id")
    private Genre genre;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "series_id")
    private Series series;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "place_id")
    private Place place;
}
