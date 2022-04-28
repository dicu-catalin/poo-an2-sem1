/**
 * Mosteneste clasa Produs si este unul dintre produsele vandute de casa de licitatii.
 */
public class Tablou extends Produs{
    private String numePictor;
    private Culori culori;

    public Tablou(int id, String nume, double pretMinim, int an, String numePictor, Culori culori) {
        super(id, nume, pretMinim, an);
        this.numePictor = numePictor;
        this.culori = culori;
    }

    public String toString(){
        return "Tablou: " + nume + ", pictor: " + numePictor + ", culori:" + culori + ", an: " +
                an + ", pret minim: " + pretMinim + ", id: " + id;
    }
}
