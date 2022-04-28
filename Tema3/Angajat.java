/**
 * Clasa din care sunt mostenite obiectele ce pot face modificari in casa de licitatii
 */
public class Angajat {
    protected String nume;
    protected CasaDeLicitatii casa;

    public Angajat(String nume, CasaDeLicitatii casa) {
        this.nume = nume;
        this.casa = casa;
    }

    public String toString(){
        return nume;
    }
}
