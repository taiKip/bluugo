package com.tarus.server.model;

import com.tarus.server.carmodel.CarModel;
import com.tarus.server.entitity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Model extends BaseEntity {
   private String name;
   @OneToMany(mappedBy = "model")
   private Set<CarModel> carModels;

   public Model(String name) {
      this.name = name;
   }
}
