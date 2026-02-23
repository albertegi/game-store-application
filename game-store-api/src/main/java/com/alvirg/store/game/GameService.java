package com.alvirg.store.game;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GameService {
    private final GameRepository gameRepository;

    public void findCategoryById(String categoryId){
        var games = gameRepository.findAllByCategoryId(categoryId);
    }

    public void transformTitle(){
        gameRepository.transformGamesTitleToUpperCase();
    }



}
