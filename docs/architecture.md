# Architecture

## System Overview

```mermaid
flowchart TB
  subgraph App
    AppModule["app\nMoviesApplication + MoviesActivity"]
  end

  subgraph Presentation
    MainFeature["feature:main\nMoviesHomeScreen + navigation"]
    PopularFeature["feature:popularmovies\nPopularMoviesScreen + PopularMoviesState"]
    FavoritesFeature["feature:favorites\nFavoritesScreen"]
    DetailsFeature["feature:moviedetails\nMovieDetailsScreen"]
    DesignSystem["core:designsystem\nTheme + MovieGridCell + layout tokens"]
  end

  subgraph Domain
    DomainMovies["domain:movies\nMovie entity + MoviesRepository"]
  end

  subgraph Data
    MoviesData["data:movies\nMoviesApi + TmdbMoviesRepository + DTO mapper"]
    FavoritesData["data:favorites\nFavoriteMoviesStore"]
  end

  subgraph Infrastructure
    Koin["Koin\nDependency injection"]
    Ktor["Ktor HttpClient\nAndroid engine"]
    TMDB["TMDB API"]
    LocalStorage["SharedPreferences"]
  end

  AppModule --> MainFeature
  MainFeature --> PopularFeature
  MainFeature --> FavoritesFeature
  MainFeature --> DetailsFeature

  MainFeature --> DesignSystem
  PopularFeature --> DesignSystem
  FavoritesFeature --> DesignSystem
  DetailsFeature --> DesignSystem

  AppModule --> Koin
  Koin --> MoviesData
  Koin --> FavoritesData
  AppModule --> DomainMovies

  PopularFeature --> DomainMovies
  MoviesData --> DomainMovies
  MoviesData --> Ktor
  Ktor --> TMDB
  FavoritesData --> LocalStorage
```

## Module Responsibilities

```mermaid
flowchart LR
  App["app\nComposition root"]
  Feature["feature:*\nScreens + screen state"]
  Core["core:designsystem\nReusable UI"]
  Domain["domain:movies\nBusiness contract"]
  Data["data:*\nNetwork/local implementations"]

  App --> Feature
  App --> Data
  Feature --> Core
  Feature --> Domain
  Data --> Domain
```

## Dependency Wiring

```mermaid
flowchart TB
  Start["MoviesApplication.onCreate()"] --> KoinStart["startKoin"]
  KoinStart --> AndroidContext["androidContext(this)"]
  KoinStart --> Modules["moviesDataModule + favoritesDataModule"]

  Modules --> HttpClient["HttpClient(Android)"]
  Modules --> MoviesApi["MoviesApi(client)"]
  Modules --> MoviesRepo["MoviesRepository\nTmdbMoviesRepository(api)"]
  Modules --> FavoritesStore["FavoriteMoviesStore(androidContext)"]

  Activity["MoviesActivity"] --> InjectRepo["by inject<MoviesRepository>()"]
  Activity --> InjectFavorites["by inject<FavoriteMoviesStore>()"]

  InjectRepo --> MoviesRepo
  InjectFavorites --> FavoritesStore
  MoviesRepo --> MoviesApi
  MoviesApi --> HttpClient
```

## Popular Movies Data Flow

```mermaid
sequenceDiagram
  actor User
  participant Screen as PopularMoviesScreen
  participant State as PopularMoviesState
  participant Repo as MoviesRepository
  participant Impl as TmdbMoviesRepository
  participant Api as MoviesApi
  participant Client as Ktor HttpClient
  participant TMDB as TMDB API

  User->>Screen: Open Discover tab
  Screen->>State: loadInitialPage()
  State->>Repo: getPopularMovies(page = 1)
  Repo->>Impl: interface dispatch
  Impl->>Api: getPopularMovies(page)
  Api->>Client: GET /3/movie/popular
  Client->>TMDB: HTTPS request
  TMDB-->>Client: JSON response
  Client-->>Api: bodyAsText()
  Api-->>Impl: List<MovieDto>
  Impl-->>State: List<Movie>
  State-->>Screen: PopularMoviesUiState.Loaded
```

## Pagination Flow

```mermaid
sequenceDiagram
  participant Grid as LazyVerticalGrid
  participant Flow as snapshotFlow(layoutInfo)
  participant State as PopularMoviesState
  participant Repo as MoviesRepository

  Grid->>Flow: visible item index changes
  Flow->>State: loadNextPage()
  State->>State: ignore if already loading or no more pages
  State->>Repo: getPopularMovies(nextPage)
  Repo-->>State: next page movies
  State->>State: append distinct movies by id
```

## Favorites Flow

```mermaid
sequenceDiagram
  actor User
  participant Cell as MovieGridCell
  participant Activity as MoviesActivity
  participant Store as FavoriteMoviesStore
  participant UI as Compose state

  User->>Cell: Tap favorite
  Cell->>Activity: onFavoriteClick(movie)
  Activity->>Store: toggleMovie(movie)
  Store->>Store: read and write SharedPreferences JSON
  Store-->>Activity: updated favorites
  Activity->>UI: favoriteMovies = updated list
```

## Data Mapping

```mermaid
flowchart LR
  Json["TMDB JSON results"] --> MovieDto["MovieDto"]
  MovieDto --> Mapper["MovieDto.toMovie()"]
  Mapper --> Movie["Movie domain entity"]
  Movie --> Grid["Popular/Favorites grid"]
  Movie --> Details["Details screen"]
```

## UI State Lifecycle

```mermaid
stateDiagram-v2
  [*] --> Loading
  Loading --> Loaded : first page success
  Loading --> Error : first page failure
  Loaded --> Loaded : next page success
  Loaded --> Loaded : favorite toggled
  Error --> Loading : retry/recreate state
```
