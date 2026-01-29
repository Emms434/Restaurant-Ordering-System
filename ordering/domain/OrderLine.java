public class Orderline {
    int quantity;
    Item item;

    public Orderline(int quantity, Item item) {
        this.quantity = quantity;
        this.item = item;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getSubtotal() {
        return item.getPrice() * quantity;
    }
    public void increaseQuantity(){
        this.quantity++;
    };
    public void decreaseQuantity(){
        if(this.quantity > 0)  this.quantity--;
    };
    @Override
    public String toString() {
        return item.getName() + " x" + quantity + " - $" +
                String.format("%.2f", getSubtotal());
    }
}
