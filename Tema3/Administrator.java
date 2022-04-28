/**
 * Mosteneste clasa Angajat si poate sa adauge produse in lista de produse a site-ului.
 */
public class Administrator extends Angajat {
    public Administrator(String nume, CasaDeLicitatii casa) {
        super(nume, casa);
    }

    public void adaugaProdus(Produs produs){
        casa.produse.add(produs);
    }
}
