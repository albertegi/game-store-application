package com.alvirg.store.category;

import com.alvirg.store.common.BaseEntity;
import com.alvirg.store.game.Game;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Category extends BaseEntity {

    private String name;
    private String description;

    @OneToMany(mappedBy = "category")
    private List<Game> games;
}
