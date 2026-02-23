package com.alvirg.store.category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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

    // JPQL syntax
    // advantages: query is validated at run time
    @Query(
            """
            SELECT c FROM Category c
            WHERE c.name LIKE lower(:catName)
            ORDER BY c.name ASC
            """
    )
    List<Category> findAllByName(@Param("catName") String categoryName);


    // find all categories using native queries
    @Query(value = """
                SELECT * FROM category CATEGORY_TBL
                WHERE name LIKE :catName ORDER BY name ASC
                """, nativeQuery = true)
    List<Category> findAllByNameUsingNativeQuery(@Param("catName") String categoryName);


    @Query(name = "Category.findByName")
    List<Category> findAllByNameQuery(@Param("CatName") String categoryName);


}
