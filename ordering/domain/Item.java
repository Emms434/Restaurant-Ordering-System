import java.util.Objects;

public class Item {
        String Name;
        Double Price;
        String Description;


        public Item(String Name, String Description, double Price){
            this.Name = Name;
            this.Description = Description;
            this.Price = Price;

        }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;          // same object
        if (o == null) return false;
        if (getClass() != o.getClass()) return false;

        Item item = (Item) o;
        return Objects.equals(Name, item.Name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Name);
    }

    public String toString(){
            return String.format("Name: %s, Description: %s Price: $%.2f", Name,Description, Price);

    }

    public double getPrice() {
            return Price;
    }

    public void setName(String name){
        this.Name = name;
    }
    public String getName(){
        return Name;
    }
}

