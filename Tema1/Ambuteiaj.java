/**
 * clasa ambuteiaj este clasa din care se extind cele 3 tipuri de ambuteiaje, accident, trafic
 * si blocaj. Contine 2 tipuri de date, reprezentand costul ambuteiajului si urmatoarea restrictie
 * de circulatie. Co
 */
public class Ambuteiaj {
    protected int cost;
    protected Ambuteiaj next;

    public Ambuteiaj(){
    }

    public int getCost() {
        return cost;
    }

    public Ambuteiaj getNext() {
        return next;
    }
}