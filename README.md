# Onboarding Task

A modern Android application built with Kotlin, Jetpack Compose, and Clean Architecture, designed to demonstrate best practices in Android development.

**Package Name**: `co.peanech.onboardingtask`

## 🚀 Tech Stack & Practices

This project leverages the latest Android technologies and development practices.

### 1. Languages and Runtime
*   **Kotlin**: Primary programming language.
*   **Java**: Used for interop where necessary.
*   **JDK 21**: Built using the JDK 21 toolchain via Gradle toolchains.
*   **Concurrency**: Coroutines and Flow are the default concurrency model. Virtual threads (Project Loom) are used where compatible.

### 2. Jetpack Libraries (AndroidX)
*   **Jetpack Compose**: Modern, declarative UI toolkit replacing XML.
    *   **Material 3**: Adheres to the latest Material Design guidelines.
    *   **Responsive Layouts**: Optimized for both phones and tablets.
*   **Navigation Component**: Structured navigation graph for multi-screen flows with type-safe arguments.
*   **Room**: SQLite object mapping library with Coroutines support, providing a local cache and offline capability.
*   **WorkManager**: Manages background tasks such as data synchronization and receipt uploads with retry policies and network constraints.
*   **Lifecycle & ViewModel**: State holders using `StateFlow` for process-safe state restoration.
*   **DataStore**: Replaces SharedPreferences for storing small datasets, feature flags, and theme settings (Preferences/Proto).
*   **CameraX & ML Kit**: Implements reliable barcode scanning with auto-focus and torch controls.

### 3. Dependency Injection
*   **Hilt**: Primary dependency injection framework (built on Dagger) for app-wide dependency management with seamless Gradle integration.
*   **Koin** (Optional): Available for feature modules or rapid prototyping.

### 4. Firebase Services
*   **Authentication**: Supports Email/Password and OAuth providers (Google, Facebook). Uses Custom Claims for Role-Based Access Control (RBAC).
*   **Firestore / Realtime Database**: Stores data for Products, Sales, and Settings with server timestamps and offline persistence.
*   **Cloud Functions**: Handles backend logic for role assignment, receipt generation, and webhook integrations.
*   **Cloud Storage**: Stores binary assets like logos, exported CSVs, and receipt PDFs.
*   **Crashlytics & Analytics**: Monitors app stability and user engagement.
*   **Remote Config**: Manages feature flags and emergency toggles.

### 5. Build and Tooling
*   **Gradle 9.0.0**: Utilizes configuration caching and parallel execution for faster builds.
*   **Version Catalogs**: Centralized dependency management using `libs.versions.toml`.
*   **Static Analysis**: Enforces code quality with Detekt, Ktlint, and Android Lint.
*   **Testing**:
    *   **Unit Tests**: JUnit5, MockK, Turbine (for Flow).
    *   **UI Tests**: Espresso and Compose UI Test.
    *   **Integration**: Robolectric.

## 📂 Project Structure

The project follows **Clean Architecture** principles, separating concerns into distinct layers:

*   **Presentation**: UI (Compose), ViewModels, and UI State.
*   **Domain**: Business logic, Use Cases, and Repository interfaces.
*   **Data**: Repository implementations, Data Sources (Room, API), and Mappers.

```text
app/
├── src/main/java/com/overtae/onboardingtaskandroid/
│   ├── data/           # Data layer (Repositories, Room, API)
│   ├── domain/         # Domain layer (Models, UseCases, Interfaces)
│   ├── presentation/   # UI layer (ViewModels, Compose Screens, States)
│   └── di/             # Dependency Injection modules
```

## 🛠️ Setup & Installation

1.  **Prerequisites**:
    *   Android Studio Koala or newer.
    *   JDK 21 installed.
2.  **Clone the repository**:
    ```bash
    git clone <repository-url>
    ```
3.  **Open in Android Studio**:
    *   Open the project root directory.
    *   Allow Gradle to sync.
4.  **Firebase Setup**:
    *   Add your `google-services.json` file to the `app/` directory.
5.  **Build & Run**:
    *   Select a device/emulator and click **Run**.

## ✅ Features

*   **User Authentication**: Secure login and signup flows.
*   **Product Management**: Create, read, update, and delete products.
*   **Sales Tracking**: Record sales and generate receipts.
*   **Offline Support**: Full functionality without internet connection, syncing when online.
*   **Barcode Scanning**: Quickly add products to sales using the camera.
*   **Settings**: Configurable app preferences and theme.
*   **Localization**: Full UI/UX support for **Khmer (Cambodia)** language.

## 📱 UI/UX Specifications (Per Screen)

### 1. Screen 1: Onboarding (Welcome)
*   **Overview**: The entry point for new users.
*   **Key Features**: Welcome message, app introduction, and navigation to authentication (Login/Signup).
*   **Language**: Khmer (e.g., "សូមស្វាគមន៍មកកាន់ Onboarding Task").

### 2. Screen 2: Home Screen
*   **Overview**: The central hub of the application.
*   **Key Features**:
    *   **Search Bar**: Allows users to quickly find tasks or items.
    *   **Category Rows**: Horizontal scrollable list to filter content by category.
    *   **Task List**: Display of active tasks.
*   **Language**: Khmer (e.g., "ទំព័រដើម").

### 3. Screen 3: Task Details
*   **Overview**: Detailed view of a specific task.
*   **Key Features**: Full task description, due dates, status management, and edit/delete options.
*   **Language**: Khmer (e.g., "ព័ត៌មានលម្អិតការងារ").

### 4. Screen 4: Recommended List
*   **Overview**: A curated list of suggested items or tasks.
*   **Key Features**: Personalized recommendations based on user activity or predefined criteria.
*   **Language**: Khmer (e.g., "បញ្ជីដែលបានណែនាំ").

## 🔜 Next Steps (Future Screens)
*   **Profile Screen**: User information and stats.
*   **Settings Screen**: App configuration.
*   **History**: View completed tasks and past activities.

## 🤝 Contribution

Please ensure all code passes static analysis checks (`./gradlew detekt ktlintCheck`) and unit tests (`./gradlew test`) before submitting a Pull Request.
