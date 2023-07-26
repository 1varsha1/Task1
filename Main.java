import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class User {
    private String userId;
    private String name;
    private String email;
    // Other user attributes and appointment information can be added as needed.

    public User(String userId, String name, String email) {
        this.userId = userId;
        this.name = name;
        this.email = email;
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}

class Appointment {
    private String appointmentId;
    private User user;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public Appointment(String appointmentId, User user, LocalDateTime startTime, LocalDateTime endTime) {
        this.appointmentId = appointmentId;
        this.user = user;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getAppointmentId() {
        return appointmentId;
    }

    public User getUser() {
        return user;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }
}

class AppointmentScheduler {
    private Map<LocalDateTime, List<Appointment>> appointments;

    public AppointmentScheduler() {
        appointments = new HashMap<>();
    }

    public void addAppointment(Appointment appointment) {
        LocalDateTime startTime = appointment.getStartTime();
        appointments.computeIfAbsent(startTime, k -> new ArrayList<>()).add(appointment);
    }

    public List<Appointment> getAppointments(LocalDateTime startTime) {
        return appointments.getOrDefault(startTime, Collections.emptyList());
    }

    public void removeAppointment(Appointment appointment) {
        LocalDateTime startTime = appointment.getStartTime();
        appointments.computeIfPresent(startTime, (k, v) -> {
            v.removeIf(appt -> appt.getAppointmentId().equals(appointment.getAppointmentId()));
            return v;
        });
    }
}

public class Main {
    public static void main(String[] args) {
        // Sample usage of the Appointment Scheduling System
        AppointmentScheduler appointmentScheduler = new AppointmentScheduler();

        User user1 = new User("user123", "John Doe", "john.doe@example.com");

        // Create an appointment
        LocalDateTime startTime = LocalDateTime.of(2023, 7, 26, 14, 0);
        LocalDateTime endTime = startTime.plusHours(1);
        Appointment appointment1 = new Appointment("appt1", user1, startTime, endTime);

        // Add the appointment to the scheduler
        appointmentScheduler.addAppointment(appointment1);

        // Get appointments for a specific time
        List<Appointment> appointmentsAtTime = appointmentScheduler.getAppointments(startTime);
        for (Appointment appointment : appointmentsAtTime) {
            System.out.println(appointment.getUser().getName() + " has an appointment at " + appointment.getStartTime());
        }

        // Remove an appointment
        appointmentScheduler.removeAppointment(appointment1);
    }
}
