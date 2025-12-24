import java.util.HashMap;

public class Order {

    private Menu menu;
    private HashMap<Item, Orderline> orderlines;

    public Order(Menu menu) {
        this.menu = menu;
        this.orderlines = new HashMap<>();
    }

    // Add item by name
    public void addItem(String itemName) {
        Item itemFound = menu.FindItem(itemName);

        if (itemFound == null) {
            System.out.println("Item not found");
            return;
        }

        if (orderlines.containsKey(itemFound)) {
            Orderline line = orderlines.get(itemFound);
            line.increaseQuantity();
        } else {
            orderlines.put(itemFound, new Orderline(1, itemFound));
        }
    }

    // Remove item by name
    public void removeItem(String itemName) {
        Item itemFound = menu.FindItem(itemName);

        if (itemFound == null || !orderlines.containsKey(itemFound)) {
            System.out.println("Item not in order");
            return;
        }

        Orderline line = orderlines.get(itemFound);
        line.decreaseQuantity();

        if (line.getQuantity() == 0) {
            orderlines.remove(itemFound);
        }

        System.out.println(itemName + " has been removed");
    }

    // Calculate total order price
    public double calculateTotal() {
        double total = 0;

        for (Orderline line : orderlines.values()) {
            total += line.getSubtotal();
        }

        return total;
    }

    // Print order summary
    public void printOrder() {
        System.out.println("----- Customer Order -----");

        for (Orderline line : orderlines.values()) {
            System.out.println(line);
        }

        System.out.printf("Total Order Price: $%.2f%n", calculateTotal());
    }
}
