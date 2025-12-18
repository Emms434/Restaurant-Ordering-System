public class Item {
        String Name;
        Double Price;
        String Description;


        public Item(String Name, String Description, double Price){
            this.Name = Name;
            this.Description = Description;
            this.Price = Price;



    }

    public String toString(){
            return String.format("Name: %s, Description: %s Price: $%.2f", Name,Description, Price);

    }

}

