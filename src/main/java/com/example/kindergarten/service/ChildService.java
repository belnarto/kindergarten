package com.example.kindergarten.service;

import com.example.kindergarten.dto.ChildDto;
import java.util.Collection;
import java.util.List;

@SuppressWarnings("unused")
public interface ChildService {

    long save(ChildDto childDto);

    default ChildDto get(String firstName, String lastName) {
        return null;
    }

    default ChildDto get(Long id) {
        return null;
    }

    default boolean remove(Long id) {
        return false;
    }

    default boolean update(Long id, ChildDto childDto) {
        return false;
    }

    default List<ChildDto> searchByCategory(String category) {
        return List.of();
    }

    default List<ChildDto> searchByName(String name) {
        return List.of();
    }

    default List<ChildDto> searchByAge(int minAge, int maxAge) {
        return List.of();
    }

    default Collection<ChildDto> getAllChildrenBySex(String sex) {
        return null;
    }

    default Collection<ChildDto> getAllChildrenBySex2(String sex) {
        return null;
    }

    default Collection<ChildDto> getAllChildrenBySex3(String sex) {
        return null;
    }
}
