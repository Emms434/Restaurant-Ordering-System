import java.util.HashMap;
public class Order {

    //Hashmap to store list of customer orders
    Menu menu;
    HashMap<Item, Integer>  newOrder = new HashMap<>();

    public Order(Menu menu) {
        this.menu = menu;
        this.newOrder = new HashMap<>();
    }

    //Use FindItem to get the Item from Menu


    public void addItem(String itemName) {
        Item itemfound = menu.FindItem(itemName);
        if (itemfound == null) {
            System.out.println("Item not found");

        } else {
            if (newOrder.containsKey(itemfound)) {
                int quantity = newOrder.get(itemfound);
                newOrder.put(itemfound, quantity + 1);
            }
            else {
                newOrder.put(itemfound, 1);
            }

        }
    }

    public void removeItem(String itemName) {
        Item itemfound = menu.FindItem(itemName);
        if (itemfound != null) {
            int quantity = newOrder.get(itemfound);
            if (quantity > 1) {
                newOrder.put(itemfound, quantity - 1);
            }
            else {
                newOrder.remove(itemfound);
            }


            System.out.println(itemName + " has been removed");
        }
    }

    public double CalculateTotal() {
        double total = 0;
        for(Item item : newOrder.keySet()) {
            double subtotal = item.Price *  newOrder.get(item);
            total += subtotal;
        }
        return total;
    }

    public void printOrder() {
        System.out.println("----- Customer Order -----");

        for (Item item : newOrder.keySet()) {
            int quantity = newOrder.get(item);
            double subtotal = item.Price * quantity;
            System.out.printf("%s (%s) x%d - $%.2f%n", item.Name, item.Description, quantity, subtotal);
        }

        double total = CalculateTotal();
        System.out.printf("Total Order Price: $%.2f%n", total);
    }





    //addItem(Item item) → add an item to the order.
        //removeItem(Item item) → optional, remove if the customer changes their mind.
        //calculateTotal() → sum up all item prices.
        //printOrder() → display each item using toString() and show total price



}


