package ua.com.sinenko.things.common.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@MappedSuperclass
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class StorageUnit implements Serializable {
    @Column(name = "unit_name")
    private String unitName;

    @Column(name = "note")
    private String note;

    @Column(name = "condition")
    private String condition;
}
