MarvelApp
=====  

MarvelApp is an Android application that shows a list Marvel characters and its details. It is built
in Kotlin and using Jetpack Compose.

Author
------  
Darío Martín-Lara Ortega - @dmartinlara on GitLab email: dariomartinlara@gmail.com

Build
-----  
You can build the app using Android Studio and adding in your local.properties file the following
constants:
> publicApiKey=YOUR_MARVEL_PUBLIC_API_KEY
> privateApiKey=YOUR_MARVEL_PRIVATE_API_KEY

About the exercise
------  
**Time spent**
For this exercise I have spent around 13 hours. 6 hours for architectural design and logic
implementation, 5 for presentation and 2 hour for testing.

**The architectural approach you took and why**
For this exercise I have decided to follow the architecture proposed by Google for the development
of Android applications since it is the one I have been using lately and allows the application to
scale easily and be open to possible modifications.

In this architecture we can find 3 layers:

- **The presentation layer**, where all the components needed to paint the screens are located. As a
  design pattern I have followed **MVVM**, as it facilitates the development and is fully integrated
  into the Android framework.

- **The domain layer**, which houses the data models with which the application will work and the
  different use cases that bring the different functionalities to our app.

- **The data layer**, where all the components needed to retrieve, store or delete the information
  that the app needs are located.

**3rd party libraries or copied code you may have used**
I have used:

- **Retrofit** and **OkHttp** for network connections
- **Jetpack Compose** for views
- **Android Paging** for pagination
- **Hilt** for dependency injection
- **Coil** for images loading
- **JUnit** for tests

**Other information about the exercise**
I have also added support for DarkTheme using JetpackCompose.