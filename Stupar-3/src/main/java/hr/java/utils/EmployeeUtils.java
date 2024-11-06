package hr.java.utils;
import hr.java.restaurant.model.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.Scanner;

public class EmployeeUtils {
    private static long chefIdCounter = 0, waiterIdCounter = 0, delivererIdCounter = 0;

    public static Chef chefInput(Scanner scanner) {
        String imeKuhara, prezimeKuhara;
        BigDecimal placaKuhara = null;
        Boolean jeIspravan = false;
        LocalDate pocetakUgovora;
        LocalDate zavrsetakUgovora;

        do {
            jeIspravan = true;
            System.out.println("Unesite ime kuhara: ");
            imeKuhara = scanner.nextLine();
            if (imeKuhara.isEmpty() || imeKuhara.length() < 2) {
                System.out.println(Messages.INVALID_CHEF_INPUT);
                jeIspravan = false;
            }
        } while (!jeIspravan);

        do {
            jeIspravan = true;
            System.out.println("Unesite prezime kuhara: ");
            prezimeKuhara = scanner.nextLine();
            if (prezimeKuhara.isEmpty() || prezimeKuhara.length() < 2) {
                System.out.println(Messages.INVALID_CHEF_INPUT);
                jeIspravan = false;
            }
        } while (!jeIspravan);

        do {
            jeIspravan = true;
            System.out.println("Unesite plaću: ");
            try {
                placaKuhara = scanner.nextBigDecimal();
                scanner.nextLine();

            } catch (InputMismatchException badData) {
                System.out.println(Messages.INVALID_CHEF_INPUT);
                scanner.nextLine();
                jeIspravan = false;
                continue;
            }
            if (placaKuhara.compareTo(BigDecimal.ZERO) < 0 || placaKuhara.compareTo(BigDecimal.valueOf(10000)) > 0) {
                System.out.println(Messages.INVALID_CHEF_INPUT);
                jeIspravan = false;
            }

        } while (!jeIspravan);

        System.out.println("Unesite datum početka ugovora (YYYY-MM-DD): ");
        String pocetakUgovoraInput = scanner.nextLine();
        pocetakUgovora = LocalDate.parse(pocetakUgovoraInput);

        System.out.println("Unesite datum završetka ugovora (YYYY-MM-DD): ");
        String zavrsetakUgovoraInput = scanner.nextLine();
        zavrsetakUgovora = LocalDate.parse(zavrsetakUgovoraInput);

        String tipUgovora = "";
        do {
            jeIspravan = true;
            System.out.println("Unesite tip ugovora (0 za FULL_TIME, 1 za PART_TIME): ");
            int tipUgovoraInput = scanner.nextInt();
            scanner.nextLine();
            if (tipUgovoraInput == 0) {
                tipUgovora = Contract.FULL_TIME;
            } else if (tipUgovoraInput == 1) {
                tipUgovora = Contract.PART_TIME;
            } else {
                System.out.println(Messages.INVALID_CONTRACT_INPUT);
                jeIspravan = false;
            }
        } while (!jeIspravan);

        Contract ugovorKuhara = new Contract(placaKuhara, pocetakUgovora, zavrsetakUgovora, tipUgovora);

        Bonus bonusKuhara = new Bonus(new BigDecimal(100.00));

        long id = chefIdCounter++;

        return new Chef.BuilderChef(id)
                .chefFirstName(imeKuhara)
                .chefLastName(prezimeKuhara)
                .chefContract(ugovorKuhara)
                .chefBonusKuhara(bonusKuhara)
                .build();
    }

    public static Waiter waiterInput(Scanner scanner){
        String imeKonobara, prezimeKonobara;
        BigDecimal placaKonobara = null;
        Boolean jeIspravan = false;
        LocalDate pocetakUgovora;
        LocalDate zavrsetakUgovora;

        do {
            jeIspravan = true;
            System.out.println("Unesite ime konobara: ");
            imeKonobara = scanner.nextLine();
            if (imeKonobara.isEmpty() || imeKonobara.length() < 2) {
                System.out.println(Messages.INVALID_WAITER_INPUT);
                jeIspravan = false;
            }
        } while (!jeIspravan);

        do {
            jeIspravan = true;
            System.out.println("Unesite prezime kuhara: ");
            prezimeKonobara = scanner.nextLine();
            if (prezimeKonobara.length() < 3) {
                System.out.println(Messages.INVALID_WAITER_INPUT);
                jeIspravan = false;
            }
        } while (!jeIspravan);

        do{
            jeIspravan = true;
            System.out.println("Unesite plaću: ");
            try{
                placaKonobara = scanner.nextBigDecimal();
                scanner.nextLine();

            }catch(InputMismatchException badData){
                System.out.println(Messages.INVALID_WAITER_INPUT);
                scanner.nextLine();
                jeIspravan = false;
                continue;
            }
            if (placaKonobara.compareTo(BigDecimal.ZERO) < 0 || placaKonobara.compareTo(BigDecimal.valueOf(10000)) > 0) {
                System.out.println(Messages.INVALID_WAITER_INPUT);
                jeIspravan = false;
            }

        }while(!jeIspravan);

        System.out.println("Unesite datum početka ugovora (YYYY-MM-DD): ");
        String pocetakUgovoraInput = scanner.nextLine();
        pocetakUgovora = LocalDate.parse(pocetakUgovoraInput);

        System.out.println("Unesite datum završetka ugovora (YYYY-MM-DD): ");
        String zavrsetakUgovoraInput = scanner.nextLine();
        zavrsetakUgovora = LocalDate.parse(zavrsetakUgovoraInput);

        String tipUgovora = "";
        do {
            jeIspravan = true;
            System.out.println("Unesite tip ugovora (0 za FULL_TIME, 1 za PART_TIME): ");
            int tipUgovoraInput = scanner.nextInt();
            scanner.nextLine();
            if (tipUgovoraInput == 0) {
                tipUgovora = Contract.FULL_TIME;
            } else if (tipUgovoraInput == 1) {
                tipUgovora = Contract.PART_TIME;
            } else {
                System.out.println(Messages.INVALID_CONTRACT_INPUT);
                jeIspravan = false;
            }
        } while (!jeIspravan);

        Contract ugovorKonobara = new Contract(placaKonobara, pocetakUgovora, zavrsetakUgovora, tipUgovora);

        Bonus bonusKonobara = new Bonus(new BigDecimal(100.00));

        long id = waiterIdCounter++;

        return new Waiter.BuilderWaiter(id)
                .waiterFirstName(imeKonobara)
                .waiterLastName(prezimeKonobara)
                .waiterContract(ugovorKonobara)
                .waiterBonusKonobara(bonusKonobara)
                .build();
    }

    public static Deliverer delivererInput(Scanner scanner){
        String imeDostavljaca, prezimeDostavljaca;
        BigDecimal placaDostavljaca = null;
        Boolean jeIspravan = false;
        LocalDate pocetakUgovora;
        LocalDate zavrsetakUgovora;

        do {
            jeIspravan = true;
            System.out.println("Unesite ime dostavljača: ");
            imeDostavljaca = scanner.nextLine();
            if (imeDostavljaca.isEmpty() || imeDostavljaca.length() < 3) {
                System.out.println(Messages.INVALID_DELIVERER_INPUT);
                jeIspravan = false;
            }
        } while (!jeIspravan);

        do {
            jeIspravan = true;
            System.out.println("Unesite prezime dostavljača: ");
            prezimeDostavljaca = scanner.nextLine();
            if (prezimeDostavljaca.isEmpty() || prezimeDostavljaca.length() < 3) {
                System.out.println(Messages.INVALID_DELIVERER_INPUT);
                jeIspravan = false;
            }
        } while (!jeIspravan);

        do{
            jeIspravan = true;
            System.out.println("Unesite plaću: ");
            try{
                placaDostavljaca = scanner.nextBigDecimal();
                scanner.nextLine();

            }catch(InputMismatchException badData){
                System.out.println(Messages.INVALID_DELIVERER_INPUT);
                scanner.nextLine();
                jeIspravan = false;
                continue;
            }
            if (placaDostavljaca.compareTo(BigDecimal.ZERO) < 0 || placaDostavljaca.compareTo(BigDecimal.valueOf(10000)) > 0) {
                System.out.println(Messages.INVALID_DELIVERER_INPUT);
                jeIspravan = false;
            }

        }while(!jeIspravan);

        System.out.println("Unesite datum početka ugovora (YYYY-MM-DD): ");
        String pocetakUgovoraInput = scanner.nextLine();
        pocetakUgovora = LocalDate.parse(pocetakUgovoraInput);

        System.out.println("Unesite datum završetka ugovora (YYYY-MM-DD): ");
        String zavrsetakUgovoraInput = scanner.nextLine();
        zavrsetakUgovora = LocalDate.parse(zavrsetakUgovoraInput);

        String tipUgovora = "";
        do {
            jeIspravan = true;
            System.out.println("Unesite tip ugovora (0 za FULL_TIME, 1 za PART_TIME): ");
            int tipUgovoraInput = scanner.nextInt();
            scanner.nextLine();
            if (tipUgovoraInput == 0) {
                tipUgovora = Contract.FULL_TIME;
            } else if (tipUgovoraInput == 1) {
                tipUgovora = Contract.PART_TIME;
            } else {
                System.out.println(Messages.INVALID_CONTRACT_INPUT);
                jeIspravan = false;
            }
        } while (!jeIspravan);

        Contract ugovorDostavljaca = new Contract(placaDostavljaca, pocetakUgovora, zavrsetakUgovora, tipUgovora);

        Bonus bonusDostavljaca = new Bonus(new BigDecimal(100.00));

        long id = delivererIdCounter++;

        return new Deliverer.BuilderDeliverer(id)
                .delivererFirstName(imeDostavljaca)
                .delivererLastName(prezimeDostavljaca)
                .delivererContract(ugovorDostavljaca)
                .delivererBonusDostavljaca(bonusDostavljaca)
                .build();
    }
}
