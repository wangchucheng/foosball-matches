# Foosball Matches


## Overview

In this project, I implemented a foosball match recorder which allows users to add/update/delete a match entry and view all the match histories. Users can also view the rankings which can be sorted by numbers of game played and numbers of game won.

Instead of in-memory storage, I implemented the local database using room database. Two verisons are provided, one is the standard version with MVVM, Navigation, etc. The other version integrates dagger and rxjava in addtion to the standard version.

## Demo

### Light

https://user-images.githubusercontent.com/38368052/206837981-ecb96fde-ed3e-4997-8dcc-dcb4f8f2bf80.mov

### Dark

https://user-images.githubusercontent.com/38368052/206837987-678a47e9-af09-40a1-9657-1d15736147ea.mov

## Branches

There are two branches in this repo:

- **main**: Implemented with MVVM, Data Binding, Kotlin Coroutine, Room database and Safe Args Navigation, etc.
- **dagger-rxjava**: Implemented with MVVM, Data Binding, Dagger, RxJava, Room database and Safe Args Navigation, etc.

Those two branches are almost identical but `dagger-rxjava` integrate `dagger` for DI and uses `RxJava` to replace Kotlin Coroutine.

## Structure

This project is designed to be modular with data layer, repo and ui layer. An overview structure is as below:

```
.
└── app/
    ├── db/
    │   ├── FoosballDatabase
    │   ├── FoosballDatabaseDao
    │   ├── FoosballRepository
    │   ├── Match
    │   ├── Score
    │   ├── ...
    │   └── ScoreDTO
    ├── di (dagger-rxjava branch)/
    │   ├── ApplicationComponent
    │   └── ApplicationModule
    └── ui/
        ├── match/
        │   ├── MatchFragment
        │   ├── MatchViewModel
        │   └── ...
        ├── matchdetail/
        │   ├── MatchDetailFragment
        │   ├── MatchDetailViewModel
        │   └── ...
        ├── ranking/
        │   ├── RankingFragment
        │   ├── RankingViewModel
        │   └── ...
        └── ...
```

## Things Covered

There are many techniques/frameworks covered in this project.

- MVVM Architecture with data binding.
- Navigation with Safe Args to pass data between Fragments.
- Dagger for DI (dagger-rxjava branch) and inject Fragment in appropriate lifecycle.
- Kotlin Coroutine (main branch) and RxJava (dagger-rxjava branch) for thread handling and async programming.
- Intent for passing data between activities and apps.
- ...

I also handled edge cases like:

- Input validation
- Error toast
- Delete alert dialog
- ...
