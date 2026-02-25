package com.alvirg.store.game;

import com.alvirg.store.category.Category;
import org.springframework.stereotype.Service;

@Service
public class GameMapper {
    public Game toGame(GameRequest gameRequest) {
        return Game.builder()
                .title(gameRequest.title())
                .category(
                        Category.builder()
                                .id(gameRequest.categoryId())
                                .build()
                ) //Hibernate only requires the id not all the attributes are ignored
                .build();
    }
}
