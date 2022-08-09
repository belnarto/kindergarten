package com.example.demoStage2.service;

import com.example.demoStage2.dto.Child;
import java.util.Collection;
import java.util.List;

public interface ChildService {

    long save(Child child);

    default Child get(String firstName, String lastName) {
        return null;
    }

    default Child get(Long id) {
        return null;
    }

    default boolean remove(Long id) {
        return false;
    }

    default boolean update(Long id, Child child) {
        return false;
    }

    default List<Child> searchByCategory(String category) {
        return List.of();
    }

    default List<Child> searchByName(String name) {
        return List.of();
    }

    default List<Child> searchByAge(int minAge, int maxAge) {
        return List.of();
    }

    default Collection<Child> getAllChildrenBySex(String sex) {
        return null;
    }

    default Collection<Child> getAllChildrenBySex2(String sex) {
        return null;
    }

    default Collection<Child> getAllChildrenBySex3(String sex) {
        return null;
    }
}
