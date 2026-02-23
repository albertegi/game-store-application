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
@NamedQuery(name = "Category.findByName",
        query = "SELECT c FROM Category c " +
                "WHERE c.name LIKE lower(:catName)" +
                "ORDER BY c.name ASC")
public class Category extends BaseEntity {

    private String name;
    private String description;

    @OneToMany(mappedBy = "category")
    private List<Game> games;
}
