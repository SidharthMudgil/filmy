# Index
1. [Overview](#overview)
2. [Tech Details](#tech-details)
3. [Sequence Diagrams](#sequence-diagrams)

# Overview

### Screenshots
| ![Screenshot 1](assets/img1.png) | ![Screenshot 2](assets/img2.png) | ![Screenshot 3](assets/img3.png) | ![Screenshot 4](assets/img4.png) |
| --- | --- | --- | --- |

### Demo Recording
https://github.com/user-attachments/assets/db8bd383-7edf-4293-9401-1c0c0bac9255


[Download Apk](assets/app-debug.apk)
[Download Recording](assets/recording.mp4)

---

# Tech Details

### Tech Stack
* Kotlin 2.x
* Jetpack Compose
* Navigation Compose
* Hilt
* Retrofit 3
* Room
* Coil
* Clean Architecture
* Multi-module Gradle setup

### Architecture

```
app
├── core:model
├── core:network
├── core:local
├── core:navigation
├── core:ui
├── feature:search
└── feature:movie_details
```

```mermaid
graph TD
    subgraph Core Modules
        core_model["model"]
        core_network["network"]
        core_local["local"]
        core_navigation["navigation"]
        core_ui["ui"]
    end

    subgraph Features
        feature_search["search"]
        feature_movie_details["movie_details"]
    end

    app["app"] --> Core_Modules
    app --> Features

    feature_search --> core_model
    feature_search --> core_network
    feature_search --> core_ui

    feature_movie_details --> core_model
    feature_movie_details --> core_network
    feature_movie_details --> core_ui

```

Each module has a single responsibility and communicates via abstractions, ensuring scalability and testability.

The app follows Clean Architecture with feature-based modularization:

* Presentation → Jetpack Compose + ViewModel
* Domain → UseCases + Repository interfaces
* Data → Repository implementations, mappers
* **Core modules**
  - `core:network` → Remote API access
  - `core:local` → Local cache & persistence
  - `core:model`, `core:ui`, `core:navigation`

---

---

### Navigation Graph

```mermaid
flowchart TD
    MainActivity --> NavHost
    NavHost --> SearchScreen
    NavHost --> MovieDetailsScreen
```

### State Management

```mermaid
flowchart LR
    Intent --> ViewModel
    ViewModel --> UiState
    ViewModel --> Effect
```

---

### Network Layer

```mermaid
flowchart LR
    ApiService --> RemoteDataSource
    RemoteDataSource --> Repostitory
```

---

### Local Layer

```mermaid
flowchart LR
    AppDatabase --> MovieDao
    MovieDao --> LocalDataSource
    LocalDataSource --> Repository
```

---

### Dependency Injection

```mermaid
flowchart TD
    Hilt --> ViewModel
    Hilt --> UseCase
    Hilt --> Repository
    Hilt --> RemoteDataSource
    Hilt --> ApiService
```

---

# Sequence Diagrams

### Trending Movies
```mermaid
sequenceDiagram
participant UI as SearchScreen
participant VM as SearchViewModel
participant UC as GetTrendingMoviesUseCase
participant Repo as SearchRepositoryImpl
participant Local as LocalDataSource
participant Remote as RemoteDataSource
participant API as ApiService

UI->>VM: SearchIntent.Search(query="")
VM->>UI: emit Empty
VM->>UC: getTrendingMovies()
UC->>Repo: getTrendingMovies()
Repo->>UI: emit Loading

Repo->>Local: getTrendingMovies()
alt Cache hit
    Local-->>Repo: List<SearchItem>
    Repo-->>UC: emit Success(cached)
end

Repo->>Remote: fetchTrendingMovies()
Remote->>API: GET /trending/movie
API-->>Remote: MovieListResponse
Remote-->>Repo: Response
Repo->>Local: saveSearchResults(response)
Repo-->>UC: emit Success(fresh)

alt Network error
    Local-->>Repo: cached data (if available)
    Repo-->>UC: emit cached OR Error
end

UC-->>VM: ResultState
VM-->>UI: SearchUiState
```
---

### Search Movies
```mermaid
sequenceDiagram
participant UI as SearchScreen
participant VM as SearchViewModel
participant UC as SearchMovieUseCase
participant Repo as SearchRepositoryImpl
participant Local as LocalDataSource
participant Remote as RemoteDataSource
participant API as ApiService

UI->>VM: SearchIntent.Search(query="movie name")
VM->>UI: emit Empty
VM->>UC: searchMovie(query)
UC->>Repo: searchMovie(query)
Repo->>UI: emit Loading

Repo->>Local: searchMovies(query)
alt Cache hit
    Local-->>Repo: List<SearchItem>
    Repo-->>UC: emit Success(cached)
end

Repo->>Remote: searchMovie(query)
Remote->>API: GET /search/movie
API-->>Remote: MovieListResponse
Remote-->>Repo: Response
Repo->>Local: saveSearchResults(response)
Repo-->>UC: emit Success(fresh)

alt Network error
    Local-->>Repo: cached data (if available)
    Repo-->>UC: emit cached OR Error
end

UC-->>VM: ResultState
VM-->>UI: SearchUiState


```

### Movie Details

```mermaid
sequenceDiagram
participant UI as MovieDetailsScreen
participant VM as MovieDetailsViewModel
participant UC as GetMovieDetailsUseCase
participant Repo as MovieRepositoryImpl
participant Local as LocalDataSource
participant Remote as RemoteDataSource
participant API as ApiService

UI->>VM: MovieDetailsIntent.Load(movieId)
VM->>UI: emit Empty
UC->>Repo: getMovieDetails(movieId)
Repo->>UI: emit Loading

Repo->>Local: getMovieDetails(movieId)
alt Cache hit
    Local-->>Repo: Movie
    Repo-->>UC: emit Success(cached)
end

Repo->>Remote: fetchMovieDetails(movieId)
Remote->>API: GET /movie/{id}
API-->>Remote: MovieDetailsResponse
Remote-->>Repo: Response
Repo->>Local: saveMovieDetails(response)
Repo-->>UC: emit Success(fresh)

alt Network error
    Local-->>Repo: cached data (if available)
    Repo-->>UC: emit cached OR Error
end

UC-->>VM: ResultState
VM-->>UI: MovieDetailsUiState

```

---

Author: Sidharth Mudgil
