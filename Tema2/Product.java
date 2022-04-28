/**
 * Clasa ce reprezinta un produs din magazin.
 */
public class Product {
    private final String uniqueId;
    private final String name;
    private final Manufacturer manufacturer;
    private final double price;
    private final int quantity;
    private Discount discount;

    private Product(ProductBuilder builder) {
        this.uniqueId = builder.uniqueId;
        this.name = builder.name;
        this.manufacturer = builder.manufacturer;
        this.price = builder.price;
        this.quantity = builder.quantity;
    }

    /**
     * Inner class, folosita pentru a instantia un obiect de tip produs.
     */
    public static class ProductBuilder{
        private final String uniqueId;
        private final String name;
        private final Manufacturer manufacturer;
        private final double price;
        private final int quantity;

        public ProductBuilder(String uniqueId, String name, Manufacturer manufacturer, double price, int quantity) {
            this.uniqueId = uniqueId;
            this.name = name;
            this.manufacturer = manufacturer;
            this.price = price;
            this.quantity = quantity;
        }

        public Product build(){
            return new Product(this);
        }
    }

    /**
     * Afiseaza informatii despre un produs.
     * @param currency  valuta folosita de magazin
     * @return          intoarce un string ce contine informatiile despre produs
     */
    public String print(Currency currency){
        return uniqueId + ", " + name + ", " + manufacturer.getName() + ", "
                + currency.getSymbol() + getPrice(currency) + ", " + quantity;
    }

    /**
     * Calculeaza pretul unui produs dupa ce aplica discountul si se converteste in valuta din magazin.
     * @param currency  valuta principala a magazinului
     * @return          intoarce pretul produsului
     */
    public double getPrice(Currency currency) {
        double finalPrice = price * currency.getParityToEur();
        if(discount != null && discount.getDiscountType() == DiscountType.PERCENTAGE_DISCOUNT)
            finalPrice = price * (100 - discount.getValue()) / 100;
        else if(discount != null) {
            finalPrice = price - discount.getValue();
            if(finalPrice < 0)
                return 0;
        }
        finalPrice = Math.floor(finalPrice * 10000) / 10000;
        return finalPrice;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public Discount getDiscount() {
        return discount;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    public void setDiscount(Discount discount) {
        this.discount = discount;
    }
}
