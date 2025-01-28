package ua.com.sinenko.things.book.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

import static jakarta.persistence.GenerationType.SEQUENCE;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "series", schema="things")
public class Series {
    @Id
    @SequenceGenerator(
            name = "series_sequence",
            sequenceName = "series_sequence",
            schema = "things",
            allocationSize = 1)
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "series_sequence")
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "name")
    private String name;

    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "series", cascade = CascadeType.ALL)
    private Set<Book> books = new HashSet<>();
}
