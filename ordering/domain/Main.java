// Main.java
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        // -----------------------------
        // 1️⃣ Create sample menu items
        // -----------------------------
        Item sandwich = new Item("Slapping Cheese Sandwich",
                "Creamy and cheesy falafel mix",
                15.70);
        Item drink = new Item("Vanilla Sunday",
                "Creamy vanilla and strawberry mix",
                3.05);
        Item steak = new Item("Steak",
                "Freshly cooked medium rare steak",
                27.50);
        Item burger = new Item("Burger",
                "Cheesy beef burger",
                14.50);

        // -----------------------------
        // 2️⃣ Create a Menu and add items
        // -----------------------------
        Menu myMenu = new Menu();
        myMenu.Menu.add(sandwich);
        myMenu.Menu.add(burger);
        myMenu.Menu.add(steak);
        myMenu.Menu.add(drink);

        // -----------------------------
        // 3️⃣ Create orders for two tables
        // -----------------------------
        Order table1 = new Order(myMenu); // Pass menu to the order
        Order table2 = new Order(myMenu);
        //If you didnt create constructor, you could also do table1.menu = myMenu to give it that menu specifically

        // -----------------------------
        // 4️⃣ Add items to each order
        // -----------------------------
        table1.addItem("Burger");
        table1.addItem("Steak");
        table1.addItem("Burger"); // Duplicate test: quantity increments

        table2.addItem("Vanilla Sunday");
        table2.addItem("Fries"); // Not in menu → prints "Item not found"

        // -----------------------------
        // 5️⃣ Remove items from orders
        // -----------------------------
        table1.removeItem("Burger"); // Quantity should decrement
        table2.removeItem("Vanilla Sunday"); // Only item, should remove completely
        table2.removeItem("Fries"); // Not in order → handled gracefully

        // -----------------------------
        // Print each table's order
        // -----------------------------
        System.out.println("\nTable 1 Order:");
        table1.printOrder();

        System.out.println("\nTable 2 Order:");
        table2.printOrder();

        // -----------------------------
        // Print totals for each table
        // -----------------------------

        // -----------------------------
        // Optional: Print full menu
        // -----------------------------
        System.out.println("\nFull Menu:");
        for (Item item : myMenu.Menu) {
            System.out.println(item.Name + " - $" + item.Price);
        }
    }
}
