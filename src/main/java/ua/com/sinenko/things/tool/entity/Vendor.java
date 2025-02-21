package ua.com.sinenko.things.tool.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "vendors", schema="things")
public class Vendor {
    @Id
    @SequenceGenerator(
            name = "vendors_sequence",
            sequenceName = "vendors_sequence",
            schema = "things",
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
    private List<Tool> tool;

}
