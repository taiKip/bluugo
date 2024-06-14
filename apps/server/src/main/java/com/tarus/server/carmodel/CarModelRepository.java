package com.tarus.server.carmodel;

import com.tarus.server.make.Make;
import com.tarus.server.model.Model;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface CarModelRepository extends JpaRepository<CarModel,Long> {
    Optional<CarModel> findByModelYearAndMakeAndModel(String year, Make make, Model model);
}
