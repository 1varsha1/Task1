import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Book {
    private int id;
    private String title;
    private String author;
    private String category;
    private double price;
    private double rating;
    private List<String> reviews;

    public Book(int id, String title, String author, String category, double price) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.category = category;
        this.price = price;
        this.rating = 0.0;
        this.reviews = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getCategory() {
        return category;
    }

    public double getPrice() {
        return price;
    }

    public double getRating() {
        return rating;
    }

    public List<String> getReviews() {
        return reviews;
    }

    public void addReview(String review) {
        reviews.add(review);
    }

    public void updateRating(double newRating) {
        // Assuming a simple averaging approach for calculating the book's rating.
        rating = (rating * reviews.size() + newRating) / (reviews.size() + 1);
    }
}

class User {
    private String username;
    private String password;
    private List<Book> cart;
    private List<Book> orders;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.cart = new ArrayList<>();
        this.orders = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public List<Book> getCart() {
        return cart;
    }

    public List<Book> getOrders() {
        return orders;
    }

    public void addToCart(Book book) {
        cart.add(book);
    }

    public void removeFromCart(Book book) {
        cart.remove(book);
    }

    public void checkout() {
        orders.addAll(cart);
        cart.clear();
    }
}

class BookStore {
    private List<Book> books;
    private Map<String, User> users;

    public BookStore() {
        books = new ArrayList<>();
        users = new HashMap<>();
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public List<Book> getBooks() {
        return books;
    }

    public void registerUser(User user) {
        users.put(user.getUsername(), user);
    }

    public User authenticateUser(String username, String password) {
        User user = users.get(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }
}

public class JavaBooksStoreEg {
    public static void main(String[] args) {
        BookStore bookStore = new BookStore();

        // Adding books to the bookstore
        Book book1 = new Book(1, "Book A", "Author X", "Fiction", 25.99);
        Book book2 = new Book(2, "Book B", "Author Y", "Science", 19.99);
        bookStore.addBook(book1);
        bookStore.addBook(book2);

        // User registration
        User user1 = new User("user1", "password1");
        bookStore.registerUser(user1);

        // User authentication
        User authenticatedUser = bookStore.authenticateUser("user1", "password1");
        if (authenticatedUser != null) {
            // Adding books to the user's cart
            authenticatedUser.addToCart(book1);
            authenticatedUser.addToCart(book2);

            // Checking out the cart
            authenticatedUser.checkout();

            // Providing a review for a book
            book1.addReview("Great book! Highly recommended.");
            book1.updateRating(4.5);

            // Displaying the book details and reviews
            System.out.println(book1.getTitle());
            System.out.println("Author: " + book1.getAuthor());
            System.out.println("Price: $" + book1.getPrice());
            System.out.println("Rating: " + book1.getRating());
            System.out.println("Reviews:");
            for (String review : book1.getReviews()) {
                System.out.println("- " + review);
            }
        } else {
            System.out.println("Invalid credentials. Authentication failed.");
        }
    }
}
