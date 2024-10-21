package hr.java.production.main;

import hr.java.restaurant.model.*;

import java.math.BigDecimal;
import java.util.Scanner;

public class Main {
    private static final Integer numberOfCategories = 2; // 3 JE PRAVI UNOS -------------
    private static final Integer numberOfIngredients = 2; // 2 JE PRAVI UNOS -------------
    private static final Integer numberOfMeals = 2; // 3 JE PRAVI UNOS -------------
    private static final Integer numberOfChefs = 2; // 3 JE PRAVI UNOS -------------
    private static final Integer numberOfWaiters = 2; // 3 JE PRAVI UNOS -------------
    private static final Integer numberOfDeliverers = 2; // 3 JE PRAVI UNOS -------------
    private static final Integer numberOfRestaurants = 2; // 3 JE PRAVI UNOS -------------
    private static final Integer restaurantAddress = 2; // 3 JE PRAVI UNOS -------------
    private static final Integer numberOfOrders = 2; // 3 JE PRAVI UNOS -------------

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
            Restaurant restoran = restoranInput(scanner);
            restaurants[i] = restoran;
        }


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
        Boolean jeIspravan;

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
        String imeSastojka;
        BigDecimal kcal;
        String metodaPreparacije;
        Category odabranaKategorija = new Category("None", "No description");;
        int categoryChoice;
        Boolean jeIspravan;

        do{
            jeIspravan = true;
            System.out.println("Unesite ime sastojka: ");
            imeSastojka = scanner.nextLine();
            if (imeSastojka.length() < 3 || isNumber(imeSastojka)) {
                System.out.println("Krivi unos, unesite ime sastojka koji ne sadrži brojeve i ima barem 3 slova.");
                jeIspravan = false;
            }

        }while(!jeIspravan);

        do{
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
        }while(!jeIspravan);

        do{
            jeIspravan = true;
            System.out.println("Unesite broj kcal: ");
            kcal = scanner.nextBigDecimal();
            scanner.nextLine();
            if(kcal.compareTo(BigDecimal.ZERO) < 0 || kcal.compareTo(BigDecimal.valueOf(1000)) > 0){
                System.out.println("Krivi unos, unesite točan broj kcal.");
                jeIspravan = false;
            }

        }while(!jeIspravan);

        do{
            jeIspravan = true;
            System.out.println("Unesite metodu preparacije: ");
            metodaPreparacije = scanner.nextLine();
            if (metodaPreparacije.length() < 3 || isNumber(metodaPreparacije)) {
                System.out.println("Krivi unos, unesite metodu preparacije koji ne sadrži brojeve i ima barem 3 slova.");
                jeIspravan = false;
            }

        }while(!jeIspravan);

        return new Ingredient(imeSastojka, odabranaKategorija, kcal, metodaPreparacije);

    }

    public static Meal mealsInput(Scanner scanner, Category[] categories, Ingredient[] ingredients){
        String imeJela;
        Category odabranaKategorija = new Category("None", "No description");;
        Ingredient odabraniSastojak = new Ingredient("No ingredient", new Category("None", "No description"), BigDecimal.ZERO, "No preparation method");
        BigDecimal price;
        Boolean jeIspravan;

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
                    odabraniSastojak = ingredients[ingredientChoice - 1];

                } else {
                    System.out.println("Krivi unos, pokušajte ponovo.");
                    jeIspravan = false;
                    continue;
                }

                while(ingredientChoice != 0){
                    ingredientChoice = scanner.nextInt();
                    scanner.nextLine();
                    if(ingredientChoice >= 1 && ingredientChoice <= categories.length){
                        odabraniSastojak = ingredients[ingredientChoice - 1];

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

    public static Restaurant restoranInput(Scanner scanner, Address[] addresses, Meal[] meals, Chef[] chefs, Waiter[] waiters, Deliverer[] deliverers){
        String imeRestorana;
        Address adresaRestorana = null;
        Meal hranaRestorana = new Meal("No Meal", new Category("None", "No description"), new Ingredient("No ingredient", new Category("None", "No description"), BigDecimal.ZERO, "No preparation method"), BigDecimal.ZERO);
        Chef kuhariRestorana = new Chef("No name", "No surname", BigDecimal.ZERO);
        Waiter konobarRestorana = new Waiter("No name", "No surname", BigDecimal.ZERO);
        Deliverer dostavljacRestorana = new Deliverer("No name", "No surname", BigDecimal.ZERO);

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

        do{
            jeIspravan = true;
            System.out.println("Popis sastojaka, birate sastojke dok ne unesete 0 ");
            for(int i=0; i < meals.length; i++) {
                System.out.println((i + 1) + ". " + meals[i].getName());
            }

            int mealChoice = scanner.nextInt();
            scanner.nextLine();

            if(mealChoice >= 1 && mealChoice <= meals.length){
                hranaRestorana = meals[mealChoice - 1];

            } else {
                System.out.println("Krivi unos, pokušajte ponovo.");
                jeIspravan = false;
                continue;
            }

            while(mealChoice != 0){
                mealChoice = scanner.nextInt();
                scanner.nextLine();
                if(mealChoice >= 1 && mealChoice <= meals.length){
                    hranaRestorana = meals[mealChoice - 1];

                }else if(mealChoice == 0) {
                    jeIspravan = true;
                    break;
                }else{
                    System.out.println("Krivi unos, pokušajte ponovo.");
                    jeIspravan = false;
                }
            }
        }while(!jeIspravan);
    }
    
}
