package hr.java.utils;
import hr.java.restaurant.model.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.Scanner;

public class EmployeeUtils {
    public static Chef chefInput(Scanner scanner){
        String imeKuhara, prezimeKuhara;
        BigDecimal placaKuhara = null;
        Boolean jeIspravan = false;
        LocalDate pocetakUgovora = LocalDate.now();
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

        do{
            jeIspravan = true;
            System.out.println("Unesite plaću: ");
            try{
                placaKuhara = scanner.nextBigDecimal();
                scanner.nextLine();

            }catch(InputMismatchException badData){
                System.out.println(Messages.INVALID_CHEF_INPUT);
                scanner.nextLine();
                jeIspravan = false;
                continue;
            }
            if (placaKuhara.compareTo(BigDecimal.ZERO) < 0 || placaKuhara.compareTo(BigDecimal.valueOf(10000)) > 0) {
                System.out.println(Messages.INVALID_CHEF_INPUT);
                jeIspravan = false;
            }

        }while(!jeIspravan);

        System.out.println("Unesite datum završetka ugovora (YYYY-MM-DD): ");
        String zavrsetakUgovoraInput = scanner.nextLine();
        zavrsetakUgovora = LocalDate.parse(zavrsetakUgovoraInput);

        Contract ugovorKuhara = new Contract(placaKuhara, pocetakUgovora, zavrsetakUgovora, Contract.FULL_TIME);

        Bonus bonusKuhara = new Bonus(new BigDecimal(100.00));

        return new Chef.BuilderChef(0L)
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
        LocalDate pocetakUgovora = LocalDate.now();
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

        System.out.println("Unesite datum završetka ugovora (YYYY-MM-DD): ");
        String zavrsetakUgovoraInput = scanner.nextLine();
        zavrsetakUgovora = LocalDate.parse(zavrsetakUgovoraInput);

        Contract ugovorKonobara = new Contract(placaKonobara, pocetakUgovora, zavrsetakUgovora, Contract.FULL_TIME);

        Bonus bonusKonobara = new Bonus(new BigDecimal(100.00));

        return new Waiter.BuilderWaiter(0L)
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
        LocalDate pocetakUgovora = LocalDate.now();
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

        System.out.println("Unesite datum završetka ugovora (YYYY-MM-DD): ");
        String zavrsetakUgovoraInput = scanner.nextLine();
        zavrsetakUgovora = LocalDate.parse(zavrsetakUgovoraInput);

        Contract ugovorDostavljaca = new Contract(placaDostavljaca, pocetakUgovora, zavrsetakUgovora, Contract.FULL_TIME);

        Bonus bonusDostavljaca = new Bonus(new BigDecimal(100.00));

        return new Deliverer.BuilderDeliverer(0L)
                .delivererFirstName(imeDostavljaca)
                .delivererLastName(prezimeDostavljaca)
                .delivererContract(ugovorDostavljaca)
                .delivererBonusDostavljaca(bonusDostavljaca)
                .build();
    }
}
