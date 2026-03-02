package com.alvirg.store.game;

import com.alvirg.store.category.CategoryRepository;
import com.alvirg.store.common.BaseEntity;
import com.alvirg.store.common.PageResponse;
import com.alvirg.store.platform.Console;
import com.alvirg.store.platform.Platform;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.http.client.ClientHttpRequestFactoryBuilder;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class GameService {
    private final GameRepository gameRepository;
    private final PlatformRepository platformRepository;
    private final CategoryRepository categoryRepository;
    private final GameMapper gameMapper;
    private final ClientHttpRequestFactoryBuilder clientHttpRequestFactoryBuilder;

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
                .isLast(pagedResult.isLast())
                .isFirst(pagedResult.isFirst())
                .build();
    }

    // example for using QueryByExample
    public void queryByExampleCaseSensitive(){
        // find a specific game by title, assuming the title is unique in database

        // create an object of Game: which may be the probe
        Game game = new Game();
        game.setTitle("The witcher III"); // It automatically handles case sensitivity: So in case in my DB --> the witcher iii, it will not return anything
//        game.setSupportedPlatforms(Console.PS);

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
//        game.setSupportedPlatforms(Console.PS);

        ExampleMatcher matcher = ExampleMatcher.matchingAll().withIgnoreCase();

        // create the example of type Game
        Example<Game> example = Example.of(game, matcher);

        Optional<Game> gameTitle = gameRepository.findOne(example);
    }public void queryByExampleCustomMatching(){
        // find a specific game by title, assuming the title is unique in database

        // create an object of Game: which may be the probe
        Game game = new Game();
        game.setTitle("witcher"); // all games title containing witcher
//        game.setSupportedPlatforms(Console.PS); // ignoring case for this

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
        Specification<Game> spec = buildSpecificationWithAndOperator("witcher", Console.PC);

        List<Game> games = gameRepository.findAll(spec);
    }
    public void specificationExample2(){
        Specification<Game> spec = buildSpecificationWithOrOperator("witcher", Console.PC);

        List<Game> games = gameRepository.findAll(spec);
    }

    private Specification<Game> buildSpecificationWithAndOperator(String title, Console platforms) {
        Specification<Game> spec = Specification.unrestricted();

        if (StringUtils.hasLength(title)) {
            spec = spec.and(GameSpecifications.byTitle(title));
        }

        if (platforms != null) {
            spec = spec.and(GameSpecifications.bySupportedPlatforms(platforms));
        }

        return spec;
    }

    private Specification<Game> buildSpecificationWithOrOperator(String title, Console platforms) {
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




    public String saveGame(final GameRequest gameRequest){


        //Two Options for doing this (fetching)
        // Option 1: --> Loop over the set of platforms and fetch them one by one from the DB
        // e.g. select * from platform where console = 'PS'
        // Option 2: --> mapping the platforms in the (request) to Platform (entity) and fetch all of them at once
        // e.g. select * from platform where console in ('PS', 'XBOX')
        // Option 2: --> is a better option
        final List<Console> selectedConsoles = gameRequest.platforms()
                .stream()
                .map(p-> Console.valueOf(p))
                .collect(Collectors.toList());

        final List<Platform> platforms = platformRepository.findAllByConsoleIn(selectedConsoles);

        // platform (gameReq) --> PS, XBOX, ABC
        // platform (DB) --> PS, XBOX

        if(platforms.size() !=selectedConsoles.size()){
            log.info("Received a non supported platforms. Received: {} - Stored: {}", platforms, selectedConsoles);
            // todo dedicated exp
            throw new RuntimeException("One or more platforms are not supported");
        }


        // check if title exists
        if (!gameRepository.existsByTitle(gameRequest.title())) {
            log.info("Game already exists: {} ", gameRequest.title());
            throw new RuntimeException("Game already exists");
        }

        // check if category exists
        if(!categoryRepository.existsById(gameRequest.categoryId())){
            log.info("Received a category that does not exist: {} ", gameRequest.categoryId());
            // todo create a dedicated exp
            throw new RuntimeException("Category does not exist");
        }

        final Game game = gameMapper.toGame(gameRequest);
        game.setPlatforms(platforms);
        final Game savedGame = gameRepository.save(game);

        // todo do we need to assign the game to the selectedPlatforms? else leave it like this
        return savedGame.getId();
    }

    public void updateGame(String gameId, GameRequest gameRequest){
        // check if the game exist by id
        final Game game = gameRepository.findById(gameId)
                .orElseThrow(()-> new RuntimeException("Game not found"));

        // check if title exists
        // we will do a double check in the updateGame method:
        // check that the game tile from the request is different from the one stored in the database
        if (!game.getTitle().equals(gameRequest.title()) && gameRepository.existsByTitle(gameRequest.title())) {
            log.info("Game already exists: {} ", gameRequest.title());
            throw new RuntimeException("Game already exists");
        }

        final List<Console> selectedConsoles = gameRequest.platforms()
                .stream()
                .map(p-> Console.valueOf(p))
                .collect(Collectors.toList());

        final List<Platform> platforms = platformRepository.findAllByConsoleIn(selectedConsoles);

        if(platforms.size() !=selectedConsoles.size()){
            log.info("Received a non supported platforms. Received: {} - Stored: {}", platforms, selectedConsoles);
            // todo dedicated exp
            throw new RuntimeException("One or more platforms are not supported");
        }

        final List<String> platformIds = platforms
                .stream()
                .map(Platform::getId)
                .toList();


        List<Platform> currentPlatforms = game.getPlatforms();

        List<Platform> newPlatforms = platformRepository.findAllById(platformIds); // get the id from gameRequest but id is not in gameRequest we only have the list of platforms. we need to get the list of platforms and from there get the different ids

        List<Platform> platformsToAdd = new ArrayList<>(newPlatforms);
        platformsToAdd.removeAll(currentPlatforms);

        List<Platform> platformsToRemove = new ArrayList<>(currentPlatforms);
        platformsToRemove.removeAll(newPlatforms);

        for(Platform platform: platformsToAdd){
            game.addPlatform(platform);
        }


        for(Platform platform : platformsToRemove){
            game.removePlatform(platform);
        }

        game.setTitle(gameRequest.title());
        gameRepository.save(game);



    }

    public String uploadGameImage(MultipartFile file,  String gameId){
        return null;
    }

    // the result should be paginated

    public PageResponse<GameResponse> findAllGames(int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        Page<Game> gamesPage = gameRepository.findAll(pageable);
        List<GameResponse> gameResponses = gamesPage
                .stream()
                .map(this.gameMapper::toGameResponse)
                .toList();
        return PageResponse.<GameResponse>builder()
                .content(gameResponses)
                .pageNumber(gamesPage.getNumber())
                .size(gamesPage.getSize())
                .totalElements(gamesPage.getNumberOfElements())
                .totalPages(gamesPage.getTotalPages())
                .isLast(gamesPage.isLast())
                .isFirst(gamesPage.isFirst())
                .build();
    }

    public void deleteGame(String gameId){
        return;
    }

    // log aggregator

}
