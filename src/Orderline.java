public class Orderline {
    int quantity;
    Item item;
    public Orderline(int quantity, Item item) {
        this.quantity = quantity;
        this.item = item;
    }

    public void increaseQuantity(int quantity) {
        this.quantity +=1 ;
    }
    public void decreaseQuantity(int quantity) {
        this.quantity -=1 ;
    }
}
