## 📌 Index
1. [Screenshots](#screenshots)
2. [APK](#apk)
3. [Demo Recording](#demo-recording)
4. [Module Overview](#module-overview)  
5. [Architecture](#architecture)  
6. [Module Dependency Graph](#module-dependency-graph)  
7. [App Navigation Flow](#app-navigation-flow)  
8. [Feature Data Flows](#feature-data-flows)  
9. [ViewModel State Management](#viewmodel-state-management)  
10. [Network Layer](#network-layer)  
11. [Dependency Injection](#dependency-injection)  
12. [Tech Stack](#tech-stack) 

## Screenshots
| ![Screenshot 1](assets/img1.png) | ![Screenshot 2](assets/img2.png) |
| --- | --- |
| ![Screenshot 3](assets/img3.png) | ![Screenshot 4](assets/img4.png) |

---

## APK
[Download filmy apk](assets/app-debug.apk)

---

## Demo Recording


https://github.com/user-attachments/assets/22fcfce8-86ff-424e-a976-d12e23231dac



[Download recording](assets/recording.mp4)

## Module Overview

```
app
├── core:model
├── core:network
├── core:navigation
├── core:ui
├── feature:search
└── feature:movie_details
```

Each module has a single responsibility and communicates via abstractions, ensuring scalability and testability.

---

## Architecture

The app follows Clean Architecture with feature-based modularization:

* Presentation → Jetpack Compose + ViewModel
* Domain → UseCases + Repository interfaces
* Data → Repository implementations, mappers
* Core modules → Shared logic (network, UI theme, navigation, models)

---

## Module Dependency Graph

```mermaid
flowchart LR
    app --> core_navigation
    app --> core_ui
    app --> feature_search
    app --> feature_movie_details

    feature_search --> core_model
    feature_search --> core_network
    feature_search --> core_navigation
    feature_search --> core_ui

    feature_movie_details --> core_model
    feature_movie_details --> core_network
    feature_movie_details --> core_navigation
    feature_movie_details --> core_ui
```

---

## App Navigation Flow

```mermaid
flowchart TD
    MainActivity --> NavHost
    NavHost --> SearchScreen
    NavHost --> MovieDetailsScreen
```

---

## Feature: Search/Trending – Data Flow

### Trending Movies
```mermaid
sequenceDiagram
participant UI as SearchScreen
participant VM as SearchViewModel
participant UC as GetTrendingMoviesUseCase
participant Repo as SearchRepositoryImpl
participant Cache as trendingCache
participant Remote as RemoteDataSource
participant API as ApiService

UI->>VM: SearchIntent.Search(query="")
VM->>UI: emit Empty
VM->>UC: getTrendingMovies()
UC->>Repo: getTrendingMovies()
Repo->>UI: emit Loading
Repo->>Cache: check trendingCache
alt Cache hit
    Cache-->>Repo: List<SearchItem>
    Repo-->>UC: emit Success(cached)
end
Repo->>Remote: fetchTrendingMovies()
Remote->>API: GET /trending/movie
API-->>Remote: MovieListResponse
Remote-->>Repo: Response
Repo->>Cache: update trendingCache
Repo-->>UC: emit Success(fresh)
alt Error
    Repo-->>UC: emit Error
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
participant Cache as searchCache
participant Remote as RemoteDataSource
participant API as ApiService

UI->>VM: SearchIntent.Search(query="movie name")
VM->>UI: emit Empty
VM->>UC: searchMovie(query)
UC->>Repo: searchMovie(query)
Repo->>UI: emit Loading
Repo->>Cache: check searchCache[query]
alt Cache hit
    Cache-->>Repo: List<SearchItem>
    Repo-->>UC: emit Success(cached)
end
Repo->>Remote: searchMovie(query)
Remote->>API: GET /search/movie
API-->>Remote: MovieListResponse
Remote-->>Repo: Response
Repo->>Cache: update searchCache[query]
Repo-->>UC: emit Success(fresh)
alt Error
    Repo-->>UC: emit Error
end
UC-->>VM: ResultState
VM-->>UI: SearchUiState

```

## Feature: Movie Details – Data Flow

```mermaid
sequenceDiagram
participant UI as MovieDetailsScreen
participant VM as MovieDetailsViewModel
participant UC as GetMovieDetailsUseCase
participant Repo as MovieRepositoryImpl
participant Cache as InMemoryCache
participant Remote as RemoteDataSource
participant API as ApiService


UI->>VM: MovieDetailsIntent.Load(movieId)
VM->>UI: emit Empty
UC->>Repo: getMovieDetails(movieId)
Repo->>UI: emit Loading


Repo->>Cache: check movieCache[movieId]
alt Cache hit
Cache-->>Repo: Movie
Repo-->>UC: emit Success(cached)
end


Repo->>Remote: fetchMovieDetails(movieId)
Remote->>API: GET /movie/{id}
API-->>Remote: MovieDetailsResponse
Remote-->>Repo: Response
Repo->>Cache: update movieCache[movieId]
alt Response not empty
Repo-->>UC: emit Success(fresh)
end


alt Error
Repo-->>UC: emit Error
end


UC-->>VM: ResultState
VM-->>UI: MovieDetailsUiState
```

---

## ViewModel State Management

```mermaid
flowchart LR
    Intent --> ViewModel
    ViewModel --> UiState
    ViewModel --> Effect
```

---

## Network Layer

```
ApiService
   ↓
RemoteDataSource
   ↓
Repository
```

## Dependency Injection

```mermaid
flowchart TD
    Hilt --> ViewModel
    Hilt --> UseCase
    Hilt --> Repository
    Hilt --> RemoteDataSource
    Hilt --> ApiService
```

---

## Tech Stack
* Kotlin 2.x
* Jetpack Compose
* Navigation Compose
* Hilt
* Retrofit 3
* Coil
* Clean Architecture
* Multi-module Gradle setup

---

Author: Sidharth Mudgil
