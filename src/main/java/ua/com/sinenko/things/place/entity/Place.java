package ua.com.sinenko.things.place.entity;

import jakarta.persistence.*;
import lombok.*;
import ua.com.sinenko.things.book.entity.Book;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Entity
@Table(name = "places", schema="things")
@Getter@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Place {
    @Id
    @SequenceGenerator(
            name = "places_sequence",
            sequenceName = "places_sequence",
            schema = "things",
            allocationSize = 1)
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "places_sequence")
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "parent_id")
    private Place parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    private List<Place> children = new ArrayList<>();

    @Column(name = "description")
    private String description;

    @Column(name = "level")
    private Long level; // 1 - flat, house etc., 2 - room, premices, 3 - shelf

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "place", cascade = CascadeType.ALL)
    private Set<Book> books = new HashSet<>();

}
