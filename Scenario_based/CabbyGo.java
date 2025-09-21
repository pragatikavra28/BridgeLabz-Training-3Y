import java.util.*;

// Interface for ride services
interface IRideService {
    void bookRide(String pickup, String destination);
    void endRide();
    double calculateFare(double distance);
}

// Vehicle class
class Vehicle {
    private String vehicleNumber;
    private int capacity;
    private String type;
    private double baseFare;
    private double ratePerKm;
    
    public Vehicle(String vehicleNumber, int capacity, String type, double baseFare, double ratePerKm) {
        this.vehicleNumber = vehicleNumber;
        this.capacity = capacity;
        this.type = type;
        this.baseFare = baseFare;
        this.ratePerKm = ratePerKm;
    }
    
    // Getters
    public String getVehicleNumber() { return vehicleNumber; }
    public int getCapacity() { return capacity; }
    public String getType() { return type; }
    public double getBaseFare() { return baseFare; }
    public double getRatePerKm() { return ratePerKm; }
    
    @Override
    public String toString() {
        return type + " (Capacity: " + capacity + ", Number: " + vehicleNumber + ")";
    }
}

// Vehicle subclasses
class Mini extends Vehicle {
    public Mini(String vehicleNumber) {
        super(vehicleNumber, 4, "Mini", 50.0, 10.0);
    }
}

class Sedan extends Vehicle {
    public Sedan(String vehicleNumber) {
        super(vehicleNumber, 4, "Sedan", 80.0, 15.0);
    }
}

class SUV extends Vehicle {
    public SUV(String vehicleNumber) {
        super(vehicleNumber, 6, "SUV", 100.0, 20.0);
    }
}

// Driver class
class Driver {
    private String name;
    private String licenseNumber;
    private double rating;
    private Vehicle vehicle;
    
    public Driver(String name, String licenseNumber, Vehicle vehicle) {
        this.name = name;
        this.licenseNumber = licenseNumber;
        this.vehicle = vehicle;
        this.rating = 5.0; // Default rating
    }
    
    // Getters
    public String getName() { return name; }
    public String getLicenseNumber() { return licenseNumber; }
    public double getRating() { return rating; }
    public Vehicle getVehicle() { return vehicle; }
    
    // Method to update rating
    public void updateRating(double newRating) {
        this.rating = (this.rating + newRating) / 2;
    }
    
    @Override
    public String toString() {
        return name + " (License: " + licenseNumber + ", Rating: " + rating + ") - " + vehicle;
    }
}

// Ride class implementing IRideService
class Ride implements IRideService {
    private Driver driver;
    private String pickupLocation;
    private String destination;
    private double distance;
    private double fare;
    private boolean isActive;
    
    public Ride(Driver driver) {
        this.driver = driver;
        this.isActive = false;
    }
    
    @Override
    public void bookRide(String pickup, String destination) {
        this.pickupLocation = pickup;
        this.destination = destination;
        this.isActive = true;
        System.out.println("Ride booked with " + driver.getName() + 
                          " from " + pickup + " to " + destination);
    }
    
    @Override
    public void endRide() {
        if (isActive) {
            this.isActive = false;
            // Simulate distance calculation (in real app, this would use GPS)
            this.distance = Math.random() * 20 + 1; // Random distance between 1-20 km
            this.fare = calculateFare(distance);
            
            System.out.println("Ride ended. Distance: " + String.format("%.2f", distance) + 
                             " km, Fare: ₹" + String.format("%.2f", fare));
        }
    }
    
    @Override
    public double calculateFare(double distance) {
        Vehicle vehicle = driver.getVehicle();
        return vehicle.getBaseFare() + (distance * vehicle.getRatePerKm());
    }
    
    // Getters
    public Driver getDriver() { return driver; }
    public double getFare() { return fare; }
    public double getDistance() { return distance; }
    public boolean isActive() { return isActive; }
}

// CabbyGo system
public class CabbyGo {
    private List<Driver> drivers;
    
    public CabbyGo() {
        drivers = new ArrayList<>();
        initializeDrivers();
    }
    
    private void initializeDrivers() {
        drivers.add(new Driver("Rajesh", "DL123456", new Mini("DL01AB1234")));
        drivers.add(new Driver("Suresh", "DL234567", new Sedan("DL02CD5678")));
        drivers.add(new Driver("Mahesh", "DL345678", new SUV("DL03EF9012")));
    }
    
    public List<Driver> findAvailableDrivers(String vehicleType) {
        List<Driver> availableDrivers = new ArrayList<>();
        for (Driver driver : drivers) {
            if (vehicleType == null || driver.getVehicle().getType().equalsIgnoreCase(vehicleType)) {
                availableDrivers.add(driver);
            }
        }
        return availableDrivers;
    }
    
    public Ride bookRide(String pickup, String destination, String vehicleType) {
        List<Driver> availableDrivers = findAvailableDrivers(vehicleType);
        
        if (availableDrivers.isEmpty()) {
            System.out.println("No drivers available for " + 
                              (vehicleType != null ? vehicleType : "any vehicle type"));
            return null;
        }
        
        // Select a random driver (in real app, this would be based on proximity)
        Driver selectedDriver = availableDrivers.get((int)(Math.random() * availableDrivers.size()));
        Ride ride = new Ride(selectedDriver);
        ride.bookRide(pickup, destination);
        
        return ride;
    }
    
    public static void main(String[] args) {
        CabbyGo cabbyGo = new CabbyGo();
        
        // Display available drivers
        System.out.println("Available Drivers:");
        for (Driver driver : cabbyGo.findAvailableDrivers(null)) {
            System.out.println("  " + driver);
        }
        
        // Book rides
        System.out.println("\nBooking rides:");
        Ride ride1 = cabbyGo.bookRide("Connaught Place", "India Gate", "Mini");
        Ride ride2 = cabbyGo.bookRide("Rajiv Chowk", "Aerocity", "Sedan");
        
        // End rides
        System.out.println("\nEnding rides:");
        if (ride1 != null) {
            ride1.endRide();
            // Rate the driver
            ride1.getDriver().updateRating(4.5);
        }
        
        if (ride2 != null) {
            ride2.endRide();
            ride2.getDriver().updateRating(4.8);
        }
        
        // Show updated ratings
        System.out.println("\nUpdated driver ratings:");
        for (Driver driver : cabbyGo.findAvailableDrivers(null)) {
            System.out.println("  " + driver.getName() + ": " + driver.getRating());
        }
    }
}