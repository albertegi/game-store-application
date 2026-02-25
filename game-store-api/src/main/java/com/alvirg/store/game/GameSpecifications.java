package com.alvirg.store.game;

import org.springframework.data.jpa.domain.Specification;

public class GameSpecifications {

    // the specification is just a set or list of filters
    public static Specification<Game> byTitle(String gameTitle){
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("title"), gameTitle));
    }

    public static Specification<Game> bySupportedPlatforms(SupportedPlatforms platforms){
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("platform"), platforms));
    }

    public static Specification<Game> byCategoryName(String categoryName){
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("category").get("name"), categoryName));
    }

}
