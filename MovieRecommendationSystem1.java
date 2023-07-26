import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Movie {
    private int id;
    private String title;
    private String genre;
    private double averageRating;
    private List<Rating> ratings;

    public Movie(int id, String title, String genre) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.ratings = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getGenre() {
        return genre;
    }

    public double getAverageRating() {
        return averageRating;
    }

    public void addRating(Rating rating) {
        ratings.add(rating);
        updateAverageRating();
    }

    private void updateAverageRating() {
        double sum = 0.0;
        for (Rating rating : ratings) {
            sum += rating.getValue();
        }
        averageRating = sum / ratings.size();
    }
}

class User {
    private int id;
    private String username;
    private Map<Integer, Rating> movieRatings;

    public User(int id, String username) {
        this.id = id;
        this.username = username;
        this.movieRatings = new HashMap<>();
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void rateMovie(Movie movie, double ratingValue) {
        Rating rating = new Rating(movie, ratingValue);
        movieRatings.put(movie.getId(), rating);
    }

    public Map<Integer, Rating> getMovieRatings() {
        return movieRatings;
    }
}

class Rating {
    private Movie movie;
    private double value;

    public Rating(Movie movie, double value) {
        this.movie = movie;
        this.value = value;
    }

    public Movie getMovie() {
        return movie;
    }

    public double getValue() {
        return value;
    }
}

class MovieRecommendationSystem {
    private List<Movie> movies;
    private List<User> users;

    public MovieRecommendationSystem() {
        movies = new ArrayList<>();
        users = new ArrayList<>();
    }

    public void addMovie(Movie movie) {
        movies.add(movie);
    }

    public void addUser(User user) {
        users.add(user);
    }

    public List<Movie> getRecommendedMovies(User user) {
        Map<Integer, Double> movieScores = new HashMap<>();
        Map<Integer, Rating> userRatings = user.getMovieRatings();

        for (User otherUser : users) {
            if (otherUser.getId() != user.getId()) {
                for (Rating rating : otherUser.getMovieRatings().values()) {
                    if (!userRatings.containsKey(rating.getMovie().getId())) {
                        double similarity = computeSimilarity(userRatings, rating.getMovie().getId(), rating.getValue());
                        double score = rating.getValue() * similarity;

                        movieScores.put(rating.getMovie().getId(), movieScores.getOrDefault(rating.getMovie().getId(), 0.0) + score);
                    }
                }
            }
        }

        // Sort movies based on scores in descending order
        List<Movie> recommendedMovies = new ArrayList<>();
        movieScores.entrySet().stream()
                .sorted(Map.Entry.<Integer, Double>comparingByValue().reversed())
                .forEach(entry -> recommendedMovies.add(getMovieById(entry.getKey())));

        return recommendedMovies;
    }

    private double computeSimilarity(Map<Integer, Rating> userRatings, int movieId, double otherRatingValue) {
        double similarity = 0.0;

        for (Rating rating : userRatings.values()) {
            if (rating.getMovie().getId() == movieId) {
                similarity = Math.abs(rating.getValue() - otherRatingValue);
                break;
            }
        }

        return 1.0 / (1.0 + similarity);
    }

    private Movie getMovieById(int movieId) {
        for (Movie movie : movies) {
            if (movie.getId() == movieId) {
                return movie;
            }
        }
        return null;
    }
}

public class MovieRecommendationSystem1 {
    public static void main(String[] args) {
        MovieRecommendationSystem recommendationSystem = new MovieRecommendationSystem();

        // Sample data for movies and users (for demonstration purposes)
        Movie movie1 = new Movie(1, "Movie A", "Action");
        Movie movie2 = new Movie(2, "Movie B", "Comedy");
        recommendationSystem.addMovie(movie1);
        recommendationSystem.addMovie(movie2);

        User user1 = new User(1, "User1");
        user1.rateMovie(movie1, 4.5);
        recommendationSystem.addUser(user1);

        User user2 = new User(2, "User2");
        user2.rateMovie(movie1, 3.0);
        user2.rateMovie(movie2, 4.0);
        recommendationSystem.addUser(user2);

        // Sample user interface loop (for demonstration purposes)
        User currentUser = user1;
        List<Movie> recommendedMovies = recommendationSystem.getRecommendedMovies(currentUser);
        System.out.println("Recommended movies for " + currentUser.getUsername() + ":");
        for (Movie movie : recommendedMovies) {
            System.out.println(movie.getTitle() + " (Genre: " + movie.getGenre() + ")");
        }
    }
}
