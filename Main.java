
import java.util.*;

// ================= VEHICLE CLASS =================
class Vehicle {
    private int vehicleId;
    private String brand;
    private double rentPerDay;
    private boolean available;

    public Vehicle(int vehicleId, String brand, double rentPerDay) {
        this.vehicleId = vehicleId;
        this.brand = brand;
        this.rentPerDay = rentPerDay;
        this.available = true;
    }

    public int getVehicleId() {
        return vehicleId;
    }

    public String getBrand() {
        return brand;
    }

    public double getRentPerDay() {
        return rentPerDay;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public String getType() {
        return "Vehicle";
    }

    public void display() {
        System.out.println(vehicleId + " | " + getType()
                + " | " + brand + " | Rs." + rentPerDay
                + " | " + (available ? "Available" : "Rented"));
    }
}

// ================= CAR CLASS =================
class Car extends Vehicle {
    public Car(int id, String brand, double rent) {
        super(id, brand, rent);
    }

    @Override
    public String getType() {
        return "Car";
    }
}

// ================= BIKE CLASS =================
class Bike extends Vehicle {
    public Bike(int id, String brand, double rent) {
        super(id, brand, rent);
    }

    @Override
    public String getType() {
        return "Bike";
    }
}

// ================= CUSTOMER CLASS =================
class Customer {
    private int customerId;
    private String name;

    public Customer(int customerId, String name) {
        this.customerId = customerId;
        this.name = name;
    }

    public int getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public int getRentalLimit() {
        return 1;
    }

    public double getDiscount() {
        return 0;
    }

    public String getCustomerType() {
        return "Normal Customer";
    }
}

// ================= PREMIUM CUSTOMER =================
class PremiumCustomer extends Customer {
    public PremiumCustomer(int id, String name) {
        super(id, name);
    }

    @Override
    public int getRentalLimit() {
        return 3;
    }

    @Override
    public double getDiscount() {
        return 10;
    }

    @Override
    public String getCustomerType() {
        return "Premium Customer";
    }
}

// ================= RENTAL TRANSACTION =================
class RentalTransaction {
    private int transactionId;
    private Vehicle vehicle;
    private Customer customer;
    private int plannedDays;
    private boolean returned;

    public RentalTransaction(int id, Vehicle vehicle,
            Customer customer, int plannedDays) {

        this.transactionId = id;
        this.vehicle = vehicle;
        this.customer = customer;
        this.plannedDays = plannedDays;
        this.returned = false;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public Customer getCustomer() {
        return customer;
    }

    public int getPlannedDays() {
        return plannedDays;
    }

    public boolean isReturned() {
        return returned;
    }

    public void setReturned(boolean returned) {
        this.returned = returned;
    }
}

// ================= RETURN TRANSACTION =================
class ReturnTransaction {
    private int returnId;
    private RentalTransaction rental;
    private int actualDays;

    public ReturnTransaction(int returnId,
            RentalTransaction rental, int actualDays) {

        this.returnId = returnId;
        this.rental = rental;
        this.actualDays = actualDays;
    }

    public int getReturnId() {
        return returnId;
    }

    public RentalTransaction getRental() {
        return rental;
    }

    public int getActualDays() {
        return actualDays;
    }
}

// ================= BILL CLASS =================
class Bill {
    private ReturnTransaction returnTransaction;
    private double totalAmount;
    private double discountAmount;
    private double finalAmount;

    public Bill(ReturnTransaction returnTransaction) {
        this.returnTransaction = returnTransaction;

        RentalTransaction rental = returnTransaction.getRental();

        totalAmount = rental.getVehicle().getRentPerDay()
                * returnTransaction.getActualDays();

        discountAmount = totalAmount
                * rental.getCustomer().getDiscount() / 100;

        finalAmount = totalAmount - discountAmount;
    }

    // Corrected method for finding bill using rental ID
    public int getRentalTransactionId() {
        return returnTransaction.getRental().getTransactionId();
    }

    public void displayBill() {
        RentalTransaction rental = returnTransaction.getRental();

        System.out.println("\n========== RENTAL BILL ==========");
        System.out.println("Return ID: " + returnTransaction.getReturnId());
        System.out.println("Transaction ID: " + rental.getTransactionId());
        System.out.println("Customer: " + rental.getCustomer().getName());
        System.out.println("Customer Type: "
                + rental.getCustomer().getCustomerType());
        System.out.println("Vehicle: " + rental.getVehicle().getBrand());
        System.out.println("Vehicle Type: " + rental.getVehicle().getType());
        System.out.println("Rental Days: " + returnTransaction.getActualDays());
        System.out.println("Rent Per Day: Rs."
                + rental.getVehicle().getRentPerDay());
        System.out.println("Total Amount: Rs." + totalAmount);
        System.out.println("Discount: Rs." + discountAmount);
        System.out.println("Final Amount: Rs." + finalAmount);
        System.out.println("=================================");
    }
}

// ================= MAIN CLASS =================
public class Main {

    static Scanner sc = new Scanner(System.in);

    static ArrayList<Vehicle> vehicles = new ArrayList<>();
    static ArrayList<Customer> customers = new ArrayList<>();
    static ArrayList<RentalTransaction> rentals = new ArrayList<>();
    static ArrayList<ReturnTransaction> returns = new ArrayList<>();
    static ArrayList<Bill> bills = new ArrayList<>();

    static int transactionId = 1;
    static int returnId = 1;

    // Read integer
    public static int readInt(String message) {
        while (true) {
            try {
                System.out.print(message);
                int value = Integer.parseInt(sc.nextLine());

                if (value <= 0) {
                    System.out.println("Enter a positive number!");
                } else {
                    return value;
                }

            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Enter numbers only.");
            }
        }
    }

    // Read decimal
    public static double readDouble(String message) {
        while (true) {
            try {
                System.out.print(message);
                double value = Double.parseDouble(sc.nextLine());

                if (!Double.isFinite(value) || value <= 0) {
                    System.out.println("Enter a valid positive amount!");
                } else {
                    return value;
                }

            } catch (NumberFormatException e) {
                System.out.println("Invalid amount!");
            }
        }
    }

    // Read text
    public static String readString(String message) {
        while (true) {
            System.out.print(message);
            String value = sc.nextLine().trim();

            if (!value.isEmpty()) {
                return value;
            }

            System.out.println("Input cannot be empty!");
        }
    }

    // Find vehicle
    public static Vehicle findVehicle(int id) {
        for (Vehicle v : vehicles) {
            if (v.getVehicleId() == id) {
                return v;
            }
        }
        return null;
    }

    // Find customer
    public static Customer findCustomer(int id) {
        for (Customer c : customers) {
            if (c.getCustomerId() == id) {
                return c;
            }
        }
        return null;
    }

    // Find rental
    public static RentalTransaction findRental(int id) {
        for (RentalTransaction r : rentals) {
            if (r.getTransactionId() == id) {
                return r;
            }
        }
        return null;
    }

    // Add vehicle
    public static void addVehicle() {
        System.out.println("\n1. Car");
        System.out.println("2. Bike");

        int type = readInt("Select vehicle type: ");

        if (type != 1 && type != 2) {
            System.out.println("Invalid vehicle type!");
            return;
        }

        int id = readInt("Enter Vehicle ID: ");

        if (findVehicle(id) != null) {
            System.out.println("Vehicle ID already exists!");
            return;
        }

        String brand = readString("Enter Vehicle Brand: ");
        double rent = readDouble("Enter Rent Per Day: ");

        Vehicle vehicle;

        if (type == 1) {
            vehicle = new Car(id, brand, rent);
        } else {
            vehicle = new Bike(id, brand, rent);
        }

        vehicles.add(vehicle);
        System.out.println("Vehicle added successfully!");
    }

    // Add customer
    public static void addCustomer() {
        int id = readInt("Enter Customer ID: ");

        if (findCustomer(id) != null) {
            System.out.println("Customer ID already exists!");
            return;
        }

        String name = readString("Enter Customer Name: ");

        System.out.println("1. Normal Customer");
        System.out.println("2. Premium Customer");

        int type = readInt("Select Customer Type: ");

        Customer customer;

        if (type == 1) {
            customer = new Customer(id, name);
        } else if (type == 2) {
            customer = new PremiumCustomer(id, name);
        } else {
            System.out.println("Invalid customer type!");
            return;
        }

        customers.add(customer);
        System.out.println("Customer added successfully!");
    }

    // Rent vehicle
    public static void rentVehicle() {
        int vehicleId = readInt("Enter Vehicle ID: ");
        Vehicle vehicle = findVehicle(vehicleId);

        if (vehicle == null) {
            System.out.println("Vehicle not found!");
            return;
        }

        if (!vehicle.isAvailable()) {
            System.out.println("Vehicle is already rented!");
            return;
        }

        int customerId = readInt("Enter Customer ID: ");
        Customer customer = findCustomer(customerId);

        if (customer == null) {
            System.out.println("Customer not found!");
            return;
        }

        int activeRentals = 0;

        for (RentalTransaction r : rentals) {
            if (r.getCustomer().getCustomerId() == customerId
                    && !r.isReturned()) {
                activeRentals++;
            }
        }

        if (activeRentals >= customer.getRentalLimit()) {
            System.out.println("Customer rental limit reached!");
            return;
        }

        int days = readInt("Enter Number of Rental Days: ");

        RentalTransaction rental = new RentalTransaction(
                transactionId++, vehicle, customer, days);

        rentals.add(rental);
        vehicle.setAvailable(false);

        System.out.println("\n===== RENTAL RECEIPT =====");
        System.out.println("Transaction ID: " + rental.getTransactionId());
        System.out.println("Customer: " + customer.getName());
        System.out.println("Vehicle: " + vehicle.getBrand());
        System.out.println("Rental Days: " + days);
        System.out.println("Rent Per Day: Rs." + vehicle.getRentPerDay());
        System.out.println("Vehicle rented successfully!");
        System.out.println("==========================");
    }

    // Return vehicle
    public static void returnVehicle() {
        int id = readInt("Enter Rental Transaction ID: ");

        RentalTransaction rental = findRental(id);

        if (rental == null) {
            System.out.println("Rental transaction not found!");
            return;
        }

        if (rental.isReturned()) {
            System.out.println("Vehicle already returned!");
            return;
        }

        int actualDays = readInt("Enter Actual Rental Days: ");

        rental.setReturned(true);
        rental.getVehicle().setAvailable(true);

        ReturnTransaction returnTransaction =
                new ReturnTransaction(returnId++, rental, actualDays);

        returns.add(returnTransaction);

        Bill bill = new Bill(returnTransaction);
        bills.add(bill);

        System.out.println("\nVehicle returned successfully!");
        System.out.println("Vehicle is now available.");

        bill.displayBill();
    }

    // Display available vehicles
    public static void displayVehicles() {
        System.out.println("\n===== AVAILABLE VEHICLES =====");

        boolean found = false;

        for (Vehicle v : vehicles) {
            if (v.isAvailable()) {
                v.display();
                found = true;
            }
        }

        if (!found) {
            System.out.println("No available vehicles!");
        }
    }

    // Generate bill
    public static void generateBill() {
        int id = readInt("Enter Rental Transaction ID: ");

        for (Bill bill : bills) {
            if (bill.getRentalTransactionId() == id) {
                bill.displayBill();
                return;
            }
        }

        System.out.println("Bill not found! Return the vehicle first.");
    }

    // Main method
    public static void main(String[] args) {

        while (true) {

            System.out.println("\n===== VEHICLE RENTAL SYSTEM =====");
            System.out.println("1. Add Vehicle");
            System.out.println("2. Add Customer");
            System.out.println("3. Rent Vehicle");
            System.out.println("4. Return Vehicle");
            System.out.println("5. Display Available Vehicles");
            System.out.println("6. Generate Bill");
            System.out.println("7. Exit");

            int choice = readInt("Enter your choice: ");

            switch (choice) {
                case 1:
                    addVehicle();
                    break;

                case 2:
                    addCustomer();
                    break;

                case 3:
                    rentVehicle();
                    break;

                case 4:
                    returnVehicle();
                    break;

                case 5:
                    displayVehicles();
                    break;

                case 6:
                    generateBill();
                    break;

                case 7:
                    System.out.println("Thank you for using Vehicle Rental System!");
                    sc.close();
                    return;

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}
