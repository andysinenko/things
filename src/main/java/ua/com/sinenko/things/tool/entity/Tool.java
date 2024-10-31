package ua.com.sinenko.things.tool.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tools", schema="things")
@Entity
public class Tool {
    @Id
    @SequenceGenerator(
            name = "tools_sequence",
            sequenceName = "tools_sequence",
            schema = "things",
            allocationSize = 1)
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "tools_sequence")
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "tool_type")
    @Enumerated(EnumType.STRING)
    private ToolType type;

    @JoinColumn(name = "vendor")
    @ManyToOne(fetch = FetchType.EAGER)
    private Vendor vendor;

    @Temporal(TemporalType.DATE)
    @Column(name = "date_of_purchasing")
    private Date dateOfPurchasing;

    @Column(name = "name")
    private String name;

    @Column(name = "serial_number")
    private String serialNumber;

    @Column(name = "description")
    private String description;
}

