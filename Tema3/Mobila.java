/**
 * Mosteneste clasa Produs si este unul dintre produsele vandute de casa de licitatii.
 */
public class Mobila extends Produs {
    private final String tip;
    private final String material;

    public Mobila(int id, String nume, double pretMinim, int an, String tip, String material) {
        super(id, nume, pretMinim, an);
        this.tip = tip;
        this.material = material;
    }

    public String toString(){
        return "Mobila: " + nume + ", " + tip + ", material: " + material + ", an: " + an +
                ", pret minim: " + pretMinim + ", id: " + id;
    }
}
