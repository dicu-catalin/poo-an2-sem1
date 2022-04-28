/**
 * clasa ce reprezinta un tip de ambuteiaj si se extinde din acesta
 */
class Blocaj extends Ambuteiaj {

    public Blocaj(){
    }

    public Blocaj(int cost){
        this.cost = cost;
        this.next = null;
    }
}