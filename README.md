# NewsApp

NewsApp is a Kotlin-based Android application designed to fetch and display news articles. The app fetches articles from a remote API and displays them using a slider for breaking news and a detailed view for individual articles. It also handles permissions, loading states, and UI optimizations to provide a smooth user experience.

## Features
- **Breaking News Slider**: Displays the latest news articles in a scrollable horizontal list.
- **Article Detail Screen**: Displays detailed information about an article when clicked.
- **Dynamic Content Fetching**: Fetches articles from a remote API using Retrofit.
- **Permissions Handling**: Manages necessary permissions such as network access.
- **Responsive UI**: Adapts to different screen sizes with a clean and user-friendly design.

## Technologies Used
- **Kotlin**: The main programming language used for development.
- **Jetpack Compose**: Used for building the UI components.
- **Retrofit**: For making network calls to the news API.
- **Accompanist Pager**: To implement a horizontal scrolling pager for displaying images in the slider.
- **ViewModel & LiveData**: For managing UI-related data lifecycle-consciously.
- **Coil**: For loading images into the UI components efficiently.

