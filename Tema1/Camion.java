/**
 * clasa ce se extinde din clasa vehicul si reprezinta un tip al acestuia
 */
class Camion extends Vehicle {
    public Camion(){
        super();
        this.tip = "Autoutilitar";
        this.gabarit = 3;
        this.cost = 6;
    }
}