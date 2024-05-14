package ua.com.sinenko.things.security.model.entity;


import jakarta.persistence.*;

import java.io.Serializable;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Entity
@Table(name = "roles")
public class ThingsUserRoles implements Serializable {
    @Id
    @SequenceGenerator(
            name = "places_sequence",
            sequenceName = "places_sequence",
            allocationSize = 1)
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "places_sequence")
    @Column(name = "id", updatable = false)
    private Long id;


}
