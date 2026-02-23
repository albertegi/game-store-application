package com.alvirg.store.category;

import com.alvirg.store.common.BaseEntity;
import com.alvirg.store.game.Game;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.NamedQuery;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "CATEGORY_TBL")
@NamedQuery(name = "Category.namedQueryFindByName",
        query = "SELECT c FROM Category c " +
                "WHERE c.name LIKE lower(:catName)" +
                "ORDER BY c.name ASC")
// advantages of NameQuery: (1) improve readability and maintainability of the code
// (2) Reusability: used across multiple methods (3) promoting the DRY principle
// (3) performance optimization - NameQueries are parsed and compiled during the application start up which can improve performance as the query preparation phase (fetching the data) is completed ahead of run time
// (4) decoupling query logic: separating query definition from application logic promotes a cleaner architecture by isolating query logic into entities
// (5) Error detection at start up: since they are validated when the persistence context is initialized, the syntax errors in queries can be detected early to avoid run time issues.
// (6) support for parameterized queries: support parameters reducing the risk of sql injection and simplifying query execution with dynamic inputs
public class Category extends BaseEntity {

    private String name;
    private String description;

    @OneToMany(mappedBy = "category")
    private List<Game> games;
}



