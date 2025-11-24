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
## 🎨 Global Design System

* **Primary Color:** Ocean Blue (`#007AFF` or similar) - Used for primary buttons and active states.
* **Background:** Off-Black / Light Gray (`#F5F7FA`) to maintain a clean, modern look.
* **Typography:**
    * *English:* Inter or SF Pro Display.
    * *Khmer:* **Kantumruy Pro** or **Battambang** (Google Fonts) for readability and modern aesthetics.
* **Iconography:** Rounded, outline style (24px stroke).

---

## 📱 Screen 1: Onboarding (Welcome)

**Objective:** Create a warm, inviting first impression and guide the user to authentication.

| Component | UI Description | Khmer Text (Label) | Interaction/Behavior |
| :--- | :--- | :--- | :--- |
| **Hero Image** | Center-aligned vector illustration (productivity theme). | N/A | Static. |
| **Title** | Large, bold typography (H1). | **សូមស្វាគមន៍មកកាន់ Onboarding Task** | Static. |
| **Subtitle** | Medium grey, smaller text explaining value prop. | គ្រប់គ្រងការងាររបស់អ្នកដោយងាយស្រួល (Manage your tasks easily) | Static. |
| **Primary Button** | Full width, rounded corners, Blue background. | **ចាប់ផ្តើម (Get Started)** | Navigates to *Login/Signup*. |
| **Secondary Link** | Small text button at the bottom. | មានគណនីរួចហើយ? ចូល (Have account? Login) | Navigates to *Login Screen*. |

---

## 🏠 Screen 2: Home Screen

**Objective:** The central dashboard. Needs to handle high information density without looking cluttered.

**Top Bar Area:**
* **Left:** Greeting Text ("Soursdey, [Name]").
* **Right:** 🔔 Notification Icon (Navigates to *NotificationsScreen*).

**Main Content Area:**

| Component | UI Description | Khmer Text (Label) | Interaction/Behavior |
| :--- | :--- | :--- | :--- |
| **Search Bar** | Rounded input field with magnifying glass icon. Grey background. | **ស្វែងរក... (Search...)** | Tapping opens keyboard/search view. |
| **Category Rows** | Horizontal scroll (Pills/Chips). Active state = Blue; Inactive = Grey. | **ការងារ, ផ្ទាល់ខ្លួន, បន្ទាន់ (Work, Personal, Urgent)** | Filters the content below. |
| **Rec. Tasks** | Horizontal Carousel. Cards with "Priority" badge. | **ការងារដែលបានណែនាំ (Recommended)** | Swiping horizontally shows top picks. |
| **Next Tasks** | Section with "Gemini Sparkle" icon. Displays AI suggestions. | **ការងារបន្ទាប់ (Next Tasks)** | Shows predictive tasks from Gemini. |
| **Task List** | Vertical list. Simple row items with Checkbox + Title + Due Time. | **បញ្ជីការងារ (Task List)** | Tap to view details; Tap checkbox to complete. |

**Bottom Navigation Bar:**
* **Background:** Black with top shadow.
* **Layout:** 5 Items.
    1.  🏠 **Home** (Active)
    2.  🕒 **History**
    3.  ➕ **Add Button:** (Center) Large, Floating style, **Blue Circle Background**, White Icon. Sits slightly higher than the bar.
    4.  👤 **Profile**
    5.  ⚙️ **Settings**

---

## 📝 Screen 3: Task Details

**Objective:** View and manage specific task attributes. Focus on readability and editing capability. The TaskDetailsScreen should use off-black background (`Color(0x000000)` / use Text Color OffWhite (#F5F7FA)

**Top Bar Area:**
* **Left:** ⬅️ Back Arrow Icon (Navigates back to Home).
* **Center:** Title text ("Task Details").
* **Right:** ✏️ Edit Icon or 🗑️ Delete Icon.

**Content Area:**

| Component | UI Description | Khmer Text (Label) | Interaction/Behavior |
| :--- | :--- | :--- | :--- |
| **Header** | Large, bold task title. | **ព័ត៌មានលម្អិតការងារ (Task Details)** | Static (or tap to edit). |
| **Status Chip** | Small rounded badge (e.g., "In Progress" - Yellow). | **កំពុងដំណើរការ (In Progress)** | Tap to change status. |
| **Description** | Multi-line text block. | *[User generated description]* | Scrollable if long. |
| **Date/Time** | Icon + Date text. | **កាលបរិច្ឆេទ (Date)** | Opens Date Picker. |
| **Action Button** | Large button at the bottom fixed to screen. | **បញ្ចប់ការងារ (Mark Complete)** | Marks task done and returns to Home. |

---

## ✨ Screen 4: Recommended List

**Objective:** A dedicated view for curated suggestions (likely navigated to from the "See All" button on the Home Screen's Recommendation section). The RecommendedListScreen should use off-black background (`Color(0x000000)` / use Text Color OffWhite (#F5F7FA)


**Top Bar Area:**
* **Left:** ⬅️ Back Arrow Icon.
* **Center:** Title text.

**Content Area:**

| Component | UI Description | Khmer Text (Label) | Interaction/Behavior |
| :--- | :--- | :--- | :--- |
| **Page Title** | H2 Header at the top. | **បញ្ជីដែលបានណែនាំ (Recommended List)** | Static. |
| **Filter Tabs** | Tabs for "By Priority", "By Due Date", "AI Picks". | **តាមអាទិភាព (By Priority)** | Sorts the list below. |
| **Task Cards** | Rich cards displaying: Title, Reason for recommendation (e.g., "Due soon"), and urgency color coding. | N/A | Tap to open *Task Details* (Screen 3). |
| **Dismiss Action** | Small "X" or "Not interested" on cards. | **មិនចាប់អារម្មណ៍ (Not interested)** | Removes item from recommendation algorithm. |

---

## 🔜 Next Steps (Future Screens)

*   **AddNewTask Screen** User can add new task that design as a comprehensive, modern implementation of the `AddNewTaskScreen.kt`.

It is designed with **Jetpack Compose Material 3**, features **Khmer language support**, uses **Input Chips** for quick selection, and includes a polished layout for data entry.

### Key UI Features

1.  **Form Structure**: Use off-black background (`Color(0x000000)`, Divided into clear `InputSection` blocks (Title, Category, Due Date, Priority, Description) for high readability.
2.  **Date & Time**: Uses a split-row layout (`DateTimePickerBox`). In a real app, clicking these would trigger `DatePickerDialog`. Here, they update state to show interaction feedback.
3.  **Visual Priority**:
      * **Priority Selector**: Instead of a generic dropdown, I created a `PrioritySelectionRow`. This creates a large touch target and visually highlights the selection with a colored border and background tint (Red for High, Green for Low).
4.  **Material 3 Chips**: Uses `FilterChip` for categories, which is standard for modern Android apps.
5.  **Fixed Bottom Button**: The "Create Task" button is placed in the `bottomBar` slot of the Scaffold. This ensures it is always accessible, even if the keyboard is open or the user has scrolled down.

---

*   **Profile Screen**: User information and stats. This implementation uses Jetpack Compose and Material 3 design principles to create a clean, modern, and information-rich profile screen, complete with placeholder user data and Khmer labels. The ProfileScreen should implement with Use off-black background (`Color(0x000000)` / use Text Color OffWhite (#F5F7FA)

### Key UI/UX Features:

1.  **Clear Segmentation:** The screen is clearly divided into three sections: **Header** (Identity), **StatsCard** (Metrics), and **ActionList** (Navigation/Actions), improving scannability.
2.  **Modern Stats Card:** Uses a clean `Card` component with vertical dividers to present three key metrics side-by-side, which is highly common in modern dashboards. Each metric is color-coded for quick visual differentiation.
3.  **Action List:** The action items (Settings, Logout) use `ListItem`s for standardized touch targets, ensuring easy navigation. The "Logout" button is intentionally colored **Red** to signal a critical, final action.
4.  **Khmer Typography:** All important labels include the Khmer translation, adhering to the design specification.

---

*   **Settings Screen**: App configuration. This implementation provides a clean and structured `SettingsScreen.kt` using Jetpack Compose, organized into logical sections for easy app configuration management, all while maintaining support for the Khmer language. The SettingsScreen should use off-black background (`Color(0x000000)` / use Text Color OffWhite (#F5F7FA)

### Key UI/UX Features

1.  **Section Organization**: The settings are grouped into logical `SettingsSection` blocks ("General," "Notifications," "Privacy & Security") using bold titles and elevated `Card` containers. This is a best practice for complex configuration screens.
2.  **Toggle Components**: The `SettingsItemToggle` is used for binary options (like Dark Mode and Notifications) and features a modern Material 3 `Switch`.
3.  **Clickable Items**: The `SettingsItemClickable` is used for navigation (like Language and Change Password) and includes a `ChevronRight` icon to clearly indicate that clicking will lead to a new screen.
4.  **Language Support**: Titles and subtitles include Khmer translations, making the interface accessible as requested.
5.  **Modern Aesthetics**: Uses rounded corners (`RoundedCornerShape(12.dp)` for cards) and a soft, off-black background (`Color(0x000000)` / use Text Color OffWhite (#F5F7FA) for a clean, modern look.

---
*   **History**: View completed tasks and past activities. The `HistoryScreen.kt` implementation below provides a comprehensive and modern view of completed tasks and past activities. It uses **Jetpack Compose Material 3** and features **filtering**, **sorting**, and clear display of historical records, including Khmer labels, and a soft, off-black background (`Color(0x000000)` / use Text Color OffWhite (#F5F7FA) for a clean, modern look.

### Key UI/UX Features:

1.  **Filtering (`HistoryFilterButton`)**: Includes a **Dropdown Menu** in the Top Bar that allows users to filter the list instantly by status (Completed, Missed, Deleted). The filter icon changes color when a filter is active. 
2.  **Modern List Card (`HistoryCard`)**: Each history item is presented in a clean `Card` with:
      * **Color-Coded Status Icon**: Helps users quickly identify the outcome (Green for Completed, Red for Missed/Failed).
      * **Detailed Information**: Shows Title, Category, Duration, and Completion Date.
      * **Khmer Labels**: All status and filter options are labeled in Khmer.
3.  **Empty State Handling**: The `EmptyHistoryState` component dynamically updates its message based on whether the user has applied a filter or if the list is just naturally empty.
4.  **Data Models**: Uses Kotlin `enum`s (`HistoryStatus`) for clear, reusable state management, making the UI robust and easy to scale.

---

*   **NotificationsScreen** Display the complete, modern Jetpack Compose implementation for `NotificationsScreen.kt`. The NotificationsScreen should use off-black background (`Color(0x000000)` / use Text Color OffWhite (#F5F7FA)
This code includes:

1.  **Khmer Language Support**: Using the translations defined in your specs.
2.  **Material 3 Design**: Using `CenterAlignedTopAppBar`, `LazyColumn`, and modern styling.
3.  **Empty State Handling**: A dedicated view when the list is empty.
4.  **Dummy Data**: A list of sample notifications to visualize the layout.

### Key Features in the Code:

1.  **`NotificationItem` Data Class**: Defines the structure so you can easily swap the dummy data for real API data later.
2.  **Color Coded Icons**:
      * **Green**: Success (Task Completed).
      * **Blue**: Info (System messages).
      * **Orange**: Reminders.
3.  **Khmer Typography**: I added `lineHeight = 20.sp` to the body text. Khmer characters often have tall "legs" (descenders/ascenders), so extra line height prevents the text from overlapping or looking cramped.
4.  **Empty State Logic**: The `if (isEmptyState)` block cleanly swaps between the List view and the Empty view.

---

### 💡 Design Notes for Developer
* **Khmer Font Handling:** Ensure `line-height` is set to at least `1.5` or `1.6` because Khmer script has tall ascenders and descenders (legs above and below the line).
* **Bottom Nav:** The center "Add" button should use absolute positioning to float halfway out of the navigation bar for that modern "pop-out" look.

---

## 🤝 Contribution

Please ensure all code passes static analysis checks (`./gradlew detekt ktlintCheck`) and unit tests (`./gradlew test`) before submitting a Pull Request.
