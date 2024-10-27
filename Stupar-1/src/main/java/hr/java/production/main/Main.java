package hr.java.production.main;

import hr.java.restaurant.model.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Main {
    private static final Integer numberOfCategories = 3;
    private static final Integer numberOfIngredients = 5;
    private static final Integer numberOfMeals = 3;
    private static final Integer numberOfChefs = 3;
    private static final Integer numberOfWaiters = 3;
    private static final Integer numberOfDeliverers = 3;
    private static final Integer numberOfRestaurants = 3;
    private static final Integer restaurantAddress = 3;
    private static final Integer numberOfOrders = 3;

    public static void main(String[] args) {
        Category[] categories = new Category[numberOfCategories];
        Ingredient[] ingredients = new Ingredient[numberOfIngredients];
        Meal[] meals = new Meal[numberOfMeals];
        Chef[] chefs = new Chef[numberOfChefs];
        Waiter[] waiters = new Waiter[numberOfWaiters];
        Deliverer[] deliverers = new Deliverer[numberOfDeliverers];
        Restaurant[] restaurants = new Restaurant[numberOfRestaurants];
        Address[] addresses = new Address[restaurantAddress];
        Order[] orderers = new Order[numberOfOrders];

        Scanner scanner = new Scanner(System.in);

        for(int i = 0; i < categories.length; i++){
            System.out.println("Unesite podatke za " + (i + 1) + " kategoriju");
            Category kategorija = categoryInput(scanner);
            categories[i] = kategorija;
        }

        for(int i = 0; i < ingredients.length; i++){
            System.out.println("Unesite podatke za " + (i + 1) + " sastojak");
            Ingredient sastojak = ingredientInput(scanner, categories);
            ingredients[i] = sastojak;
        }

        for(int i = 0; i < meals.length; i++){
            System.out.println("Unesite podatke za " + (i+1) + " jelo");
            Meal jela = mealsInput(scanner, categories, ingredients);
            meals[i] = jela;
        }

        for(int i = 0; i < chefs.length; i++){
            System.out.println("Unesite podatke za " + (i+1) + " kuhara");
            Chef kuhara = chefInput(scanner);
            chefs[i] = kuhara;
        }

        for(int i = 0; i < waiters.length; i++){
            System.out.println("Unesite podatke za " + (i+1) + " konobara");
            Waiter konobar = waiterInput(scanner);
            waiters[i] = konobar;
        }

        for(int i = 0; i < deliverers.length; i++){
            System.out.println("Unesite podatke za " + (i+1) + " dostavljača");
            Deliverer dostavljac = delivererInput(scanner);
            deliverers[i] = dostavljac;
        }

        for(int i = 0; i < restaurants.length; i++){
            System.out.println("Unesite podatke za " + (i+1) + " restoran");
            Restaurant restoran = restoranInput(scanner, addresses, meals, chefs, waiters, deliverers);
            restaurants[i] = restoran;
        }

        for(int i = 0; i < orderers.length; i++){
            System.out.println("Unesite podatke za " + (i+1) + " narudžbu");
            Order order = orderInput(scanner, restaurants, meals, deliverers);
            orderers[i] = order;
        }

        nadiRestoranSaNajvecomNarudzbom(orderers);

        nadiDostavljacaSaNajviseDostava(deliverers);


    }

    public static boolean isNumber(String input) {
        for (int i = 0; i < input.length(); i++) {
            char znak = input.charAt(i);
            if (Character.isDigit(znak)) {
                return true;
            }
        }
        return false;
    }

    public static Category categoryInput(Scanner scanner) {
        String imeKategorije;
        String opisKategorije;
        boolean jeIspravan;

        do {
            jeIspravan = true;
            System.out.println("Unesite ime kategorije: ");
            imeKategorije = scanner.nextLine();
            if (imeKategorije.length() < 3 || isNumber(imeKategorije)) {
                System.out.println("Krivi unos, unesite ime kategorije koje ne sadrži brojeve i ima barem 3 slova.");
                jeIspravan = false;
            }
        } while (!jeIspravan);

        do {
            jeIspravan = true;
            System.out.println("Unesite opis kategorije: ");
            opisKategorije = scanner.nextLine();
            if (opisKategorije.length() < 3 || isNumber(opisKategorije)) {
                System.out.println("Krivi unos, unesite opis kategorije koji ne sadrži brojeve i ima barem 3 slova.");
                jeIspravan = false;
            }
        } while (!jeIspravan);

        return new Category(imeKategorije, opisKategorije);
    }

    public static Ingredient ingredientInput(Scanner scanner, Category[] categories) {
        BigDecimal kcal = BigDecimal.valueOf(0);
        String metodaPreparacije;
        Category odabranaKategorija = new Category("None", "No description");
        int categoryChoice;
        Boolean jeIspravan;
        String imeSastojka;
        do {
            jeIspravan = true;
            System.out.println("Unesite ime sastojka: ");
            imeSastojka = scanner.nextLine();
            if (imeSastojka.length() < 3 || isNumber(imeSastojka)) {
                System.out.println("Krivi unos, unesite ime sastojka koji ne sadrži brojeve i ima barem 3 slova.");
                jeIspravan = false;
            }

        } while (!jeIspravan);

        do {
            jeIspravan = true;
            System.out.println("Popis kategorija, odaberite jednu brojem 1-3: ");
            for (int i = 0; i < categories.length; i++) {
                System.out.println((i + 1) + ". " + categories[i].getName());
            }

            categoryChoice = scanner.nextInt();
            scanner.nextLine();

            if (categoryChoice >= 1 && categoryChoice <= categories.length) {
                odabranaKategorija = categories[categoryChoice - 1];
            } else {
                System.out.println("Krivi unos, pokušajte ponovo.");
                jeIspravan = false;
            }
        } while (!jeIspravan);



        do {
            jeIspravan = true;
            System.out.println("Unesite broj kcal: ");
            kcal = scanner.nextBigDecimal();
            scanner.nextLine();
            if (kcal.compareTo(BigDecimal.ZERO) < 0 || kcal.compareTo(BigDecimal.valueOf(1000)) > 0) {
                System.out.println("Krivi unos, unesite broj kcal u rasponu od 0 do 1000.");
                jeIspravan = false;
            }

        } while (!jeIspravan);

        do {
            jeIspravan = true;
            System.out.println("Unesite metodu preparacije: ");
            metodaPreparacije = scanner.nextLine();
            if (metodaPreparacije.length() < 3 || isNumber(metodaPreparacije)) {
                System.out.println("Krivi unos, unesite metodu preparacije koji ne sadrži brojeve i ima barem 3 slova.");
                jeIspravan = false;
            }

        } while (!jeIspravan);

        return new Ingredient(imeSastojka, odabranaKategorija, kcal, metodaPreparacije);

    }

    public static Meal mealsInput(Scanner scanner, Category[] categories, Ingredient[] ingredients){
        String imeJela;
        Category odabranaKategorija = new Category("None", "No description");
        Ingredient[] odabraniSastojak = new Ingredient[numberOfIngredients];
        BigDecimal price;
        Boolean jeIspravan;
        int brojSastojka = 0;

        do{
            jeIspravan = true;
            System.out.println("Unesite ime jela: ");
            imeJela = scanner.nextLine();
            if (imeJela.length() < 3 || isNumber(imeJela)) {
                System.out.println("Krivi unos, unesite ime jela koji ne sadrži brojeve i ima barem 3 slova.");
                jeIspravan = false;
            }
        }while(!jeIspravan);

        do{
            jeIspravan = true;
            System.out.println("Popis kategorija, odaberite jednu brojem 1-3: ");
            for (int i = 0; i < categories.length; i++) {
                System.out.println((i + 1) + ". " + categories[i].getName());
            }

            int categoryChoice = scanner.nextInt();
            scanner.nextLine();

            if (categoryChoice >= 1 && categoryChoice <= categories.length) {
                odabranaKategorija = categories[categoryChoice - 1];
            } else {
                System.out.println("Krivi unos, pokušajte ponovo.");
                jeIspravan = false;
            }
        }while(!jeIspravan);

        do{
            jeIspravan = true;
            System.out.println("Popis sastojaka, birate sastojke dok ne unesete 0 ");
            for(int i=0; i < ingredients.length; i++) {
                System.out.println((i + 1) + ". " + ingredients[i].getName());
            }

                int ingredientChoice = scanner.nextInt();
                scanner.nextLine();

                if(ingredientChoice >= 1 && ingredientChoice <= categories.length){
                    odabraniSastojak[brojSastojka] = ingredients[ingredientChoice - 1];
                    brojSastojka++;

                } else {
                    System.out.println("Krivi unos, pokušajte ponovo.");
                    jeIspravan = false;
                    continue;
                }

                while(ingredientChoice != 0){
                    ingredientChoice = scanner.nextInt();
                    scanner.nextLine();
                    if(ingredientChoice >= 1 && ingredientChoice <= categories.length){
                        odabraniSastojak[brojSastojka] = ingredients[ingredientChoice - 1];
                        brojSastojka++;

                    }else if(ingredientChoice == 0) {
                        jeIspravan = true;
                        break;
                    }else{
                        System.out.println("Krivi unos, pokušajte ponovo.");
                        jeIspravan = false;
                    }
                }
        }while(!jeIspravan);

        do{
            jeIspravan = true;
            System.out.println("Unesite cijenu: ");
            price = scanner.nextBigDecimal();
            scanner.nextLine();
            if(price.compareTo(BigDecimal.ZERO) < 0 || price.compareTo(BigDecimal.valueOf(10000)) > 0){
                System.out.println("Krivi unos, unesite točnu cijenu.");
                jeIspravan = false;
            }

        }while(!jeIspravan);

        return new Meal(imeJela, odabranaKategorija, odabraniSastojak, price);
    }

    public static Chef chefInput(Scanner scanner){
        String imeKuhara;
        String prezimeKuhara;
        BigDecimal placa;
        Boolean jeIspravan;

        do {
            jeIspravan = true;
            System.out.println("Unesite ime kuhara: ");
            imeKuhara = scanner.nextLine();
            if (imeKuhara.length() < 3 || isNumber(imeKuhara)) {
                System.out.println("Krivi unos, unesite ime kuhara koje ne sadrži brojeve i ima barem 3 slova.");
                jeIspravan = false;
            }
        } while (!jeIspravan);

        do {
            jeIspravan = true;
            System.out.println("Unesite prezime kuhara: ");
            prezimeKuhara = scanner.nextLine();
            if (prezimeKuhara.length() < 3 || isNumber(prezimeKuhara)) {
                System.out.println("Krivi unos, unesite prezime kuhara koje ne sadrži brojeve i ima barem 3 slova.");
                jeIspravan = false;
            }
        } while (!jeIspravan);

        do{
            jeIspravan = true;
            System.out.println("Unesite plaću: ");
            placa = scanner.nextBigDecimal();
            scanner.nextLine();
            if(placa.compareTo(BigDecimal.ZERO) < 0 || placa.compareTo(BigDecimal.valueOf(10000)) > 0){
                System.out.println("Krivi unos, unesite točnu plaću.");
                jeIspravan = false;
            }

        }while(!jeIspravan);

        return new Chef(imeKuhara, prezimeKuhara, placa);
    }

    public static Waiter waiterInput(Scanner scanner){
        String imeKonobara;
        String prezimeKonobara;
        BigDecimal placa;
        Boolean jeIspravan;

        do {
            jeIspravan = true;
            System.out.println("Unesite ime konobara: ");
            imeKonobara = scanner.nextLine();
            if (imeKonobara.length() < 3 || isNumber(imeKonobara)) {
                System.out.println("Krivi unos, unesite ime konobara koje ne sadrži brojeve i ima barem 3 slova.");
                jeIspravan = false;
            }
        } while (!jeIspravan);

        do {
            jeIspravan = true;
            System.out.println("Unesite prezime kuhara: ");
            prezimeKonobara = scanner.nextLine();
            if (prezimeKonobara.length() < 3 || isNumber(prezimeKonobara)) {
                System.out.println("Krivi unos, unesite prezime konobara koje ne sadrži brojeve i ima barem 3 slova.");
                jeIspravan = false;
            }
        } while (!jeIspravan);

        do{
            jeIspravan = true;
            System.out.println("Unesite plaću: ");
            placa = scanner.nextBigDecimal();
            scanner.nextLine();
            if(placa.compareTo(BigDecimal.ZERO) < 0 || placa.compareTo(BigDecimal.valueOf(10000)) > 0){
                System.out.println("Krivi unos, unesite točnu plaću.");
                jeIspravan = false;
            }

        }while(!jeIspravan);

        return new Waiter(imeKonobara, prezimeKonobara, placa);
    }

    public static Deliverer delivererInput(Scanner scanner){
        String imeDostavljaca;
        String prezimeDostavljaca;
        BigDecimal placa;
        Boolean jeIspravan;

        do {
            jeIspravan = true;
            System.out.println("Unesite ime dostavljača: ");
            imeDostavljaca = scanner.nextLine();
            if (imeDostavljaca.length() < 3 || isNumber(imeDostavljaca)) {
                System.out.println("Krivi unos, unesite ime dostavljača koje ne sadrži brojeve i ima barem 3 slova.");
                jeIspravan = false;
            }
        } while (!jeIspravan);

        do {
            jeIspravan = true;
            System.out.println("Unesite prezime dostavljača: ");
            prezimeDostavljaca = scanner.nextLine();
            if (prezimeDostavljaca.length() < 3 || isNumber(prezimeDostavljaca)) {
                System.out.println("Krivi unos, unesite prezime dostavljača koje ne sadrži brojeve i ima barem 3 slova.");
                jeIspravan = false;
            }
        } while (!jeIspravan);

        do{
            jeIspravan = true;
            System.out.println("Unesite plaću: ");
            placa = scanner.nextBigDecimal();
            scanner.nextLine();
            if(placa.compareTo(BigDecimal.ZERO) < 0 || placa.compareTo(BigDecimal.valueOf(10000)) > 0){
                System.out.println("Krivi unos, unesite točnu plaću.");
                jeIspravan = false;
            }

        }while(!jeIspravan);

        return new Deliverer(imeDostavljaca, prezimeDostavljaca, placa);
    }

    public static Address addressInput(Scanner scanner){
        String ulica;
        String brojKucneAdrese;
        String grad;
        String postanskiBroj;
        Boolean jeIspravan;

        do{
            jeIspravan = true;
            System.out.println("Unesite ulicu: ");
            ulica = scanner.nextLine();
            if (ulica.length() < 1) {
                System.out.println("Krivi unos, unesite ulicu koja ima barem 2 slova.");
                jeIspravan = false;
            }

        }while(!jeIspravan);

        do{
            jeIspravan = true;
            System.out.println("Unesite kucnu adresu: ");
            brojKucneAdrese = scanner.nextLine();
            if (brojKucneAdrese.length() < 1) {
                System.out.println("Krivi unos, unesite kućnu adresu koja ima barem 2 slova.");
                jeIspravan = false;
            }

        }while(!jeIspravan);

        do{
            jeIspravan = true;
            System.out.println("Unesite grad: ");
            grad = scanner.nextLine();
            if (grad.length() < 2) {
                System.out.println("Krivi unos, unesite grad koja ima barem 3 slova.");
                jeIspravan = false;
            }

        }while(!jeIspravan);

        do{
            jeIspravan = true;
            System.out.println("Unesite postanski broj: ");
            postanskiBroj = scanner.nextLine();
            if (postanskiBroj.length() < 1) {
                System.out.println("Krivi unos, unesite postanski broj koja ima barem 2 slova.");
                jeIspravan = false;
            }

        }while(!jeIspravan);

        return new Address(ulica, brojKucneAdrese, grad, postanskiBroj);
    }

    public static Restaurant restoranInput(Scanner scanner, Address[] addresses, Meal[] meals, Chef[] chefs, Waiter[] waiters, Deliverer[] deliverers){
        String imeRestorana;
        Address adresaRestorana = null;
        Meal[] hranaRestorana = new Meal[meals.length]; // Inicijalizirajte niz na odgovarajuću veličinu
        Chef[] kuhariRestorana = new Chef[chefs.length]; // Inicijalizirajte niz na odgovarajuću veličinu
        Waiter[] konobarRestorana = new Waiter[waiters.length]; // Inicijalizirajte niz na odgovarajuću veličinu
        Deliverer[] dostavljacRestorana = new Deliverer[deliverers.length]; // Inicijalizirajte niz na odgovarajuću veličinu
        int brojJela = 0;
        int brojKuhara = 0;
        int brojKonobara = 0;
        int brojDostavljaca = 0;

        Boolean jeIspravan;

        do{
            jeIspravan = true;
            System.out.println("Unesite ime restorana: ");
            imeRestorana = scanner.nextLine();
            if (imeRestorana.length() < 3 || isNumber(imeRestorana)) {
                System.out.println("Krivi unos, unesite ime restorana koji ne sadrži brojeve i ima barem 3 slova.");
                jeIspravan = false;
            }

        }while(!jeIspravan);

        adresaRestorana = addressInput(scanner);

        do {
            jeIspravan = true;
            System.out.println("Popis jela, birate jela dok ne unesete 0 ");
            for (int i = 0; i < meals.length; i++) {
                if (meals[i] != null) { // Provjeravaj da jelo nije null
                    System.out.println((i + 1) + ". " + meals[i].getName());
                }
            }

            int mealChoice = scanner.nextInt();
            scanner.nextLine();

            while (mealChoice != 0) {
                if (mealChoice >= 1 && mealChoice <= meals.length && meals[mealChoice - 1] != null) {
                    hranaRestorana[brojJela] = meals[mealChoice - 1];
                    brojJela++; // Inkrementiraj broj jela
                } else {
                    System.out.println("Krivi unos, pokušajte ponovo.");
                    jeIspravan = false;
                }

                mealChoice = scanner.nextInt();
                scanner.nextLine();
            }
        } while (!jeIspravan);

        do{
            jeIspravan = true;
            System.out.println("Popis kuhara, birate kuhare dok ne unesete 0 ");
            for(int i=0; i < chefs.length; i++) {
                System.out.println((i + 1) + ". " + chefs[i].getFirstName() + " " + chefs[i].getLastName());
            }

            int chefChoice = scanner.nextInt();
            scanner.nextLine();

            if(chefChoice >= 1 && chefChoice <= meals.length){
                kuhariRestorana[brojKuhara] = chefs[chefChoice - 1];
                brojKuhara++;

            } else {
                System.out.println("Krivi unos, pokušajte ponovo.");
                jeIspravan = false;
                continue;
            }

            while(chefChoice != 0){
                chefChoice = scanner.nextInt();
                scanner.nextLine();
                if(chefChoice >= 1 && chefChoice <= meals.length){
                    kuhariRestorana[brojKuhara] = chefs[chefChoice - 1];
                    brojKuhara++;

                }else if(chefChoice == 0) {
                    jeIspravan = true;
                    break;
                }else{
                    System.out.println("Krivi unos, pokušajte ponovo.");
                    jeIspravan = false;
                }
            }
        }while(!jeIspravan);

        do{
            jeIspravan = true;
            System.out.println("Popis konobara, odaberite jednog brojem 1-3: ");
            for (int i = 0; i < waiters.length; i++) {
                System.out.println((i + 1) + ". " + waiters[i].getFirstName() + " " + waiters[i].getLastName());
            }

            int waiterChoice = scanner.nextInt();
            scanner.nextLine();

            if (waiterChoice >= 1 && waiterChoice <= waiters.length) {
                konobarRestorana[brojKonobara] = waiters[waiterChoice - 1];
                brojKonobara++;
            } else {
                System.out.println("Krivi unos, pokušajte ponovo.");
                jeIspravan = false;
            }
        }while(!jeIspravan);

        do{
            jeIspravan = true;
            System.out.println("Popis dostavljača, odaberite jednog brojem 1-3: ");
            for (int i = 0; i < deliverers.length; i++) {
                System.out.println((i + 1) + ". " + deliverers[i].getFirstName() + " " + deliverers[i].getLastName());
            }

            int delivererChoice = scanner.nextInt();
            scanner.nextLine();

            if (delivererChoice >= 1 && delivererChoice <= deliverers.length) {
                dostavljacRestorana[brojDostavljaca] = deliverers[delivererChoice - 1];
                brojDostavljaca++;
            } else {
                System.out.println("Krivi unos, pokušajte ponovo.");
                jeIspravan = false;
            }
        }while(!jeIspravan);

        return new Restaurant(imeRestorana, adresaRestorana, hranaRestorana, kuhariRestorana, konobarRestorana, dostavljacRestorana);
    }

    public static Order orderInput(Scanner scanner, Restaurant[] restaurants, Meal[] meals, Deliverer[] deliverers) {

        Restaurant selectedRestaurant = null;
        Meal[] selectedMeals = new Meal[10];
        int mealCount = 0;
        Deliverer selectedDeliverer = null;
        boolean jeIspravan;
        LocalDateTime vrijemeDostave = null;

        // Odabir restorana
        do {
            jeIspravan = true;
            System.out.println("Popis restorana, odaberite jedan brojem 1-" + restaurants.length + ": ");
            for (int i = 0; i < restaurants.length; i++) {
                System.out.println((i + 1) + ". " + restaurants[i].getName());
            }

            int restaurantChoice = scanner.nextInt();
            scanner.nextLine();

            if (restaurantChoice >= 1 && restaurantChoice <= restaurants.length) {
                selectedRestaurant = restaurants[restaurantChoice - 1];
            } else {
                System.out.println("Krivi unos, pokušajte ponovo.");
                jeIspravan = false;
            }
        } while (!jeIspravan);

        // Odabir jela iz odabranog restorana
        do {
            jeIspravan = true;

            if (selectedRestaurant == null) {
                System.out.println("Odabrani restoran je nevažeći. Pokušajte ponovo.");
                jeIspravan = false;
                continue;
            }

            Meal[] availableMeals = selectedRestaurant.getMeals();
            if (availableMeals == null || availableMeals.length == 0) {
                System.out.println("Nema dostupnih jela za restoran " + selectedRestaurant.getName());
                jeIspravan = false;
                continue;
            }

            System.out.println("Popis jela za restoran " + selectedRestaurant.getName() + ", odaberite jedan brojem 1-" + availableMeals.length);
            for (int i = 0; i < availableMeals.length; i++) {
                if (availableMeals[i] != null) {
                    System.out.println((i + 1) + ". " + availableMeals[i].getName());
                }
            }

            int mealChoice = scanner.nextInt();
            scanner.nextLine();

            if (mealChoice == 0) {
                // User chose to finish selecting meals
                break; // Exit the loop
            }

            if (mealChoice >= 1 && mealChoice <= availableMeals.length) {
                if (mealCount < selectedMeals.length) {
                    selectedMeals[mealCount] = availableMeals[mealChoice - 1];
                    mealCount++;
                } else {
                    System.out.println("Prekoračili ste maksimalni broj jela (10).");
                    jeIspravan = false;
                }
            } else {
                System.out.println("Krivi unos, pokušajte ponovo.");
                jeIspravan = false;
            }
        } while (!jeIspravan || mealCount == 0);

        // Odabir dostavljača
        do {
            jeIspravan = true;
            System.out.println("Popis dostavljača, odaberite jednog brojem 1-" + deliverers.length + ": ");
            for (int i = 0; i < deliverers.length; i++) {
                System.out.println((i + 1) + ". " + deliverers[i].getFirstName() + " " + deliverers[i].getLastName());
            }

            int delivererChoice = scanner.nextInt();
            scanner.nextLine();

            if (delivererChoice >= 1 && delivererChoice <= deliverers.length) {
                selectedDeliverer = deliverers[delivererChoice - 1];
                selectedDeliverer.incrementDostave();
            } else {
                System.out.println("Krivi unos, pokušajte ponovo.");
                jeIspravan = false;
            }
        } while (!jeIspravan);



        // Trim the selectedMeals array to the number of meals actually selected
        Meal[] finalSelectedMeals = new Meal[mealCount];
        for (int i = 0; i < mealCount; i++) {
            finalSelectedMeals[i] = selectedMeals[i];
        }

        do {
            jeIspravan = true;
            System.out.println("Unesite vrijeme dostave (u formatu: yyyy-MM-dd HH:mm): ");
            String vrijemeDostaveInput = scanner.nextLine();

            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                vrijemeDostave = LocalDateTime.parse(vrijemeDostaveInput, formatter);

                // Provjera da li je vrijeme u budućnosti
                if (vrijemeDostave.isBefore(LocalDateTime.now())) {
                    System.out.println("Vrijeme dostave mora biti u budućnosti. Pokušajte ponovo.");
                    jeIspravan = false;
                }

            } catch (DateTimeParseException e) {
                System.out.println("Neispravan format datuma i vremena. Pokušajte ponovo.");
                jeIspravan = false;
            }

        } while (!jeIspravan);

        return new Order(selectedRestaurant, finalSelectedMeals, selectedDeliverer, vrijemeDostave);
    }

    public static void nadiRestoranSaNajvecomNarudzbom(Order[] orders) {
        BigDecimal highestPrice = BigDecimal.ZERO;
        Restaurant[] highestRestaurants = new Restaurant[orders.length];
        int restaurantCount = 0;

        for (Order order : orders) {
            BigDecimal currentPrice = order.getTotalPrice();
            Restaurant restaurant = order.getRestaurant();

            if (currentPrice.compareTo(highestPrice) > 0) {
                highestPrice = currentPrice;
                restaurantCount = 0;
                highestRestaurants[restaurantCount++] = restaurant;
            } else if (currentPrice.compareTo(highestPrice) == 0) {
                highestRestaurants[restaurantCount++] = restaurant;
            }
        }

        System.out.println("Restorani s najskupljom narudžbom (cijena: " + highestPrice + "):");
        for (int i = 0; i < restaurantCount; i++) {
            System.out.println("Restoran: " + highestRestaurants[i].getName());
        }
    }

    public static void nadiDostavljacaSaNajviseDostava(Deliverer[] deliverers) {
        int maxDostave = 0;
        Deliverer[] najDostavljaci = new Deliverer[deliverers.length];
        int brojNajDostavljaca = 0;

        for (Deliverer deliverer : deliverers) {
            if (deliverer.getBrojDostava() > maxDostave) {
                maxDostave = deliverer.getBrojDostava();
                brojNajDostavljaca = 0;
                najDostavljaci[brojNajDostavljaca++] = deliverer;
            } else if (deliverer.getBrojDostava() == maxDostave) {
                najDostavljaci[brojNajDostavljaca++] = deliverer;
            }
        }

        System.out.println("Dostavljači s najviše dostava (" + maxDostave + "):");
        for (int i = 0; i < brojNajDostavljaca; i++) {
            Deliverer dostavljac = najDostavljaci[i];
            System.out.println(dostavljac.getFirstName() + " " + dostavljac.getLastName() + ", Plaća: " + dostavljac.getSalary());
        }
    }



}
