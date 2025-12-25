# **Pokémon Catalogue App 📱**


https://github.com/user-attachments/assets/758525f9-268c-4085-a579-fcc7b130a92b


A modern native Android application built with **Kotlin** and **Jetpack Compose**, demonstrating **Clean Architecture** and **MVVM** patterns. This app allows users to browse Generation 1 Pokémon, filter/sort results, and manage a personal "Backpack" collection.

## **🏗️ Architecture**

This project strictly follows **Clean Architecture** principles to ensure separation of concerns, scalability, and testability.

### **Layers**

1. **Domain Layer** (com.example.pokemoncatalogueapp.domain)
    * **Role**: The "Brain" of the app. Contains pure business logic.
    * **Components**:
        * Models: Pure Kotlin data classes (e.g., Pokemon).
        * Repository Interfaces: Definitions of data operations.
        * Use Cases: Single-responsibility business rules (e.g., GetPokemonListUseCase, ToggleFavoriteUseCase).
    * *Dependencies*: None (Pure Kotlin).
2. **Data Layer** (com.example.pokemoncatalogueapp.data)
    * **Role**: The "Implementation". Handles data retrieval.
    * **Components**:
        * API: Retrofit service definitions.
        * DTOs: Data Transfer Objects mirroring JSON responses.
        * Repository Implementation: Concrete implementation of Domain interfaces.
    * *Dependencies*: Retrofit, Gson.
3. **Presentation Layer** (com.example.pokemoncatalogueapp.ui)
    * **Role**: The "Face" of the app. Handles UI and State.
    * **Components**:
        * Screens: Jetpack Compose UI functions.
        * ViewModels: Manages UI state (StateFlow) and maps User Actions to Use Cases.
    * *Dependencies*: Jetpack Compose, Coil (Image Loading), Android Lifecycle.

### **Dependency Injection**

* **Module**: AppModule.kt
* **Type**: Manual Dependency Injection (Singleton pattern) to provide Repositories and Use Cases to ViewModels.

## **🛠️ Tech Stack**

* **Language**: Kotlin
* **UI Framework**: Jetpack Compose (Material3)
* **Architecture**: MVVM \+ Clean Architecture
* **Async**: Coroutines \+ StateFlow
* **Networking**: Retrofit 2 \+ Gson
* **Image Loading**: Coil
* **Navigation**: Jetpack Navigation Compose

## **🚀 Features**

* **Catalogue**: Browse all 151 Gen 1 Pokémon.
* **Search**: Real-time search by name.
* **Sorting**: Sort by ID, Name (A-Z/Z-A), or Type.
* **Filtering**: Filter by specific element types (Fire, Water, etc.).
* **Backpack**: Add/Remove Pokémon to a personal collection.
* **Favorites & Ratings**: Mark favorites and rate Pokémon (1-5 stars).
* **Persistent State**: (In-Memory for this demo) Tracks user interactions across screens.

## **📦 Setup & Run**

1. **Clone** the repository.
2. Open in **Android Studio** (Hedgehog or later recommended).
3. Sync Gradle files.
4. Run on an Emulator or Physical Device.
    * *Note: Internet connection is required to fetch data from PokeAPI.*

## **📂 Folder Structure**

app/src/main/java/com/example/pokemoncatalogueapp
├── data          \# API, DTOs, Repository Impl  
├── domain        \# Models, UseCases, Repo Interfaces  
├── di            \# Manual Dependency Injection  
├── ui            \# ViewModels, Screens, Components  
└── MainActivity.kt  

