# Movies

Modular Android movie app built with Kotlin, Jetpack Compose, Koin, Ktor, Coil, and The Movie Database API.

## Screenshots

| Discover | Favorites | Details |
| --- | --- | --- |
| <img src="docs/screenshots/discover.png" width="220" alt="Discover screen screenshot"> | <img src="docs/screenshots/favorites.png" width="220" alt="Favorites screen screenshot"> | <img src="docs/screenshots/details.png" width="220" alt="Movie details screen screenshot"> |

## Architecture

The app is split into small Gradle modules around presentation, domain, data, and shared design system responsibilities.

```mermaid
flowchart TB
  App["app\nApplication + Activity + Koin startup"]

  subgraph Features
    Main["feature:main\nNavigation + tabs"]
    Popular["feature:popularmovies\nPopular movies grid + paging state"]
    Favorites["feature:favorites\nFavorite movies grid"]
    Details["feature:moviedetails\nMovie details screen"]
  end

  DesignSystem["core:designsystem\nTheme + reusable movie UI"]
  Domain["domain:movies\nMovie entity + MoviesRepository contract"]

  subgraph Data
    MoviesData["data:movies\nTMDB API + repository implementation"]
    FavoritesData["data:favorites\nLocal favorites store"]
  end

  TMDB["TMDB API"]
  Storage["SharedPreferences"]

  App --> Main
  Main --> Popular
  Main --> Favorites
  Main --> Details
  Popular --> Domain
  Main --> DesignSystem
  Popular --> DesignSystem
  Favorites --> DesignSystem
  Details --> DesignSystem
  MoviesData --> Domain
  MoviesData --> TMDB
  FavoritesData --> Storage
  App --> MoviesData
  App --> FavoritesData
```

Important runtime flow:

1. `MoviesApplication` starts Koin and registers the data modules.
2. `MoviesActivity` injects `MoviesRepository` and `FavoriteMoviesStore`.
3. `MoviesHomeScreen` creates `PopularMoviesState` and routes between home/details.
4. `PopularMoviesState` requests pages through `MoviesRepository`.
5. `TmdbMoviesRepository` calls `MoviesApi`, maps DTOs into domain `Movie` entities, and returns them to Compose UI.

See [docs/architecture.md](docs/architecture.md) for the fuller Mermaid diagrams.

## Local Setup

Add a TMDB API key to `local.properties`:

```properties
TMDB_API_KEY=your_api_key_here
```

Then build the app:

```bash
./gradlew :app:assembleDebug
```
