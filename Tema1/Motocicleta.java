/**
 * clasa ce se extinde din clasa vehicul si reprezinta un tip al acestuia
 */
class Motocicleta extends Vehicle {
    public Motocicleta(){
        super();
        this.tip = "moped";
        this.gabarit = 1;
        this.cost = 2;
    }
}