import java.security.spec.RSAOtherPrimeInfo;
import java.time.LocalDateTime;

/**
 * Clasa ce reprezinta discounturile existenta in magazin.
 */
public class Discount {
    private String name;
    private DiscountType discountType;
    private double value;
    private LocalDateTime lastDateApplied = null;

    public Discount(String name, DiscountType discountType, double value) {
        this.name = name;
        this.discountType = discountType;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public DiscountType getDiscountType() {
        return discountType;
    }

    public double getValue() {
        return value;
    }

    public void setAsAppliedNow(){
        lastDateApplied = LocalDateTime.now();
    }

    /**
     * Afiseaza tipul discountului, valoarea, numele si ultima data cand a fost accesat.
     */
    public void print(){
        System.out.print(discountType + " " + value + " " + name);
        if(lastDateApplied != null)
            System.out.println(" " + lastDateApplied);
        else
            System.out.println();
    }
}
