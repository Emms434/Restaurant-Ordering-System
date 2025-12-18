import java.util.ArrayList;

public class Menu {
    ArrayList<Item> Menu = new ArrayList<>();

    //Searching the menu arrayList for an item per request from customer

    public Item FindItem(String ItemName) {
        for (Item neworder : Menu){
            if(ItemName.equals(neworder.Name)){
                return neworder;

            }
            }
        System.out.println("Item not found");
        return null;
    }

}
