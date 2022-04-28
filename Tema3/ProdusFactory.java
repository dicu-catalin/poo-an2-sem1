/**
 * Clasa ce foloseste design pattern-ul factory pentru a instantia obiecte ce mostenesc clasa Produs
 */
public class ProdusFactory {
    private static ProdusFactory instantaUnica;

    private ProdusFactory(){

    }

    public static ProdusFactory Instanta(){
        if(instantaUnica == null)
            instantaUnica = new ProdusFactory();
        return instantaUnica;
    }


    /**
     * Metoda ce intoarce obiectele corespunzatoare
     * @param info  contine informatiile despre produs
     * @param id    id-ul produsului
     * @return      Intoarce un obiect nou, ce mosteneste clasa Produs
     */
    public Produs creeazaProdus(String[] info, int id){
        String numeProdus;
        double pretMinim;
        int an;
        String tipProdus;
        tipProdus = info[0];
        numeProdus = info[1];
        pretMinim = Double.parseDouble(info[2]);
        an = Integer.parseInt(info[3]);
        if(tipProdus.contains("tablou")) {
            String numePictor;
            Culori culori;
            String culoare;
            numePictor = info[4];
            culoare = info[5];
            if (culoare.contains("ULEI"))
                culori = Culori.ULEI;
            else if (culoare.contains("TEMPERA"))
                culori = Culori.TEMPERA;
            else
                culori = Culori.ACRILIC;
            return new Tablou(id, numeProdus, pretMinim, an, numePictor, culori);
        }
        else if(tipProdus.contains("mobila"))
            return new Mobila(id, numeProdus, pretMinim, an, info[4], info[5]);
        else
            return new Bijuterie(id, numeProdus, pretMinim, an, info[4],
                    Boolean.getBoolean(info[5]));
    }
}
