package ua.com.sinenko.things.tool.entity;

import jakarta.persistence.*;
import lombok.*;
import ua.com.sinenko.things.place.entity.Place;

import java.time.LocalDate;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tools")
@Entity
public class Tool {
    @Id
    @SequenceGenerator(
            name = "tools_sequence",
            sequenceName = "tools_sequence",
            allocationSize = 1)
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "tools_sequence")
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "tool_type")
    @Enumerated(EnumType.STRING)
    private ToolType type;

    @Column(name = "serial_number")
    private String serialNumber;

    @JoinColumn(name = "vendor")
    @ManyToOne(fetch = FetchType.EAGER)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Vendor vendor;

    @JoinColumn(name = "place")
    @ManyToOne(fetch = FetchType.EAGER)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Place place;

    @Temporal(TemporalType.DATE)
    @Column(name = "date_of_purchasing")
    private LocalDate dateOfPurchasing;

    @Column(name = "description")
    private String description;
}

