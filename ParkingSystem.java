import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class ParkingSpot {
    private int id;
    private String location;
    private boolean isAvailable;

    public ParkingSpot(int id, String location) {
        this.id = id;
        this.location = location;
        this.isAvailable = true;
    }

    public int getId() {
        return id;
    }

    public String getLocation() {
        return location;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }
}

class User {
    private String username;
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}

class Booking {
    private int bookingId;
    private ParkingSpot parkingSpot;
    private User user;
    private String bookingTime;

    public Booking(int bookingId, ParkingSpot parkingSpot, User user, String bookingTime) {
        this.bookingId = bookingId;
        this.parkingSpot = parkingSpot;
        this.user = user;
        this.bookingTime = bookingTime;
    }

    public int getBookingId() {
        return bookingId;
    }

    public ParkingSpot getParkingSpot() {
        return parkingSpot;
    }

    public User getUser() {
        return user;
    }

    public String getBookingTime() {
        return bookingTime;
    }
}

public class ParkingSystem {
    private static List<ParkingSpot> parkingSpots = new ArrayList<>();
    private static List<User> users = new ArrayList<>();
    private static List<Booking> bookings = new ArrayList<>();
    private static int bookingIdCounter = 1;

    public static void main(String[] args) {
        initializeParkingSpots();

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n1. User Registration");
            System.out.println("2. Search for available parking spots");
            System.out.println("3. Book a parking spot");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    userRegistration(scanner);
                    break;
                case 2:
                    showAvailableParkingSpots();
                    break;
                case 3:
                    bookParkingSpot(scanner);
                    break;
                case 4:
                    System.out.println("Thank you for using the parking system. Goodbye!");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void initializeParkingSpots() {
        parkingSpots.add(new ParkingSpot(1, "Main Street"));
        parkingSpots.add(new ParkingSpot(2, "Central Park"));
        parkingSpots.add(new ParkingSpot(3, "Downtown Mall"));
    }

    private static void userRegistration(Scanner scanner) {
        scanner.nextLine(); // Consume the newline character
        System.out.print("Enter your username: ");
        String username = scanner.nextLine();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        User user = new User(username, password);
        users.add(user);
        System.out.println("Registration successful! You can now log in.");
    }

    private static void showAvailableParkingSpots() {
        System.out.println("\nAvailable Parking Spots:");
        for (ParkingSpot spot : parkingSpots) {
            if (spot.isAvailable()) {
                System.out.println(spot.getId() + ". " + spot.getLocation());
            }
        }
    }

    private static void bookParkingSpot(Scanner scanner) {
        showAvailableParkingSpots();
        System.out.print("Enter the ID of the parking spot you want to book: ");
        int spotId = scanner.nextInt();

        ParkingSpot selectedSpot = null;
        for (ParkingSpot spot : parkingSpots) {
            if (spot.getId() == spotId && spot.isAvailable()) {
                selectedSpot = spot;
                break;
            }
        }

        if (selectedSpot != null) {
            scanner.nextLine(); // Consume the newline character
            System.out.print("Enter your username: ");
            String username = scanner.nextLine();
            User user = findUser(username);
            if (user != null) {
                String bookingTime = "08:00 AM"; // For simplicity, hardcoding the booking time
                Booking booking = new Booking(bookingIdCounter++, selectedSpot, user, bookingTime);
                selectedSpot.setAvailable(false);
                bookings.add(booking);
                System.out.println("Booking successful! Enjoy your parking.");
            } else {
                System.out.println("User not found. Please register or try again.");
            }
        } else {
            System.out.println("Invalid parking spot ID or the spot is already booked.");
        }
    }

    private static User findUser(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }
}
