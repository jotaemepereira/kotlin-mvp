# Model-View-Presenter application with Kotlin

A simple Android app in Kotlin that shows a list of trending repositories on Github and a detail of each one when clicked.
The selected architecture for the project was MVP (Model-View-Presenter)

## Libraries

- Dagger2 for dependency injection
- Retrofit2 for networking handling
- RxJava2 for async operations
- Moshi for JSON parsing
- Mockito for mocking on tests
- And MarkDownView and CircleImageView for the UI

## Tests


### Unit tests

Added unit tests for both GithubTrendsPresenter and RepoDetailPresenterTest.
Used Mockito to mock the view and the data manager (API calls).

### UI Tests

Used Espresso to run UI tests on both GithubTrendsActivity and RepoDetailActivity.


## Taken decisions

- The search is done on the already searched items. It does not perform a new search within the API
- The trending topic search query is 'android', so it will show the trending repositories on Android

## Improvements

- Unify mock classes for both tests suites (instead of having one for unit tests and other for UI tests)
- Custom message handler for possible Retrofit network responses
- When searching and scrolling down the app will make an API call to get more repos
