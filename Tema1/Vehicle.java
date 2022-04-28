/**
 * clasa din care se extind cele 4 tipuri de vehicule. Contine 3 tipuri de date protected pentru
 * a fi vazute de clasele care o mostenesc: tipul
 */
public class Vehicle {
    protected String tip;
    protected int gabarit;
    protected int cost;

    public Vehicle(){

    }

    public String getTip() {
        return tip;
    }

    public int getGabarit() {
        return gabarit;
    }

    public int getCost() {
        return cost;
    }
}