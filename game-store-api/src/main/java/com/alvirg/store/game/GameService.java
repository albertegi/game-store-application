package com.alvirg.store.game;

import com.alvirg.store.common.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    public PageResponse<Game> pagedResult(final int pageNumber, final int size){

        Pageable pageable = PageRequest.of(pageNumber,
                size,
                Sort.by(
                        Sort.Direction.DESC, "title")
        );

        Page<Game> pagedResult = gameRepository.findAllByCategoryName("anyCat", pageable);

        return PageResponse.<Game>builder()
                .content(pagedResult.getContent())
                .totalElements(pagedResult.getNumberOfElements())
                .totalPages(pagedResult.getTotalPages())
                .last(pagedResult.isLast())
                .first(pagedResult.isFirst())
                .build();


    }



}
