package com.alvirg.store.game;

import com.alvirg.store.common.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

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

    // example for pagination
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

    // example for using QueryByExample
    public void queryByExampleCaseSensitive(){
        // find a specific game by title, assuming the title is unique in database

        // create an object of Game: which may be the probe
        Game game = new Game();
        game.setTitle("The witcher III"); // It automatically handles case sensitivity: So in case in my DB --> the witcher iii, it will not return anything
        game.setSupportedPlatforms(SupportedPlatforms.PS);

        // create the example of type Game
        Example<Game> example = Example.of(game);

        Optional<Game> gameTitle = gameRepository.findOne(example);
    }
     // example for using QueryByExample
    public void queryByExampleCaseInSensitive(){
        // find a specific game by title, assuming the title is unique in database

        // create an object of Game: which may be the probe
        Game game = new Game();
        game.setTitle("The witcher III"); // It automatically handles case sensitivity: So in case in my DB --> the witcher iii, it will not return anything
        game.setSupportedPlatforms(SupportedPlatforms.PS);

        ExampleMatcher matcher = ExampleMatcher.matchingAll().withIgnoreCase();

        // create the example of type Game
        Example<Game> example = Example.of(game, matcher);

        Optional<Game> gameTitle = gameRepository.findOne(example);
    }public void queryByExampleCustomMatching(){
        // find a specific game by title, assuming the title is unique in database

        // create an object of Game: which may be the probe
        Game game = new Game();
        game.setTitle("witcher"); // all games title containing witcher
        game.setSupportedPlatforms(SupportedPlatforms.PS); // ignoring case for this

        ExampleMatcher matcher = ExampleMatcher.matchingAny()
                .withMatcher("title", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                .withMatcher("supportedPlatforms", ExampleMatcher.GenericPropertyMatchers.exact());

        // create the example of type Game
        Example<Game> example = Example.of(game, matcher);
        /*
            The output query
            select * from game
            where lower(title) like '%witcher%'
            and supportedPlatforms = 'PS'
         */

        List<Game> gameTitle = gameRepository.findAll(example);
    }

    public void queryByExampleIgnoringProperties(){
        Game game = new Game();
        game.setTitle("The witcher III"); // filter everything, but ignoring some fields

        ExampleMatcher matcher = ExampleMatcher.matchingAny()
                .withMatcher("title", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                .withIgnorePaths("supportedPlatforms", "coverPicture");

        Example<Game> example = Example.of(game, matcher);
        List<Game> gameTitle = gameRepository.findAll(example);

        /*
            Limitations of query by Example Executor
            (1) Nesting and grouping statements are not supported. e.g.
            --> select * from game where (title = ?0 and supportedPlatforms = ?1) OR coverPicture is not null
            (2) String matching only includes exact, case-sensitive, starts, ends, contains and regex
            (3) All types other than String are exact-match only

         */
    }

    public void specificationExample1(){
        Specification<Game> spec = buildSpecificationWithAndOperator("witcher", SupportedPlatforms.PC);

        List<Game> games = gameRepository.findAll(spec);
    }
    public void specificationExample2(){
        Specification<Game> spec = buildSpecificationWithOrOperator("witcher", SupportedPlatforms.PC);

        List<Game> games = gameRepository.findAll(spec);
    }

    private Specification<Game> buildSpecificationWithAndOperator(String title, SupportedPlatforms platforms) {
        Specification<Game> spec = Specification.unrestricted();

        if (StringUtils.hasLength(title)) {
            spec = spec.and(GameSpecifications.byTitle(title));
        }

        if (platforms != null) {
            spec = spec.and(GameSpecifications.bySupportedPlatforms(platforms));
        }

        return spec;
    }

    private Specification<Game> buildSpecificationWithOrOperator(String title, SupportedPlatforms platforms) {
        Specification<Game> spec = Specification.unrestricted();

        if (StringUtils.hasLength(title)) {
            spec = spec.or(GameSpecifications.byTitle(title));
        }

        if (platforms != null) {
            spec = spec.or(GameSpecifications.bySupportedPlatforms(platforms));
        }

        return spec;
    }

    // Usually we would have been done but Projections solved the problem.
    // 1 --> class (GameRepresentation) (id, title, platforms)
    // 2 --> call the game repository and fetch all the games (paged)
    // 3 --> map the result (loop over the result from the DB, do the mapping, collect, return the result)

    // Solution: Create an interface (GameRepresentation)
    // create a method that will return a list of Game
    //

    public List<GameRepresentation1> getGameWithRep1(){
        return gameRepository.findAllGames();
    }

    public List<GameRepresentation2> getGameWithRep2(){
        return gameRepository.findAllGames2();
    }

}
