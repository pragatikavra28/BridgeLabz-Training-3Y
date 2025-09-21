import java.util.*;

// Interface for schedulable events
interface ISchedulable {
    void schedule();
    void reschedule(Date newDate);
    void cancel();
}

// User class
class User {
    private String name;
    private String email;
    private String phone;
    
    public User(String name, String email, String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
    }
    
    public String getName() {
        return name;
    }
    
    public String getEmail() {
        return email;
    }
    
    public String getPhone() {
        return phone;
    }
    
    @Override
    public String toString() {
        return name + " (" + email + ")";
    }
}

// Base Event class
abstract class Event implements ISchedulable {
    private static int nextId = 1;
    
    protected final int eventId;
    protected String eventName;
    protected String location;
    protected Date date;
    protected List<User> attendees;
    protected User organizer;
    protected boolean isScheduled;
    protected boolean hasCatering;
    protected boolean hasDecoration;
    protected double baseCost;
    protected double cateringCost;
    protected double decorationCost;
    
    public Event(String eventName, String location, Date date, User organizer) {
        this.eventId = nextId++;
        this.eventName = eventName;
        this.location = location;
        this.date = date;
        this.organizer = organizer;
        this.attendees = new ArrayList<>();
        this.isScheduled = false;
        this.hasCatering = false;
        this.hasDecoration = false;
        this.baseCost = 1000.0; // Default base cost
        this.cateringCost = 500.0;
        this.decorationCost = 300.0;
    }
    
    public Event(String eventName, String location, Date date, User organizer, 
                boolean hasCatering, boolean hasDecoration) {
        this(eventName, location, date, organizer);
        this.hasCatering = hasCatering;
        this.hasDecoration = hasDecoration;
    }
    
    @Override
    public void schedule() {
        if (!isScheduled) {
            isScheduled = true;
            System.out.println("Event '" + eventName + "' has been scheduled for " + date);
        } else {
            System.out.println("Event is already scheduled");
        }
    }
    
    @Override
    public void reschedule(Date newDate) {
        if (isScheduled) {
            this.date = newDate;
            System.out.println("Event '" + eventName + "' has been rescheduled to " + newDate);
        } else {
            System.out.println("Cannot reschedule an unscheduled event");
        }
    }
    
    @Override
    public void cancel() {
        if (isScheduled) {
            isScheduled = false;
            System.out.println("Event '" + eventName + "' has been canceled");
        } else {
            System.out.println("Event is not scheduled");
        }
    }
    
    public void addAttendee(User user) {
        attendees.add(user);
        System.out.println(user.getName() + " has been added to the event");
    }
    
    public double calculateCost() {
        double total = baseCost;
        if (hasCatering) total += cateringCost;
        if (hasDecoration) total += decorationCost;
        return total;
    }
    
    public void displayEventDetails() {
        System.out.println("Event ID: " + eventId);
        System.out.println("Name: " + eventName);
        System.out.println("Location: " + location);
        System.out.println("Date: " + date);
        System.out.println("Organizer: " + organizer);
        System.out.println("Attendees: " + attendees.size());
        System.out.println("Total cost: $" + calculateCost());
        System.out.println("Status: " + (isScheduled ? "Scheduled" : "Not Scheduled"));
    }
    
    // Getters with access control
    public int getEventId() {
        return eventId;
    }
    
    public String getEventName() {
        return eventName;
    }
    
    public String getLocation() {
        return location;
    }
    
    public Date getDate() {
        return date;
    }
    
    public List<User> getAttendees() {
        return new ArrayList<>(attendees); // Return copy to preserve encapsulation
    }
    
    public User getOrganizer() {
        return organizer;
    }
}

// BirthdayEvent subclass
class BirthdayEvent extends Event {
    private String birthdayPerson;
    private int age;
    
    public BirthdayEvent(String eventName, String location, Date date, User organizer, 
                        String birthdayPerson, int age) {
        super(eventName, location, date, organizer);
        this.birthdayPerson = birthdayPerson;
        this.age = age;
    }
    
    public BirthdayEvent(String eventName, String location, Date date, User organizer, 
                        String birthdayPerson, int age, boolean hasCatering, boolean hasDecoration) {
        super(eventName, location, date, organizer, hasCatering, hasDecoration);
        this.birthdayPerson = birthdayPerson;
        this.age = age;
    }
    
    @Override
    public void schedule() {
        super.schedule();
        System.out.println("Happy " + age + "th birthday to " + birthdayPerson + "!");
    }
    
    @Override
    public double calculateCost() {
        double total = super.calculateCost();
        // Add birthday-specific costs
        total += 200; // Cake and decorations
        return total;
    }
    
    @Override
    public void displayEventDetails() {
        super.displayEventDetails();
        System.out.println("Birthday person: " + birthdayPerson);
        System.out.println("Age: " + age);
    }
}

// ConferenceEvent subclass
class ConferenceEvent extends Event {
    private String topic;
    private List<String> speakers;
    
    public ConferenceEvent(String eventName, String location, Date date, User organizer, String topic) {
        super(eventName, location, date, organizer);
        this.topic = topic;
        this.speakers = new ArrayList<>();
    }
    
    public ConferenceEvent(String eventName, String location, Date date, User organizer, 
                          String topic, boolean hasCatering, boolean hasDecoration) {
        super(eventName, location, date, organizer, hasCatering, hasDecoration);
        this.topic = topic;
        this.speakers = new ArrayList<>();
    }
    
    public void addSpeaker(String speaker) {
        speakers.add(speaker);
        System.out.println(speaker + " has been added as a speaker");
    }
    
    @Override
    public void schedule() {
        super.schedule();
        System.out.println("Conference on '" + topic + "' with " + speakers.size() + " speakers");
    }
    
    @Override
    public double calculateCost() {
        double total = super.calculateCost();
        // Add conference-specific costs
        total += speakers.size() * 500; // Speaker fees
        total += 1000; // AV equipment
        return total;
    }
    
    @Override
    public void displayEventDetails() {
        super.displayEventDetails();
        System.out.println("Topic: " + topic);
        System.out.println("Speakers: " + speakers);
    }
}

// Main class
public class EventEase {
    public static void main(String[] args) {
        // Create users
        User organizer = new User("Jane Smith", "jane@example.com", "555-1234");
        User attendee1 = new User("John Doe", "john@example.com", "555-5678");
        User attendee2 = new User("Bob Johnson", "bob@example.com", "555-9012");
        
        // Create events with different constructors
        Date today = new Date();
        BirthdayEvent birthday = new BirthdayEvent(
            "Birthday Party", "Community Hall", today, organizer, "Alice", 25, true, true
        );
        
        ConferenceEvent conference = new ConferenceEvent(
            "Tech Conference", "Convention Center", today, organizer, "AI and Machine Learning"
        );
        conference.addSpeaker("Dr. Smith");
        conference.addSpeaker("Prof. Johnson");
        
        // Schedule events
        birthday.schedule();
        conference.schedule();
        
        // Add attendees
        birthday.addAttendee(attendee1);
        birthday.addAttendee(attendee2);
        conference.addAttendee(attendee1);
        
        // Display event details
        System.out.println("\n--- Birthday Event Details ---");
        birthday.displayEventDetails();
        
        System.out.println("\n--- Conference Event Details ---");
        conference.displayEventDetails();
        
        // Calculate costs
        System.out.println("\n--- Cost Calculation ---");
        System.out.println("Birthday cost: $" + birthday.calculateCost());
        System.out.println("Conference cost: $" + conference.calculateCost());
    }
}