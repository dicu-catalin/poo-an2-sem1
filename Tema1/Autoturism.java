/**
 * clasa ce se extinde din clasa vehicul si reprezinta un tip al acestuia
 */
class Autoturism extends Vehicle {
    public Autoturism(){
        super();
        this.tip = "Autovehicul";
        this.gabarit = 2;
        this.cost = 4;
    }
}