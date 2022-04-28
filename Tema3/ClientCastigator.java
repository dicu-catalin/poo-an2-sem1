/**
 * Clasa care decoreaza un obiect de tipul client, astfel incat sa afiseze produsele castigate de
 * fiecare persoana.
 */
public class ClientCastigator extends Client {
    private Client clientCastigator;
    private Produs produs;
    @Override
    public void setPretMaximOferit(int idProdus) {

    }

    public ClientCastigator(Client client, Produs produs){
        super();
        clientCastigator = client;
    }

    public String toString(){
        return clientCastigator.getNume() + " a castigat produsul " + produs;
    }
}
