package ua.com.sinenko.things.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

import static jakarta.persistence.GenerationType.SEQUENCE;

@MappedSuperclass
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "tools")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Tool extends StorageUnit {
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

    @Column(name = "type")
    private String type;

    @Column(name = "vendor")
    private String vendor;

    @Temporal(TemporalType.DATE)
    @Column(name = "date_of_purchasing")
    private Date date_of_purchasing;

    @Column(name = "name")
    private String name;

    @Column(name = "serial_number")
    private String serialNumber;

    @Column(name = "description")
    private String description;
}
