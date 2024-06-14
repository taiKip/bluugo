package com.tarus.server.make;

import com.tarus.server.carmodel.CarModel;
import com.tarus.server.entitity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.Set;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Make extends BaseEntity {
    @Column(unique = true)
    private String name;
    @OneToMany(mappedBy = "make")
    private Set<CarModel> carModels;

    public Make(String name) {
        this.name = name;
    }
}
