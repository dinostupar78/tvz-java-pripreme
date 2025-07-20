package hr.java.production.main;
import hr.java.restaurant.model.*;
import hr.java.restaurant.utils.Messages;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;
import java.util.Scanner;

public class Main {
    private static final Integer NUMBER_OF_CATEGORIES = 3;
    private static final Integer NUMBER_OF_INGREDIENTS = 5;
    private static final Integer NUMBER_OF_MEALS = 3;
    private static final Integer NUMBER_OF_CHEFS = 3;
    private static final Integer NUMBER_OF_WAITERS = 3;
    private static final Integer NUMBER_OF_DELIVERERS = 3;
    private static final Integer NUMBER_OF_RESTAURANTS = 3;
    private static final Integer NUMBER_OF_ADDRESSES = 3;
    private static final Integer NUMBER_OF_ORDERS = 3;

    public static void main(String[] args) {
        Category[] categories = new Category[NUMBER_OF_CATEGORIES];
        Ingredient[] ingredients = new Ingredient[NUMBER_OF_INGREDIENTS];
        Meal[] meals = new Meal[NUMBER_OF_MEALS];
        Chef[] chefs = new Chef[NUMBER_OF_CHEFS];
        Waiter[] waiters = new Waiter[NUMBER_OF_WAITERS];
        Deliverer[] deliverers = new Deliverer[NUMBER_OF_DELIVERERS];
        Restaurant[] restaurants = new Restaurant[NUMBER_OF_RESTAURANTS];
        Address[] addresses = new Address[NUMBER_OF_ADDRESSES];
        Order[] orders = new Order[NUMBER_OF_ORDERS];

        Scanner scanner = new Scanner(System.in);

        for(int i = 0; i < categories.length; i++){
            System.out.println(Messages.DATA_INPUT + (i + 1) + "." + " kategoriju:");
            Category cat = categoryInput(scanner);
            categories[i] = cat;
        }

        for(int i = 0; i < ingredients.length; i++){
            System.out.println(Messages.CATEGORY_INPUT_NAME);
            Ingredient ingredient = ingredientInput(scanner, categories);
            ingredients[i] = ingredient;
        }

        for(int i = 0; i < meals.length; i++){
            System.out.println(Messages.DATA_INPUT + (i + 1) + "." + " jelo.");
            Meal meal = mealsInput(scanner, categories, ingredients);
            meals[i] = meal;
        }

        for(int i = 0; i < chefs.length; i++){
            System.out.println(Messages.DATA_INPUT + (i + 1) + "." + " kuhara.");
            Chef chef = chefInput(scanner);
            chefs[i] = chef;
        }

        for(int i = 0; i < waiters.length; i++){
            System.out.println(Messages.DATA_INPUT + (i + 1) + "." + " konobara.");
            Waiter waiter = waiterInput(scanner);
            waiters[i] = waiter;
        }

        for(int i = 0; i < deliverers.length; i++){
            System.out.println(Messages.DATA_INPUT + (i + 1) + "." + " dostavljača.");
            Deliverer deliverer = delivererInput(scanner);
            deliverers[i] = deliverer;
        }

        for(int i = 0; i < restaurants.length; i++){
            System.out.println(Messages.DATA_INPUT + (i + 1) + "." + " restoran.");
            Restaurant restaurant = restaurantInput(scanner, meals,
                    chefs, waiters, deliverers);
            restaurants[i] = restaurant;
        }

        for(int i = 0; i < orders.length; i++){
            System.out.println(Messages.DATA_INPUT + (i + 1) + "." + " narudžbu.");
            Order order = orderInput(scanner, restaurants, meals, deliverers);
            orders[i] = order;
        }

        nadiRestoranSaNajvecomNarudzbom(orders);

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
        String catName;
        String catDesc;
        boolean isValid;

        do {
            isValid = true;
            System.out.println(Messages.CATEGORY_INPUT_NAME);
            catName = scanner.nextLine();
            if (catName.length() < 3 || isNumber(catName)) {
                System.out.println(Messages.CATEGORY_INPUT_ERROR);
                isValid = false;
            }
        } while (!isValid);

        do {
            isValid = true;
            System.out.println(Messages.CATEGORY_INPUT_DESC);
            catDesc = scanner.nextLine();
            if (catDesc.length() < 3 || isNumber(catDesc)) {
                System.out.println(Messages.CATEGORY_INPUT_ERROR);
                isValid = false;
            }
        } while (!isValid);

        return new Category(catName, catDesc);
    }

    public static Ingredient ingredientInput(Scanner scanner, Category[] categories) {
        String ingredientName;
        String prepMethod;
        BigDecimal kcal;

        Category selectedCat = Optional.ofNullable(categories[0]).orElse(categories[0]); // TEK SE RADI NA 4. LABOSU
        Integer catChoice;
        boolean isValid;

        do {
            isValid = true;
            System.out.println(Messages.INGREDIENT_INPUT_NAME);
            ingredientName = scanner.nextLine();
            if (ingredientName.length() < 3 || isNumber(ingredientName)) {
                System.out.println(Messages.INGREDIENT_INPUT_ERROR);
                isValid = false;
            }

        } while (!isValid);

        do {
            isValid = true;
            System.out.println(Messages.INGREDIENT_INPUT_CATEGORY);
            for (int i = 0; i < categories.length; i++) {
                System.out.println((i + 1) + ". " + categories[i].getName());
            }

            catChoice = scanner.nextInt();
            scanner.nextLine();

            if (catChoice >= 1 && catChoice <= categories.length) {
                selectedCat = categories[catChoice - 1];
            } else {
                System.out.println(Messages.INGREDIENT_INPUT_ERROR);
                isValid = false;
            }
        } while (!isValid);


        do {
            isValid = true;
            System.out.println(Messages.INGREDIENT_INPUT_KCAL);
            kcal = scanner.nextBigDecimal();
            scanner.nextLine();
            if (kcal.compareTo(BigDecimal.ZERO) < 0 || kcal.compareTo(BigDecimal.valueOf(1000)) > 0) {
                System.out.println(Messages.INGREDIENT_INPUT_KCAL_ERROR);
                isValid = false;
            }

        } while (!isValid);

        do {
            isValid = true;
            System.out.println(Messages.INGREDIENT_INPUT_PREP_METHOD);
            prepMethod = scanner.nextLine();
            if (prepMethod.length() < 3 || isNumber(prepMethod)) {
                System.out.println(Messages.INGREDIENT_INPUT_PREP_METHOD_ERROR);
                isValid = false;
            }

        } while (!isValid);

        return new Ingredient(ingredientName, selectedCat, kcal, prepMethod);

    }

    public static Meal mealsInput(Scanner scanner, Category[] categories, Ingredient[] ingredients){
        String mealName;
        Category selectedCat = Optional.ofNullable(categories[0]).orElse(categories[0]);
        Ingredient[] selectedIngredient = new Ingredient[NUMBER_OF_INGREDIENTS];
        BigDecimal price;
        boolean isValid;
        Integer ingredientCounter = 0;

        do{
            isValid = true;
            System.out.println(Messages.MEAL_INPUT_NAME);
            mealName = scanner.nextLine();
            if (mealName.length() < 3 || isNumber(mealName)) {
                System.out.println(Messages.MEAL_INPUT_ERROR);
                isValid = false;
            }
        }while(!isValid);

        do{
            isValid = true;
            System.out.println(Messages.MEAL_INPUT_CATEGORY);
            for (int i = 0; i < categories.length; i++) {
                System.out.println((i + 1) + ". " + categories[i].getName());
            }

            int categoryChoice = scanner.nextInt();
            scanner.nextLine();

            if (categoryChoice >= 1 && categoryChoice <= categories.length) {
                selectedCat = categories[categoryChoice - 1];
            } else {
                System.out.println(Messages.MEAL_INPUT_CATEGORY_ERROR);
                isValid = false;
            }
        }while(!isValid);

        do{
            isValid = true;
            System.out.println(Messages.MEAL_INPUT_INGREDIENT);
            for(int i=0; i < ingredients.length; i++) {
                System.out.println((i + 1) + ". " + ingredients[i].getName());
            }

                int ingredientChoice = scanner.nextInt();
                scanner.nextLine();

                if(ingredientChoice >= 1 && ingredientChoice <= categories.length){
                    selectedIngredient[ingredientCounter] = ingredients[ingredientChoice - 1];
                    ingredientCounter++;

                } else {
                    System.out.println(Messages.MEAL_INPUT_INGREDIENT_ERROR);
                    isValid = false;
                    continue;
                }

                while(ingredientChoice != 0){
                    ingredientChoice = scanner.nextInt();
                    scanner.nextLine();
                    if(ingredientChoice >= 1 && ingredientChoice <= categories.length){
                        selectedIngredient[ingredientCounter] = ingredients[ingredientChoice - 1];
                        ingredientCounter++;

                    }else if(ingredientChoice == 0) {
                        isValid = true;
                        break;
                    }else{
                        System.out.println(Messages.MEAL_INPUT_INGREDIENT_ERROR);
                        isValid = false;
                    }
                }
        }while(!isValid);

        do{
            isValid = true;
            System.out.println(Messages.MEAL_INPUT_PRICE);
            price = scanner.nextBigDecimal();
            scanner.nextLine();
            if(price.compareTo(BigDecimal.ZERO) < 0 || price.compareTo(BigDecimal.valueOf(10000)) > 0){
                System.out.println(Messages.MEAL_INPUT_PRICE_ERROR);
                isValid = false;
            }

        }while(!isValid);

        return new Meal(mealName, selectedCat, selectedIngredient, price);
    }

    public static Chef chefInput(Scanner scanner){
        String chefName;
        String chefSurname;
        BigDecimal chefSalary;
        boolean isValid;

        do {
            isValid = true;
            System.out.println(Messages.CHEF_INPUT_NAME);
            chefName = scanner.nextLine();
            if (chefName.length() < 3 || isNumber(chefName)) {
                System.out.println(Messages.CHEF_INPUT_NAME_ERROR);
                isValid = false;
            }
        } while (!isValid);

        do {
            isValid = true;
            System.out.println(Messages.CHEF_INPUT_SURNAME);
            chefSurname = scanner.nextLine();
            if (chefSurname.length() < 3 || isNumber(chefSurname)) {
                System.out.println(Messages.CHEF_INPUT_SURNAME_ERROR);
                isValid = false;
            }
        } while (!isValid);

        do{
            isValid = true;
            System.out.println(Messages.CHEF_INPUT_SALARY);
            chefSalary = scanner.nextBigDecimal();
            scanner.nextLine();
            if(chefSalary.compareTo(BigDecimal.ZERO) < 0 || chefSalary.compareTo(BigDecimal.valueOf(10000)) > 0){
                System.out.println(Messages.CHEF_INPUT_SALARY_ERROR);
                isValid = false;
            }

        }while(!isValid);

        return new Chef(chefName, chefSurname, chefSalary);
    }

    public static Waiter waiterInput(Scanner scanner){
        String waiterName;
        String waiterSurname;
        BigDecimal waiterSalary;
        boolean isValid;

        do {
            isValid = true;
            System.out.println(Messages.WAITER_INPUT_NAME);
            waiterName = scanner.nextLine();
            if (waiterName.length() < 3 || isNumber(waiterName)) {
                System.out.println(Messages.WAITER_INPUT_NAME_ERROR);
                isValid = false;
            }
        } while (!isValid);

        do {
            isValid = true;
            System.out.println(Messages.WAITER_INPUT_SURNAME);
            waiterSurname = scanner.nextLine();
            if (waiterSurname.length() < 3 || isNumber(waiterSurname)) {
                System.out.println(Messages.WAITER_INPUT_SURNAME_ERROR);
                isValid = false;
            }
        } while (!isValid);

        do{
            isValid = true;
            System.out.println(Messages.WAITER_INPUT_SALARY);
            waiterSalary = scanner.nextBigDecimal();
            scanner.nextLine();
            if(waiterSalary.compareTo(BigDecimal.ZERO) < 0 || waiterSalary.compareTo(BigDecimal.valueOf(10000)) > 0){
                System.out.println(Messages.WAITER_INPUT_SALARY_ERROR);
                isValid = false;
            }

        }while(!isValid);

        return new Waiter(waiterName, waiterSurname, waiterSalary);
    }

    public static Deliverer delivererInput(Scanner scanner){
        String delivererName;
        String delivererSurname;
        BigDecimal delivererSalary;
        boolean isValid;

        do {
            isValid = true;
            System.out.println(Messages.DELIVERER_INPUT_NAME);
            delivererName = scanner.nextLine();
            if (delivererName.length() < 3 || isNumber(delivererName)) {
                System.out.println(Messages.DELIVERER_INPUT_NAME_ERROR);
                isValid = false;
            }
        } while (!isValid);

        do {
            isValid = true;
            System.out.println(Messages.DELIVERER_INPUT_SURNAME);
            delivererSurname = scanner.nextLine();
            if (delivererSurname.length() < 3 || isNumber(delivererSurname)) {
                System.out.println(Messages.DELIVERER_INPUT_SURNAME_ERROR);
                isValid = false;
            }
        } while (!isValid);

        do{
            isValid = true;
            System.out.println(Messages.DELIVERER_INPUT_SALARY);
            delivererSalary = scanner.nextBigDecimal();
            scanner.nextLine();
            if(delivererSalary.compareTo(BigDecimal.ZERO) < 0 || delivererSalary.compareTo(BigDecimal.valueOf(10000)) > 0){
                System.out.println(Messages.DELIVERER_INPUT_SALARY_ERROR);
                isValid = false;
            }

        }while(!isValid);

        return new Deliverer(delivererName, delivererSurname, delivererSalary);
    }

    public static Address addressInput(Scanner scanner){
        String street;
        String houseNumber;
        String city;
        String postanskiBroj;
        boolean isValid;

        do{
            isValid = true;
            System.out.println(Messages.ADDRESS_INPUT_STREET);
            street = scanner.nextLine();
            if (street.length() < 3) {
                System.out.println(Messages.ADDRESS_INPUT_STREET_ERROR);
                isValid = false;
            }

        }while(!isValid);

        do{
            isValid = true;
            System.out.println(Messages.ADDRESS_INPUT_HOUSE_NUMBER);
            houseNumber = scanner.nextLine();
            if (houseNumber.length() < 2) {
                System.out.println(Messages.ADDRESS_INPUT_HOUSE_NUMBER_ERROR);
                isValid = false;
            }

        }while(!isValid);

        do{
            isValid = true;
            System.out.println(Messages.ADDRESS_INPUT_CITY);
            city = scanner.nextLine();
            if (city.length() < 2) {
                System.out.println(Messages.ADDRESS_INPUT_CITY_ERROR);
                isValid = false;
            }

        }while(!isValid);

        do{
            isValid = true;
            System.out.println(Messages.ADDRESS_INPUT_POSTAL_CODE);
            postanskiBroj = scanner.nextLine();
            if (postanskiBroj.length() < 2) {
                System.out.println(Messages.ADDRESS_INPUT_POSTAL_CODE_ERROR);
                isValid = false;
            }

        }while(!isValid);

        return new Address(street, houseNumber, city, postanskiBroj);
    }

    public static Restaurant restaurantInput(Scanner scanner, Meal[] meals, Chef[] chefs, Waiter[] waiters, Deliverer[] deliverers){
        String restaurantName;
        Address restaurantAddress;
        Meal[] restaurantMeals = new Meal[meals.length];
        Chef[] restaurantChefs = new Chef[chefs.length];
        Waiter[] restaurantWaiters = new Waiter[waiters.length];
        Deliverer[] restaurantDeliverers = new Deliverer[deliverers.length];
        Integer mealCounter = 0;
        Integer chefCounter = 0;
        Integer waiterCounter = 0;
        Integer delivererCounter = 0;

        boolean isValid;

        do{
            isValid = true;
            System.out.println(Messages.RESTAURANT_INPUT_NAME);
            restaurantName = scanner.nextLine();
            if (restaurantName.length() < 3 || isNumber(restaurantName)) {
                System.out.println(Messages.RESTAURANT_INPUT_NAME_ERROR);
                isValid = false;
            }

        }while(!isValid);

        restaurantAddress = addressInput(scanner);

        do {
            isValid = true;
            System.out.println(Messages.RESTAURANT_INPUT_MEAL);
            for (int i = 0; i < meals.length; i++) {
                if (meals[i] != null) {
                    System.out.println((i + 1) + ". " + meals[i].getName());
                }
            }

            int mealChoice = scanner.nextInt();
            scanner.nextLine();

            while (mealChoice != 0) {
                if (mealChoice >= 1 && mealChoice <= meals.length && meals[mealChoice - 1] != null) {
                    restaurantMeals[mealCounter] = meals[mealChoice - 1];
                    mealCounter++;
                } else {
                    System.out.println(Messages.RESTAURANT_INPUT_MEAL_ERROR);
                    isValid = false;
                }

                mealChoice = scanner.nextInt();
                scanner.nextLine();
            }
        } while (!isValid);

        do{
            isValid = true;
            System.out.println(Messages.RESTAURANT_INPUT_CHEF);
            for(int i=0; i < chefs.length; i++) {
                System.out.println((i + 1) + ". " + chefs[i].getFirstName() + " " + chefs[i].getLastName());
            }

            int chefChoice = scanner.nextInt();
            scanner.nextLine();

            if(chefChoice >= 1 && chefChoice <= meals.length){
                restaurantChefs[chefCounter] = chefs[chefChoice - 1];
                chefCounter++;

            } else {
                System.out.println(Messages.RESTAURANT_INPUT_CHEF_ERROR);
                isValid = false;
                continue;
            }

            while(chefChoice != 0){
                chefChoice = scanner.nextInt();
                scanner.nextLine();
                if(chefChoice >= 1 && chefChoice <= meals.length){
                    restaurantChefs[chefCounter] = chefs[chefChoice - 1];
                    chefCounter++;

                }else if(chefChoice == 0) {
                    isValid = true;
                    break;
                }else{
                    System.out.println(Messages.RESTAURANT_INPUT_CHEF_ERROR);
                    isValid = false;
                }
            }
        }while(!isValid);

        do{
            isValid = true;
            System.out.println(Messages.RESTAURANT_INPUT_WAITER);
            for (int i = 0; i < waiters.length; i++) {
                System.out.println((i + 1) + ". " + waiters[i].getFirstName() + " " + waiters[i].getLastName());
            }

            int waiterChoice = scanner.nextInt();
            scanner.nextLine();

            if (waiterChoice >= 1 && waiterChoice <= waiters.length) {
                restaurantWaiters[waiterCounter] = waiters[waiterChoice - 1];
                waiterCounter++;
            } else {
                System.out.println(Messages.RESTAURANT_INPUT_WAITER_ERROR);
                isValid = false;
            }
        }while(!isValid);

        do{
            isValid = true;
            System.out.println(Messages.RESTAURANT_INPUT_DELIVERER);
            for (int i = 0; i < deliverers.length; i++) {
                System.out.println((i + 1) + ". " + deliverers[i].getFirstName() + " " + deliverers[i].getLastName());
            }

            int delivererChoice = scanner.nextInt();
            scanner.nextLine();

            if (delivererChoice >= 1 && delivererChoice <= deliverers.length) {
                restaurantDeliverers[delivererCounter] = deliverers[delivererChoice - 1];
                delivererCounter++;
            } else {
                System.out.println(Messages.RESTAURANT_INPUT_DELIVERER_ERROR);
                isValid = false;
            }
        }while(!isValid);

        return new Restaurant(restaurantName, restaurantAddress, restaurantMeals, restaurantChefs, restaurantWaiters, restaurantDeliverers);
    }

    public static Order orderInput(Scanner scanner, Restaurant[] restaurants, Meal[] meals, Deliverer[] deliverers) {

        Restaurant selectedRestaurant = restaurants[restaurants.length - 1];
        Meal[] selectedMeals = new Meal[10];
        int mealCount = 0;
        Deliverer selectedDeliverer = deliverers[deliverers.length - 1];
        boolean isValid;
        LocalDateTime vrijemeDostave = Optional.of(LocalDateTime.now()).orElse(LocalDateTime.now());

        do {
            isValid = true;
            System.out.println(Messages.ORDER_INPUT_RESTAURANT + restaurants.length + ".");
            for (int i = 0; i < restaurants.length; i++) {
                System.out.println((i + 1) + ". " + restaurants[i].getName());
            }

            int restaurantChoice = scanner.nextInt();
            scanner.nextLine();

            if (restaurantChoice >= 1 && restaurantChoice <= restaurants.length) {
                selectedRestaurant = restaurants[restaurantChoice - 1];
            } else {
                System.out.println(Messages.ORDER_INPUT_RESTAURANT_ERROR);
                isValid = false;
            }
        } while (!isValid);

        do {
            isValid = true;

            if (selectedRestaurant == null) {
                System.out.println(Messages.ORDER_INPUT_SELECTED_RESTAURANT);
                isValid = false;
                continue;
            }

            Meal[] availableMeals = selectedRestaurant.getMeals();
            if (availableMeals == null || availableMeals.length == 0) {
                System.out.println(Messages.ORDER_INPUT_NO_MEALS + selectedRestaurant.getName());
                isValid = false;
                continue;
            }

            System.out.println(Messages.ORDER_INPUT_MEALS + selectedRestaurant.getName() + Messages.ORDER_INPUT_MEALS_SELECT + availableMeals.length);
            for (int i = 0; i < availableMeals.length; i++) {
                if (availableMeals[i] != null) {
                    System.out.println((i + 1) + ". " + availableMeals[i].getName());
                }
            }

            int mealChoice = scanner.nextInt();
            scanner.nextLine();

            if (mealChoice == 0) {
                break;
            }

            if (mealChoice >= 1 && mealChoice <= availableMeals.length) {
                if (mealCount < selectedMeals.length) {
                    selectedMeals[mealCount] = availableMeals[mealChoice - 1];
                    mealCount++;
                } else {
                    System.out.println(Messages.ORDER_INPUT_MEALS_ERROR);
                    isValid = false;
                }
            } else {
                System.out.println(Messages.ORDER_INPUT_ERROR);
                isValid = false;
            }
        } while (!isValid || mealCount == 0);

        do {
            isValid = true;
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
                isValid = false;
            }
        } while (!isValid);



        // Trim the selectedMeals array to the number of meals actually selected
        Meal[] finalSelectedMeals = new Meal[mealCount];
        for (int i = 0; i < mealCount; i++) {
            finalSelectedMeals[i] = selectedMeals[i];
        }

        do {
            isValid = true;
            System.out.println("Unesite vrijeme dostave (u formatu: yyyy-MM-dd HH:mm): ");
            String vrijemeDostaveInput = scanner.nextLine();

            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                vrijemeDostave = LocalDateTime.parse(vrijemeDostaveInput, formatter);

                // Provjera da li je vrijeme u budućnosti
                if (vrijemeDostave.isBefore(LocalDateTime.now())) {
                    System.out.println("Vrijeme dostave mora biti u budućnosti. Pokušajte ponovo.");
                    isValid = false;
                }

            } catch (DateTimeParseException e) {
                System.out.println("Neispravan format datuma i vremena. Pokušajte ponovo.");
                isValid = false;
            }

        } while (!isValid);

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
