import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Clasa principala ce reprezinta magazinul(de tip singleton) care contine obiectele din fisier.
 */

public class Store {
    public String name;
    public Currency currency;
    public List<Currency> currencies = new ArrayList<>();
    public List<Product> products = new ArrayList<>();
    public List<Manufacturer> manufacturers = new ArrayList<>();
    public List<Discount> discounts = new ArrayList<>();
    private static Store instance;

    String[] HEADERS = {"uniq_id", "product_name", "manufacturer", "price", "number_available_in_stock"};

    /**
     * Constructorul de baza care creeaza magazinul, ii da nume si initiaza valuta ca fiind euro
     * @param name  numele magazinului
     */
    private Store(String name) {
        this.name = name;
        currency = new Currency("EUR", "€", 1.0);
        currencies.add(currency);
    }

    /**
     * Metoda statica care permite instantierea unui singur magazin
     * @param name  numele magazinului
     * @return  Intoarce instanta unica de tip store
     */
    public static Store Instance(String name){
        if(instance == null)
            instance = new Store(name);
        return instance;
    }

    /**
     * Metoda care incarca produsele dintr-un fisier csv.
     * @param filename      numele fisierului din care sunt luate produsele
     * @throws IOException  exceptie daca numele fisierului nu exista
     * @throws CurrencyNotFoundException    exceptie daca nu exista valuta din fisierul cu produse
     */
    void readCSV(String filename) throws IOException, CurrencyNotFoundException {
        String price;
        Reader in = new FileReader(filename);
        CSVParser records = CSVFormat.DEFAULT
                .withHeader(HEADERS)
                .withFirstRecordAsHeader()
                .parse(in);

        for (CSVRecord record : records) {
            // simbolul pentru valuta din fisierul csv
            String symbol = String.valueOf(record.get("price").charAt(0));
            int ok = 1;
            double realPrice;
            String quantity;
            int realQuantity;
            Manufacturer currManufacturer = null;
            String id = record.get("uniq_id");
            String manufacturerName = record.get("manufacturer");
            Currency currency;
            // verifica daca produsul are un manufacturer corespunzator
            if (manufacturerName.equals(""))
                manufacturerName = "Not Available";
            // verifica daca produsul exista deja, si ok se face 0 in acest caz
            if(findProduct(id) != null)
                ok = 0;
            // daca produsul exista deja, se trece la urmatorul produs
            if (ok == 0)
                continue;
            // verifica daca exista deja acest manufacturer
            for (Manufacturer manufacturer : manufacturers)
                if (manufacturer.getName().equals(manufacturerName)) {
                    manufacturer.increaseProducts();
                    ok = 0;
                    currManufacturer = manufacturer;
                    break;
                }
            // daca nu exista, creeaza un nou obiect si il adauga in lista
            if (ok == 1) {
                currManufacturer = new Manufacturer(manufacturerName, 1);
                manufacturers.add(currManufacturer);
            }
            // sterge din stringul pretului simbolul pentru valuta
            price = record.get("price").split("£")[1];
            realPrice = 0;
            // formateaza pretul
            for (String a : price.split(",")) {
                realPrice *= 1000;
                realPrice = Double.parseDouble(a);
            }
            // cauta valuta din fisier, dupa simbol
            currency = findCurrency(symbol);
            // daca nu exista, produsele nu pot fi instantiate
            if(currency == null)
                throw new CurrencyNotFoundException();
            // transforma pretul in euro
            realPrice = realPrice * currency.getParityToEur();
            quantity = record.get("number_available_in_stock");
            realQuantity = 0;
            // citeste cantitatea din stoc
            for (int i = 0; i < quantity.length(); i++) {
                if (quantity.charAt(i) < '0' || quantity.charAt(i) > '9')
                    break;
                realQuantity *= 10;
                realQuantity += quantity.charAt(i) - '0';
            }
            // adauga noul produs in lista de produse
            products.add(new Product.ProductBuilder(id, record.get("product_name"),
                    currManufacturer, realPrice, realQuantity).build());
        }
    }

    /**
     * Verifica daca exista o valuta(dupa simbol) in lista de currencies.
     * @param symbol    simbolul valutei
     * @return          intoarce valuta in cazul in care exista
     */
    Currency findCurrency(String symbol){
        for(Currency currency : currencies)
            if(currency.getSymbol().equals(symbol))
                return currency;
        return null;
    }

    /**
     * Cauta un produs in lista de produse.
     * @param id    id-ul produsului cautat
     * @return      intoarce produsul, in cazul in care exista
     */
    Product findProduct(String id){
        for (Product product : products)
            if (product.getUniqueId().equals(id))
                return product;
        return null;
    }

    /**
     * Parcurge lista de valute si afiseaza fiecare currency.
     */
    void listCurrencies() {
        for (Currency currency : currencies)
            System.out.println(currency.getName() + " " + currency.getParityToEur());
    }

    /**
     * Parcurge lista de produse si afiseaza fiecare produs.
     */
    void listProducts() {
        for (Product product : products)
            System.out.println(product.print(currency));
    }

    /**
     * Cauta o valuta(dupa nume), iar acesta devine valuta principala din magazin.
     * @param currencyName  numele valutei
     * @throws CurrencyNotFoundException    eroare in cazul in care valuta nu e gasita
     */
    void setCurrency(String currencyName) throws CurrencyNotFoundException {
        for (Currency currency : currencies)
            if (currency.getName().equals(currencyName)) {
                this.currency = currency;
                return;
            }
        throw new CurrencyNotFoundException();
    }

    /**
     * Cauta un produs dupa id si il afiseaza.
     * @param id    id-ul produsului cautat
     */
    void showProduct(String id) {
        Product product = findProduct(id);
        if(product != null)
            System.out.println(product.print(currency));
    }

    /**
     * Afiseaza fiecare producator.
     */
    void listManufacturers() {
        for (Manufacturer manufacturer : manufacturers)
            manufacturer.print();
    }

    /**
     * Cauta si afiseaza toate produsele fabricate de un anumit producator.
     * @param nameManufacturer  numele producatorului
     */
    void listProductsByManufacturer(String nameManufacturer) {
        for (Product product : products)
            if (product.getManufacturer().getName().equals(nameManufacturer))
                System.out.println(product.print(currency));
    }

    /**
     * Afiseaza fiecare discount din magazin.
     */
    void listDiscounts() {
        for (Discount discount : discounts)
            discount.print();
    }

    /**
     * Creeaza si adauga un nou discount in lista.
     * @param discountType  tipul discountului
     * @param name          numele discountului
     * @param value         valoarea discountului
     */
    void createDiscount(DiscountType discountType, String name, double value) {
        discounts.add(new Discount(name, discountType, value));
    }

    /**
     * Aplica un anumit discount tuturor produselor.
     * @param discount  discountul aplicat
     * @throws DiscountNotFoundException    eroare daca nu exista discountul
     * @throws NegativePriceException       eroare daca pretul unui produs este negativ
     */
    void applyDiscount(Discount discount) throws DiscountNotFoundException, NegativePriceException{
        if(discount == null){
            throw new DiscountNotFoundException();}
        discount.setAsAppliedNow();
        for(Product product : products){
            if(product.getPrice(currency) < 0)
                throw new NegativePriceException();
            product.setDiscount(discount);
        }

    }

    /**
     * Cauta un anumit discount dupa tipul si numele acestuia.
     * @param discountType  tipul discountului
     * @param value         valoarea discountului
     * @return              intoarce discountul daca il gaseste
     */
    Discount findDiscount(DiscountType discountType, double value){
        for(Discount discount : discounts)
            if(discount.getDiscountType().equals(discountType) && discount.getValue() == value)
                return discount;
        return null;
    }

    /**
     * Calculeaza pretul produselor care au id-urile corespunzatoare
     * @param command   stringul in care se afla id-urile
     * @return          intoarce valoarea totala
     */
    public double calculateTotal(String command){
        String[] productsID = command.split(" ");
        double totalValue = 0;
        for(int i = 1; i < productsID.length; i++){
            Product product = findProduct(productsID[i]);
            if(product != null)
                totalValue += product.getPrice(currency);
        }
        // rotunjire la 4 zecimale, deoarece apareau valori dubioase la a 10-a zecimala
        totalValue = Math.floor(totalValue * 10000) / 10000;
        return totalValue;
    }

    /**
     * Metoda ce scrie intr-un fisier csv starea curenta a magazinului
     * @param filename      numele fisierului in care scrie
     * @throws IOException  exceptie in cazul in care fisierul nu se poate deschide
     */
    void saveCSV(String filename) throws IOException {
        CSVPrinter printer = new CSVPrinter(new FileWriter(filename), CSVFormat.DEFAULT);
        printer.printRecord("uniq_id", "product_name", "manufacturer", "price", "number_available_in_stock");
        for(Product product : products) {
            printer.printRecord(product.getUniqueId(), product.getName(),
                    product.getManufacturer().getName(), currency.getSymbol() + product.getPrice(currency),
                    product.getQuantity());
        }
        printer.close();
    }

    /*void loadStore(String filename) throws IOException{

    }

    void saveStore(String filename) throws IOException{

    }*/

    class CurrencyNotFoundException extends Exception {
        public CurrencyNotFoundException() {
            super("Valuta nu a fost gasita");
        }
    }

    class DiscountNotFoundException extends Exception {
        public DiscountNotFoundException() {
            super("Discountul nu a gost gasit");
        }
    }

    class DuplicateManufacturerException extends Exception {
        public DuplicateManufacturerException() {
            super("Producatorul exista deja");
        }
    }

    class DuplicateProductException extends Exception {
        public DuplicateProductException() {
            super("Produsul exita deja");
        }
    }

    class NegativePriceException extends Exception {
        public NegativePriceException() {
            super("Pretul produsului este negativ");
        }
    }

    public static void main(String[] args) throws IOException {
        Store store = Instance("Amazon");
        Scanner s = new Scanner(System.in);
        String command = s.nextLine();
        while (!command.equals("quit") && !command.equals("exit")) {
            try {
                System.out.println(command);
                if(command.contains("loadcsv"))
                    store.readCSV(command.split(" ")[1]);
                else if (command.equals("listcurrencies"))
                    store.listCurrencies();
                else if (command.contains("addcurrency")) {
                    String name = command.split(" ")[1];
                    String symbol = command.split(" ")[2];
                    double parity = Double.parseDouble(command.split(" ")[3]);
                    store.currencies.add(new Currency(name, symbol, parity));
                }
                else if (command.contains("updateparity")) {
                    String currencyName = command.split(" ")[1];
                    double currentValue = Double.parseDouble(command.split(" ")[2]);
                    for (Currency currency : store.currencies)
                        if (currency.getName().equals(currencyName))
                            currency.setParityToEur(currentValue);
                }
                else if (command.equals("listproducts")) {
                    store.listProducts();
                }
                else if (command.equals("getstorecurrency"))
                    System.out.println(store.currency.getName());
                else if (command.contains("setstorecurrency"))
                    store.setCurrency(command.split(" ")[1]);
                else if (command.contains("showproduct"))
                    store.showProduct(command.split(" ")[1]);
                else if (command.equals("listmanufacturers"))
                    store.listManufacturers();
                else if (command.contains("listproductsbymanufacturer"))
                    store.listProductsByManufacturer(command.split(" ")[1]);
                else if (command.contains("listdiscounts"))
                    store.listDiscounts();
                else if (command.contains("addiscount")) { // de vazut
                    String[] values = command.split(" ");
                    DiscountType discountType;
                    StringBuilder nameDiscount = new StringBuilder();
                    if (command.contains("PERCENTAGE"))
                        discountType = DiscountType.PERCENTAGE_DISCOUNT;
                    else
                        discountType = DiscountType.FIXED_DISCOUNT;
                    for(int i = 3; i < values.length; i++)
                        nameDiscount.append(values[i]).append(" ");
                    double valueDiscount = Double.parseDouble(values[2]);
                    store.createDiscount(discountType, nameDiscount.toString(), valueDiscount);
                }
                else if (command.contains("applydiscount")) {
                    DiscountType discountType;
                    double value;
                    Discount discount;
                    if(command.split(" ")[1].equals("PERCENTAGE"))
                        discountType = DiscountType.PERCENTAGE_DISCOUNT;
                    else
                        discountType = DiscountType.FIXED_DISCOUNT;
                    value = Double.parseDouble(command.split(" ")[2]);
                    discount = store.findDiscount(discountType, value);
                    store.applyDiscount(discount);
                }
                else if (command.contains("calculatetotal"))
                    System.out.println(store.calculateTotal(command));
                else if(command.contains("savecsv"))
                    store.saveCSV("test.csv");
            } catch (DiscountNotFoundException | NegativePriceException
                    | CurrencyNotFoundException | FileNotFoundException e) {
                e.getMessage();
            } catch (Exception e){
                System.out.println("O alta eroare a aparut");
            } finally {
                command = s.nextLine();
            }
        }
        s.close();
    }
}
