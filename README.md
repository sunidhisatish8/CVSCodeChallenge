# Flickr search image app

## About

This Android app allows users to search for images on Flickr based on tags. The user can input a search query, and the app will display a list of images whose tags match the query. Images are displayed in a simple grid layout, one image below the other. When an image is tapped, the app opens a detailed view displaying additional information about the image, such as title, description, author, and the formatted published date.

## Tools

- **Android Studio**: The IDE used for developing the application.
- **Jetpack Compose**: Used for building the UI in a declarative and modern way.
- **Kotlin**: The primary programming language used for the app's development.
- **JUnit**: Used for unit testing various parts of the code to ensure functionality.

## Technologies

- **MVVM Architecture**: The app follows the Model-View-ViewModel (MVVM) architecture, which ensures clean code, separation of concerns, and ease of testing.
- **StateFlow**: These are used for managing and observing data in the app, ensuring that UI updates reactively when the data changes.
- **Coroutines**: Coroutines are used to handle asynchronous tasks such as network calls, ensuring that the app remains responsive without blocking the UI thread.
- **Flows**: Flows are used for handling search input reactively. The search query triggers a flow that debounces the input, ensuring that API calls are made only after the user stops typing for a brief period, minimizing unnecessary requests.
- **Debounce**: Applied to the search functionality to delay network requests until the user has finished typing, reducing the load on the server and improving the user experience.
- **Retrofit**: Retrofit is used for making network requests to the Flickr API.

## API Endpoint

The app fetches images from the following API endpoint:
https://api.flickr.com/services/feeds/photos_public.gne?format=json&nojsoncallback=1&tags=<search_string>

vbnet
Copy code
The `<search_string>` is replaced with the tags entered by the user (which can be a single tag or multiple comma-separated tags).

Retrofit is configured to handle the network requests and parse the JSON response into appropriate data models, making it easy to work with the data in the app.

## Setup Instructions

- Ensure that your development environment is set up for Jetpack Compose:
  - I have utilized Android Studio Ladybug version.
  - Verify that the `composeOptions` in your `build.gradle` file are correctly configured.
- Build and run the app on an emulator or a physical device.

## App Behavior

- The app presents a search bar at the top of the screen.
- The user can type in tags into the search bar. As the user types, the app dynamically updates the displayed images based on the entered query.
- Debouncing ensures that the search query only triggers an API request after the user stops typing for a set period of time.
- The images are displayed in a single-column grid layout (one image below the other).
- A loading indicator is shown while the app is fetching images from the Flickr API.
- Tapping on any image will open a detailed view with more information about the image, including:
  - Title
  - Description
  - Author
  - Formatted published date

## Unit Test

A unit test has been included in the project to verify the functionality of key parts of the code, particularly the logic that handles API data fetching and response parsing.

## Future Enhancements

- **Pagination**: Implement pagination to allow the user to scroll and load more results.
- **Error Handling**: Improve the error handling for network failures or cases where no results are returned from the API.
- **Image Caching**: Implement image caching to reduce loading times and improve performance when scrolling through search results.

## Demo Screenshots

| **Screen 1**        | **Screen 2**         | **Screen 3**          | **Screen 4**         |
|---------------------|----------------------|-----------------------|----------------------|
| **Search Screen**    | **Image Grid**       | **Image Detail View** | **Screen rotation**     |

|![Screenshot 2025-01-15 120228](https://github.com/user-attachments/assets/2e4b721e-5247-4697-a8c4-583ed60a4c22) | ![Screenshot 2025-01-15 120327](https://github.com/user-attachments/assets/94b3e076-153c-415f-8900-82818c90fadc) | 
![Screenshot 2025-01-15 120803](https://github.com/user-attachments/assets/dce1f987-33b2-413f-8d82-deb3709dcc78) | 
![Screenshot 2025-01-15 120823](https://github.com/user-attachments/assets/d076718c-9d1e-4688-a839-e193887f1c10) |
