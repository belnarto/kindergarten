package com.example.demoStage2.repository;

import com.example.demoStage2.entity.ChildEntity;
import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ChildRepository extends JpaRepository<ChildEntity, Long> {

    List<ChildEntity> findByCategoryIgnoreCaseOrderByUpdatedAtDesc(String category);

    @Query(value="select\n"
        + "        childentit0_.id as id1_0_,\n"
        + "        childentit0_.first_name as first_na2_0_,\n"
        + "        childentit0_.last_name as last_nam3_0_,\n"
        + "        childentit0_.age as age4_0_,\n"
        + "        childentit0_.sex as sex5_0_,\n"
        + "        childentit0_.category as category6_0_,\n"
        + "        childentit0_.birthdate as birthdat7_0_,\n"
        + "        childentit0_.updated_at as updated_8_0_,\n"
        + "        contactpho1_.child_entity_id as child_en1_1_0__,\n"
        + "        contactpho1_.contact_phones as contact_2_1_0__ \n"
        + "    from\n"
        + "        child_entity childentit0_ \n"
        + "    left outer join\n"
        + "        child_entity_contact_phones contactpho1_ \n"
        + "            on childentit0_.id=contactpho1_.child_entity_id \n"
        + "    where\n"
        + "        upper(childentit0_.first_name) like upper(concat('%', ?1 , '%')) escape '\\' \n"
        + "    order by\n"
        + "        childentit0_.updated_at desc", nativeQuery = true)
    List<ChildEntity> findByPartName(String firstName);

    @EntityGraph(
        type = EntityGraphType.FETCH,
        attributePaths = {
            "contactPhones"
        }
    )
    List<ChildEntity> findByAgeGreaterThanAndAgeLessThanEqualOrderByAgeAsc(int minAge, int maxAge);
}
