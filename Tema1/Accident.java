/**
 * clasa ce reprezinta un tip de ambuteiaj si se extinde din acesta
 */
class Accident extends Ambuteiaj {

    public Accident() {
    }

    public Accident(int cost) {
        this.cost = cost;
        this.next = null;
    }
}