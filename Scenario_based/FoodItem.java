import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

// IOrderable Interface
interface IOrderable {
    boolean placeOrder();
    boolean cancelOrder();
}

// Base FoodItem Class
abstract class FoodItem {
    private int stockLevel;
    protected String name;
    protected String category;
    protected double price;

    public FoodItem(String name, String category, double price, int initialStock) {
        this.name = name;
        this.category = category;
        this.price = price;
        this.stockLevel = initialStock;
    }

    public FoodItem(String name, String category, double price) {
        this(name, category, price, 10);
    }

    public boolean isAvailable() {
        return stockLevel > 0;
    }

    public int getStockLevel() {
        return stockLevel;
    }

    public boolean reduceStock(int quantity) {
        if (stockLevel >= quantity) {
            stockLevel -= quantity;
            return true;
        }
        return false;
    }

    public void restock(int quantity) {
        if (quantity > 0) {
            stockLevel += quantity;
        }
    }

    public abstract double applyDiscount();

    public String getName() { return name; }
    public double getPrice() { return price; }
}

// Derived FoodItem Types
class VegItem extends FoodItem {
    private boolean isOrganic;

    public VegItem(String name, String category, double price, boolean isOrganic, int stock) {
        super(name, category, price, stock);
        this.isOrganic = isOrganic;
    }

    public VegItem(String name, String category, double price, boolean isOrganic) {
        this(name, category, price, isOrganic, 10);
    }

    @Override
    public double applyDiscount() {
        return isOrganic ? price * 0.9 : price;
    }
}

class NonVegItem extends FoodItem {
    private String meatType;

    public NonVegItem(String name, String category, double price, String meatType, int stock) {
        super(name, category, price, stock);
        this.meatType = meatType;
    }

    public NonVegItem(String name, String category, double price, String meatType) {
        this(name, category, price, meatType, 10);
    }

    @Override
    public double applyDiscount() {
        return price * 0.95;
    }
}

// Order Class
class Order implements IOrderable {
    private List<FoodItem> items;
    private double totalPrice;
    private String orderId;
    private java.util.Date orderTime;
    private String status;

    public Order() {
        this.items = new ArrayList<>();
        this.orderId = generateOrderId();
        this.orderTime = new java.util.Date();
        this.status = "Pending";
    }

    public Order(List<FoodItem> initialItems) {
        this();
        if (initialItems != null) {
            this.items.addAll(initialItems);
            calculateTotal();
        }
    }

    private String generateOrderId() {
        return "ORD" + System.currentTimeMillis();
    }

    public void addItem(FoodItem item, int quantity) {
        for (int i = 0; i < quantity; i++) {
            items.add(item);
        }
        calculateTotal();
    }

    public void addItem(FoodItem item) {
        addItem(item, 1);
    }

    public void removeItem(String itemName) {
        items.removeIf(item -> item.getName().equals(itemName));
        calculateTotal();
    }

    private void calculateTotal() {
        totalPrice = items.stream().mapToDouble(FoodItem::applyDiscount).sum();
    }

    public double getTotal() {
        return totalPrice;
    }

    public double applyOrderDiscount(double discountPercent) {
        return totalPrice * (1 - discountPercent / 100);
    }

    @Override
    public boolean placeOrder() {
        // Check stock for all items
        for (FoodItem item : items.stream().distinct().collect(Collectors.toList())) {
            long count = items.stream().filter(i -> i.equals(item)).count();
            if (item.getStockLevel() < count) {
                System.out.println("Insufficient stock for " + item.getName());
                return false;
            }
        }

        // Reduce stock
        for (FoodItem item : items.stream().distinct().collect(Collectors.toList())) {
            long count = items.stream().filter(i -> i.equals(item)).count();
            item.reduceStock((int) count);
        }

        status = "Confirmed";
        System.out.println("Order " + orderId + " placed successfully. Total: $" + totalPrice);
        return true;
    }

    @Override
    public boolean cancelOrder() {
        if ("Confirmed".equals(status)) {
            // Restock items
            for (FoodItem item : items.stream().distinct().collect(Collectors.toList())) {
                long count = items.stream().filter(i -> i.equals(item)).count();
                item.restock((int) count);
            }
            status = "Cancelled";
            System.out.println("Order " + orderId + " cancelled");
            return true;
        }
        return false;
    }

    public void displayOrder() {
        System.out.println("Order ID: " + orderId);
        System.out.println("Status: " + status);
        System.out.println("Items: " + items.size());
        System.out.println("Total: $" + totalPrice);
    }
}

// Combo Meal Class
class ComboMeal extends FoodItem {
    private List<FoodItem> comboItems;

    public ComboMeal(String name, List<FoodItem> items) {
        super(name, "Combo", items.stream().mapToDouble(FoodItem::getPrice).sum() * 0.8);
        this.comboItems = new ArrayList<>(items);
    }

    @Override
    public double applyDiscount() {
        return price;
    }

    public void displayComboDetails() {
        System.out.println("Combo: " + name);
        String itemNames = comboItems.stream()
                .map(FoodItem::getName)
                .collect(Collectors.joining(", "));
        System.out.println("Includes: " + itemNames);
        double originalPrice = comboItems.stream().mapToDouble(FoodItem::getPrice).sum();
        System.out.println("Combo Price: $" + price + " (Save $" + (originalPrice - price) + ")");
    }
}