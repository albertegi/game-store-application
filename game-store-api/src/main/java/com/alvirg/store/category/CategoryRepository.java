package com.alvirg.store.category;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, String> {
    // find all the categories by name starting with e.g. ('ac..
    //select * from category where name like 'a%' order by name asc

    /**
     *
     * Advantages of using JPA Queries:
     * (1) abstract complex sql away
     * (2) speed of development
     * Disadvantage: Method name is too long
     */
    List<Category> findAllByNameStartingWithIgnoreCaseOOrderByNameAsc(String name);
}
