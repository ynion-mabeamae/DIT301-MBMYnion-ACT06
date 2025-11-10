# Activity 6 : Reflection

<strong>Which API did you choose and why?</strong>
<p>
I chose the OpenWeatherMap API for this acitivty since it has an excellent and dependent weather data service, which is suitable for educational use. The API delivers a wide variety of weather data elements, including temperature, humidity, wind speed as well as weather descriptions. Having many weather concepts to work with enabled me to build an application which included many features. Their free tier offers from 1000 daily API calls, which was more than enough for my development and testing purposes. The documentation as well as the quality of the returned JSON data is well structured. OpenWeatherMap also has global coverage of cities, which made it certain my app would work for anyone, anywhere. I felt confident about using it for this project, because it has an established reputation for reliability, I could focus on implementation and not worry about possible downtime of data or the API itself.
</p>

<strong>How did you implement data fetching and JSON parsing?</strong>
<p>
  I implemented data fetching using Retrofit2, a type-safe HTTP client for Android, which streamlined the API communication process. The implementation involved creating a Retrofit instance with the OpenWeatherMap base URL and integrating Gson converter for automatic JSON parsing. I defined the API endpoints through an interface with suspend functions for coroutine support, enabling asynchronous network calls without blocking the main thread. The data fetching process included proper error handling with different HTTP status codes and utilized coroutines to manage background threading, ensuring a responsive user interface while waiting for API responses.
</p>

<strong>What challenges did you face when handling errors or slow
connections?</strong>
<p>
During the implementation of error handling, we encountered several issues, especially in API key validation and detecting connectivity. For example, the new OpenWeatherMap API keys took a decent amount of time to activate (up to 2 hours), which was problematic since we received, "Invalid API Key, Reasons: Key invalid., " messages to mislead users with valid credentials. Dealing with a slow network connection was also a challenge, as I had to handle the timeout appropriately and indicate to users that it was still loading, so they didn't feel that the app had frozen. I also had to deal with different types of errors - for instance, if there was a network error vs. an invalid city name vs. being rated by the API - and allow for the use of the HTTP status code for a more detailed report to the user. Finally, for coroutine life cycles when the screen was rotated and sent the app backgrounded, I had to ensure that I cancelled the network calls so that I did not impact the memory of the app and unnecessarily billed the API.
</p>

<strong>How would you improve your app’s UI or performance in future
versions?</strong>
<p>
For future iterations, my focus will be on both user accessibility and performance optimizations. In terms of user experience, the UI could be greatly improved by incorporating weather icons representing different weather conditions, changing it to more appealing Material Design 3 with dynamic color themes based on weather conditions, and including a 5-day forecast feature with horizontal scrolling. In terms of performance, I would implement caching methods using the Room database to save user recent searches and reduce calls to the API, with the addition of a swipe-to-refresh for manually retrieving their information. Other features would add location-based weather detection using GPS, allowing for favorites cities to be stored locally, and weather alerts regarding severe conditions. I would also enhance the network layer with exponential backoff retry methods, and proper implemented ViewModel architecture to survive configuration changes, all resulting in a more robust, user-friendly application.
</p>