package com.tarus.server.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ModelRepository extends JpaRepository<Model,Long> {
    Optional<Model> findByNameIgnoreCase(String name);
}
