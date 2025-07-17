package ua.com.sinenko.things.security.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.util.List;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Entity
@Table(name = "authorities", schema="things")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Authority implements Serializable {
    @Id
    @SequenceGenerator(
            name = "authority_sequence",
            sequenceName = "authority_sequence",
            schema = "things",
            allocationSize = 1)
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "authority_sequence")
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "name", updatable = false)
    private String name;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"),
            schema = "things"
    )
    private List<ThingsUser> thingsUser;
}
