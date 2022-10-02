# EpiFi Demo App

Repository for Android app made for epiFi recruitment process.

![Minimum API Level](https://img.shields.io/badge/Min%20API%20Level-24-green)
![Target API Level](https://img.shields.io/badge/Max%20API%20Level-31-orange)

### Apk File - [Apk Drive Link](https://drive.google.com/file/d/1Z-N5wTzdi6SRmNnxW0XNH0Dje4FNFr4a/view?usp=sharing)

## Languages Used

1. Kotlin
2. Xml for layouts
3. Groovy for gradle file.


## Architecture

This app employs a clean architecture approach, <strong>MVVM with Repository Pattern.</strong>

This type of architecture design employs repositories to supply data to view models by utilizing both local and remote data sources
based on our requirement

### Reason to choose this architecture design are:-
1. Reduces the view model's complexity, preventing it from becoming bloated.
2. Aids in the creation of explicit logic for the display and data layers separately.
3. Reduces coupling.
4. Aids in boosting the code's testability.
5. Make it simple to add and delete new features from the app in the future.


## External Libraries Used

### Hilt
This project employs hilt for dependency injection, allowing us to inject dependencies into the components from outside which further reduces coupling
and help simplify the creation of unit test cases.

### Glide
It is a library for loading images. It is used to render gifs from urls in the image view. It is incredibly effective and simple to use.

### Glide Webp Decoder
This library is used in conjunction with the glide image loading library to load webp gif urls in the imageview.

### Paging
This library aids in the use of pagination, which is required to display small pieces of data at a time. It loads partial data to reduce network utilisation.


