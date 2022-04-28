/**
 * clasa ce reprezinta un tip de ambuteiaj si se extinde din acesta
 */
class Trafic extends Ambuteiaj {

    public Trafic(){
    }

    public Trafic(int cost){
        this.cost = cost;
        this.next = null;
    }

}