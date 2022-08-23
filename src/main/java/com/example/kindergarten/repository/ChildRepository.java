package com.example.kindergarten.repository;

import com.example.kindergarten.entity.ChildEntity;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChildRepository extends JpaRepository<ChildEntity, Long> {

    List<ChildEntity> findByCategoryIgnoreCaseOrderByUpdatedAtDesc(String category);

    List<ChildEntity> findByFirstNameContainingIgnoreCaseOrderByUpdatedAtDesc(String firstName);

    @EntityGraph(
            type = EntityGraphType.FETCH,
            attributePaths = {
                    "contactPhones"
            }
    )
    List<ChildEntity> findByAgeGreaterThanAndAgeLessThanEqualOrderByAgeAsc(int minAge, int maxAge);
}
