package hr.java.utils;
import hr.java.restaurant.model.Chef;
import hr.java.restaurant.model.Deliverer;
import hr.java.restaurant.model.Waiter;

import java.math.BigDecimal;
import java.util.InputMismatchException;
import java.util.Scanner;

public class EmployeeUtils {
    public static Chef chefInput(Scanner scanner){
        String imeKuhara, prezimeKuhara;
        BigDecimal placaKuhara = null;
        Boolean jeIspravan = false;

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

        return new Chef(0L, imeKuhara, prezimeKuhara, placaKuhara);
    }

    public static Waiter waiterInput(Scanner scanner){
        String imeKonobara, prezimeKonobara;
        BigDecimal placaKonobara = null;
        Boolean jeIspravan = false;

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

        return new Waiter(0L, imeKonobara, prezimeKonobara, placaKonobara);
    }

    public static Deliverer delivererInput(Scanner scanner){
        String imeDostavljaca, prezimeDostavljaca;
        BigDecimal placaDostavljaca = null;
        Boolean jeIspravan = false;

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
                System.out.println(Messages.INVALID_CHEF_INPUT);
                scanner.nextLine();
                jeIspravan = false;
                continue;
            }
            if (placaDostavljaca.compareTo(BigDecimal.ZERO) < 0 || placaDostavljaca.compareTo(BigDecimal.valueOf(10000)) > 0) {
                System.out.println(Messages.INVALID_CHEF_INPUT);
                jeIspravan = false;
            }

        }while(!jeIspravan);

        return new Deliverer(0L, imeDostavljaca, prezimeDostavljaca, placaDostavljaca);
    }
}
