import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Candidate class to represent candidates/options in the ballot
class Candidate {
    private String name;
    private int votes;

    public Candidate(String name) {
        this.name = name;
        this.votes = 0;
    }

    public String getName() {
        return name;
    }

    public int getVotes() {
        return votes;
    }

    public void incrementVotes() {
        this.votes++;
    }
}

// Ballot class to represent a voting ballot
class Ballot {
    private String title;
    private List<Candidate> candidates;

    public Ballot(String title) {
        this.title = title;
        this.candidates = new ArrayList<>();
    }

    public String getTitle() {
        return title;
    }

    public List<Candidate> getCandidates() {
        return candidates;
    }

    public void addCandidate(Candidate candidate) {
        candidates.add(candidate);
    }
}

// VotingSystem class to manage the overall voting process
class VotingSystem {
    private Map<String, User> registeredUsers;
    private List<Ballot> ballots;

    public VotingSystem() {
        registeredUsers = new HashMap<>();
        ballots = new ArrayList<>();
    }

    public void registerUser(User user) {
        registeredUsers.put(user.getUsername(), user);
    }

    public User authenticateUser(String username, String password) {
        User user = registeredUsers.get(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    public void createBallot(Ballot ballot) {
        ballots.add(ballot);
    }

    public List<Ballot> getBallots() {
        return ballots;
    }

    public void castVote(User user, Ballot ballot, Candidate selectedCandidate) {
        // Assuming the user is eligible to vote in this example.
        if (ballots.contains(ballot) && ballot.getCandidates().contains(selectedCandidate)) {
            selectedCandidate.incrementVotes();
            System.out.println(user.getUsername() + " cast a vote for " + selectedCandidate.getName());
        }
    }

    public void generateResults(Ballot ballot) {
        System.out.println("Results for Ballot: " + ballot.getTitle());
        for (Candidate candidate : ballot.getCandidates()) {
            System.out.println(candidate.getName() + ": " + candidate.getVotes() + " votes");
        }
    }
}

// User class to represent registered users
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

public class Voting {
    public static void main(String[] args) {
        VotingSystem votingSystem = new VotingSystem();

        // User registration
        User user1 = new User("user1", "password1");
        votingSystem.registerUser(user1);

        // Ballot creation
        Ballot ballot1 = new Ballot("Presidential Election");
        Candidate candidate1 = new Candidate("Candidate A");
        Candidate candidate2 = new Candidate("Candidate B");
        ballot1.addCandidate(candidate1);
        ballot1.addCandidate(candidate2);
        votingSystem.createBallot(ballot1);

        // User authentication
        User authenticatedUser = votingSystem.authenticateUser("user1", "password1");
        if (authenticatedUser != null) {
            // Cast vote
            votingSystem.castVote(authenticatedUser, ballot1, candidate1);
            votingSystem.castVote(authenticatedUser, ballot1, candidate2);

            // Generate results
            votingSystem.generateResults(ballot1);
        } else {
            System.out.println("Invalid credentials. Authentication failed.");
        }
    }
}
