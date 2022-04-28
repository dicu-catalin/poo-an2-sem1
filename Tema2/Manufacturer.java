/**
 * Clasa ce reprezinta un producator.
 */
public class Manufacturer {
    private String name;
    private int countProducts;

    public Manufacturer(String name, int countProducts) {
        this.name = name;
        this.countProducts = countProducts;
    }

    public String getName() {
        return name;
    }

    /**
     * Creste numarul de produse pe care le care producatorul in magazin
     */
    public void increaseProducts(){
        countProducts++;
    }

    /**
     * Afiseaza numele producatorului.
     */
    public void print(){
        System.out.println(name);
    }
}
