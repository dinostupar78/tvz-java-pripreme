package hr.java.production.main;

import hr.java.restaurant.model.Meal;
import hr.java.restaurant.model.MealData;

import java.io.*;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class TestMain {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            // 1. Serijalizacija podataka u binarnu datoteku
            System.out.println("Unesite datum (YYYY-MM-DD): ");
            LocalDate date = LocalDate.parse(scanner.nextLine());

            System.out.println("Unesite ime: ");
            String firstName = scanner.nextLine();

            System.out.println("Unesite prezime: ");
            String lastName = scanner.nextLine();

            System.out.println("Unesite ime jela: ");
            String mealName = scanner.nextLine();

            System.out.println("Unesite cijenu: ");
            BigDecimal price = new BigDecimal(scanner.nextLine());

            MealData mealData = new MealData(date, firstName, lastName, mealName, price);

            String filePath = "mealData.dat";
            serializeData(mealData, filePath);

            System.out.println("Podaci su uspješno serijalizirani u " + filePath);

            // 2. Traženje korisničkog puta i odabir binarne datoteke
            Path projectPath = requestProjectPath(scanner);

            // 3. Učitavanje i verifikacija binarne datoteke
            File selectedFile = selectBinaryFile(scanner, projectPath);

            MealData loadedData = deserializeData(selectedFile.getAbsolutePath());
            System.out.println("Podaci iz odabrane datoteke:\n" + loadedData);

            // 4. Usporedba cijena s dostupnim jelima
            List<Meal> meals = List.of(
                    new Meal(1L, "Pizza", null, null, new BigDecimal("50.0"), 800),
                    new Meal(2L, "Burger", null, null, new BigDecimal("40.0"), 600),
                    new Meal(3L, "Pasta", null, null, new BigDecimal("60.0"), 700)
            );

            comparePricesAndSave(meals, loadedData, "meals2.txt");
            System.out.println("Usporedba završena. Podaci su spremljeni u meals2.txt");

        } catch (Exception e) {
            System.out.println("Došlo je do pogreške: " + e.getMessage());
        }
    }

    private static void serializeData(MealData data, String filePath) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filePath))) {
            out.writeObject(data);
        }
    }

    private static MealData deserializeData(String filePath) throws IOException, ClassNotFoundException {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filePath))) {
            return (MealData) in.readObject();
        }
    }

    private static Path requestProjectPath(Scanner scanner) {
        while (true) {
            System.out.println("Unesite path projekta: ");
            String input = scanner.nextLine();
            Path path = Path.of(input);

            if (Files.exists(path) && Files.isDirectory(path)) {
                return path;
            } else {
                System.out.println("Neispravan path. Pokušajte ponovo.");
            }
        }
    }

    private static File selectBinaryFile(Scanner scanner, Path directoryPath) {
        while (true) {
            try {
                List<File> binaryFiles = Files.list(directoryPath)
                        .map(Path::toFile)
                        .filter(file -> file.getName().endsWith(".dat"))
                        .collect(Collectors.toList());

                System.out.println("Dostupne binarne datoteke:");
                for (int i = 0; i < binaryFiles.size(); i++) {
                    System.out.println((i + 1) + ". " + binaryFiles.get(i).getName());
                }

                System.out.println("Odaberite broj datoteke: ");
                int choice = Integer.parseInt(scanner.nextLine());

                if (choice > 0 && choice <= binaryFiles.size()) {
                    return binaryFiles.get(choice - 1);
                } else {
                    System.out.println("Neispravan izbor. Pokušajte ponovo.");
                }
            } catch (Exception e) {
                System.out.println("Pogreška prilikom odabira datoteke. Pokušajte ponovo.");
            }
        }
    }

    private static void comparePricesAndSave(List<Meal> meals, MealData data, String outputFile) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(outputFile))) {
            writer.println("Usporedba cijena za jelo iz datoteke: " + data.getMealName() + " (Cijena: " + data.getPrice() + ")");
            BigDecimal totalPrice = BigDecimal.ZERO;

            for (Meal meal : meals) {
                if (data.getPrice().compareTo(meal.getPrice()) > 0) {
                    writer.println(meal.getName() + ": Cijena iz datoteke je veća.");
                } else if (data.getPrice().compareTo(meal.getPrice()) < 0) {
                    writer.println(meal.getName() + ": Cijena iz datoteke je manja.");
                    totalPrice = totalPrice.add(meal.getPrice());
                } else {
                    writer.println(meal.getName() + ": Cijene su jednake.");
                }
            }

            writer.println("Ukupna cijena jela s manjom cijenom: " + totalPrice);
        }
    }
}
