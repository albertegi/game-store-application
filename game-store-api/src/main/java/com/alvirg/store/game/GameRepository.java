package com.alvirg.store.game;

import com.alvirg.store.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GameRepository extends JpaRepository<Game, String> {
    /**
     *
     * Finding all the games belonging to a given category
     *  2 methods:
     *  (1) We can get the Category itself by id and from there get all the games
     *  since the Category has a list of games
     *  (2) from the games table. find the games where category_id = ...
     *
     */

    // find all games by category (v1)
    // you need to create an object of Category in CategoryService and set the
    // id and pass the object to the calling method.
    // spring works with ids not Objects
    List<Game> findAllByCategory(Category category);

    // find all games by category (v2)
    List<Game> findAllByCategoryId(String CategoryId);

    // find all the games where the name equals 'Actions' in the Category table
    /*
        select g.* from category c
        inner join game g on g.category_id = c.id
        where c.name = 'Action'

     */
    List<Game> findAllByCategoryName(String name);

    // find all games by category using jpql syntax
    @Query(
            """
            SELECT g FROM Game g
            INNER JOIN Category c ON g.category.id = c.id
            WHERE c.name LIKE :catName
            """
    )
    List<Game> findAllByCat(@Param("catName") String catName);
}
