/**
 * Clasa ce reprezinta valuta existenta in magazin
 */
public class Currency {
    private String name;
    private String symbol;
    private double parityToEur;

    public Currency(String name, String symbol, double parityToEur) {
        this.name = name;
        this.symbol = symbol;
        this.parityToEur = parityToEur;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getName() {
        return name;
    }

    public double getParityToEur() {
        return parityToEur;
    }

    public void setParityToEur(double parityToEur) {
        this.parityToEur = parityToEur;
    }
}
