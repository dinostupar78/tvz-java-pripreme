package hr.javafx.utils;

import hr.javafx.restaurant.enums.ContractType;
import hr.javafx.restaurant.exception.DuplicateEntityException;
import hr.javafx.restaurant.exception.InvalidAmountException;
import hr.javafx.restaurant.model.*;

import java.math.BigDecimal;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.Scanner;

import static hr.javafx.main.InputMain.log;
import static hr.javafx.utils.ExceptionUtils.*;

/**
 * Utility klasa koja omogućuje unos podataka za različite vrste zaposlenika u restoranu, uključujući kuhare, konobare i dostavljače.
 * Klasa sadrži metode za unos imena, prezimena, plaće, datuma početka i završetka ugovora, te tipa ugovora,
 * a također provodi validaciju podataka kako bi osigurala ispravnost unesenih vrijednosti.
 */

public class EmployeeInputUtils {
    private static long chefIdCounter = 0, waiterIdCounter = 0, delivererIdCounter = 0,
    chefContractIdCounter = 0, waiterContractIdCounter = 0, delivererContractIdCounter = 0;

    /**
     * Unos podataka za kuhara. Provodi validaciju unosa, uključujući provjeru duplikata.
     * @param scanner Scanner za unos s tipkovnice
     * @return novokreirani objekt {@link Chef}
     */

    public static Chef chefInput(Scanner scanner)  {
        String chefName, chefSurname;
        BigDecimal chefSalary = new BigDecimal(0);;
        Boolean isValid = false;
        LocalDate contractStartTime, contractEndTime;

        do {
            isValid = true;
            System.out.println("Unesite ime kuhara: ");
            chefName = scanner.nextLine();
            if (chefName.isEmpty() || chefName.length() < 2) {
                log.info(Messages.INVALID_CHEF_INPUT);
                isValid = false;
            } else {
                try{
                    checkDuplicateChefData(chefName);
                } catch(DuplicateEntityException badData){
                    log.info(badData.getMessage());
                    isValid = false;
                }
            }
        } while (!isValid);

        do {
            isValid = true;
            System.out.println("Unesite prezime kuhara: ");
            chefSurname = scanner.nextLine();
            if (chefSurname.isEmpty() || chefSurname.length() < 2) {
                log.info(Messages.INVALID_CHEF_INPUT);
                isValid = false;
            } else {
                try{
                    checkDuplicateChefData(chefSurname);
                } catch(DuplicateEntityException badData){
                    log.info(badData.getMessage());
                    isValid = false;
                }
            }
        } while(!isValid);

        long chefContractId = chefContractIdCounter++;

        do {
            isValid = true;
            System.out.println("Unesite plaću: ");
            try {
                chefSalary = scanner.nextBigDecimal();
                scanner.nextLine();
                validateSalary(chefSalary);
            } catch (InputMismatchException badData) {
                log.info(Messages.INVALID_CHEF_INPUT);
                scanner.nextLine();
                isValid = false;
            } catch(InvalidAmountException badData){
                log.info(badData.getMessage());
                isValid = false;
            }
        } while(!isValid);

        do{
            isValid = true;
            contractStartTime = LocalDate.now();
            System.out.println("Unesite datum početka ugovora (YYYY-MM-DD): ");
            try{
                String pocetakUgovoraInput = scanner.nextLine();
                contractStartTime = LocalDate.parse(pocetakUgovoraInput);
            }catch(DateTimeException badData){
                log.info(Messages.INVALID_DATE_INPUT);
                isValid = false;
            }
        } while(!isValid);

        do{
            isValid = true;
            contractEndTime = LocalDate.now();
            System.out.println("Unesite datum završetka ugovora (YYYY-MM-DD): ");
            try{
                String zavrsetakUgovoraInput = scanner.nextLine();
                contractEndTime = LocalDate.parse(zavrsetakUgovoraInput);
            }catch(DateTimeException badData){
                log.info(Messages.INVALID_DATE_INPUT);
                isValid = false;
            }
        } while(!isValid);

        ContractType contractType = ContractType.NOT_DEFINED;

        do {
            isValid = true;
            System.out.println("Unesite tip ugovora (0 za FULL_TIME, 1 za PART_TIME): ");
            int tipUgovoraInput = scanner.nextInt();
            scanner.nextLine();
            if (tipUgovoraInput == 0) {
                contractType = ContractType.FULL_TIME;
            } else if (tipUgovoraInput == 1) {
                contractType = ContractType.PART_TIME;
            } else {
                log.info(Messages.INVALID_CONTRACT_INPUT);
                isValid = false;
            }
        } while (!isValid);

        Contract chefContract = new Contract(chefContractId, chefSalary, contractStartTime, contractEndTime, contractType);
        Bonus chefBonus = new Bonus(new BigDecimal(100.00));
        long id = chefIdCounter++;

        return new Chef.BuilderChef(id)
                .chefFirstName(chefName)
                .chefLastName(chefSurname)
                .chefContract(chefContract)
                .chefBonusKuhara(chefBonus)
                .build();
    }

    /**
     * Unos podataka za konobara. Provodi validaciju unosa, uključujući provjeru duplikata.
     * @param scanner Scanner za unos s tipkovnice
     * @return novokreirani objekt {@link Waiter}
     */

    public static Waiter waiterInput(Scanner scanner){
        String waiterName, waiterSurname;
        BigDecimal waiterSalary = new BigDecimal(0);
        Boolean isValid = false;
        LocalDate contractStartTime;
        LocalDate contractEndTime;

        do {
            isValid = true;
            System.out.println("Unesite ime konobara: ");
            waiterName = scanner.nextLine();
            if (waiterName.isEmpty() || waiterName.length() < 2) {
                log.info(Messages.INVALID_WAITER_INPUT);
                isValid = false;
            } else {
                    try{
                        checkDuplicateWaiterData(waiterName);
                    } catch(DuplicateEntityException badData){
                        log.info(badData.getMessage());
                        isValid = false;
                    }
                }
        } while(!isValid);

        do {
            isValid = true;
            System.out.println("Unesite prezime konobara: ");
            waiterSurname = scanner.nextLine();
            if (waiterSurname.isEmpty() ||waiterSurname.length() < 3) {
                log.info(Messages.INVALID_WAITER_INPUT);
                isValid = false;
            } else {
                try{
                    checkDuplicateWaiterData(waiterSurname);
                } catch(DuplicateEntityException badData){
                    log.info(badData.getMessage());
                    isValid = false;
                }
            }
        } while(!isValid);

        long waiterContractId = waiterContractIdCounter++;

        do{
            isValid = true;
            System.out.println("Unesite plaću: ");
            try{
                waiterSalary = scanner.nextBigDecimal();
                scanner.nextLine();
                validateSalary(waiterSalary);
            }catch(InputMismatchException badData){
                log.info(Messages.INVALID_WAITER_INPUT);
                scanner.nextLine();
                isValid = false;
            } catch(InvalidAmountException badData){
                log.info(badData.getMessage());
                isValid = false;
            }
        } while(!isValid);

        do{
            isValid = true;
            contractStartTime = LocalDate.now();
            System.out.println("Unesite datum početka ugovora (YYYY-MM-DD): ");
            try{
                String pocetakUgovoraInput = scanner.nextLine();
                contractStartTime = LocalDate.parse(pocetakUgovoraInput);
            }catch(DateTimeException badData){
                log.info(Messages.INVALID_DATE_INPUT);
                isValid = false;
            }
        } while(!isValid);

        do{
            isValid = true;
            contractEndTime = LocalDate.now();
            System.out.println("Unesite datum završetka ugovora (YYYY-MM-DD): ");
            try{
                String zavrsetakUgovoraInput = scanner.nextLine();
                contractEndTime = LocalDate.parse(zavrsetakUgovoraInput);
            }catch(DateTimeException badData){
                log.info(Messages.INVALID_DATE_INPUT);
                isValid = false;
            }
        } while(!isValid);

        ContractType contractType = ContractType.NOT_DEFINED;
        do {
            isValid = true;
            System.out.println("Unesite tip ugovora (0 za FULL_TIME, 1 za PART_TIME): ");
            int tipUgovoraInput = scanner.nextInt();
            scanner.nextLine();
            if (tipUgovoraInput == 0) {
                contractType = ContractType.FULL_TIME;
            } else if (tipUgovoraInput == 1) {
                contractType = ContractType.PART_TIME;
            } else {
                log.info(Messages.INVALID_CONTRACT_INPUT);
                isValid = false;
            }
        } while(!isValid);

        Contract waiterContract = new Contract(waiterContractId, waiterSalary, contractStartTime, contractEndTime, contractType);
        Bonus waiterBonus = new Bonus(new BigDecimal(100.00));
        long id = waiterIdCounter++;

        return new Waiter.BuilderWaiter(id)
                .waiterFirstName(waiterName)
                .waiterLastName(waiterSurname)
                .waiterContract(waiterContract)
                .waiterBonusKonobara(waiterBonus)
                .build();
    }

    /**
     * Unos podataka za dostavljača. Provodi validaciju unosa, uključujući provjeru duplikata.
     * @param scanner Scanner za unos s tipkovnice
     * @return novokreirani objekt {@link Deliverer}
     */

    public static Deliverer delivererInput(Scanner scanner){
        String delivererName, delivererSurname;
        BigDecimal delivererSalary = new BigDecimal(0);;
        Boolean isValid = false;
        LocalDate contractStartTime;
        LocalDate contractEndTime;

        do {
            isValid = true;
            System.out.println("Unesite ime dostavljača: ");
            delivererName = scanner.nextLine();
            if (delivererName.isEmpty() || delivererName.length() < 3) {
                log.info(Messages.INVALID_DELIVERER_INPUT);
                isValid = false;
            } else{
                try{
                    checkDuplicateDelivererData(delivererName);
                } catch(DuplicateEntityException badData){
                    log.info(badData.getMessage());
                    isValid = false;
                }
            }
        } while(!isValid);

        do {
            isValid = true;
            System.out.println("Unesite prezime dostavljača: ");
            delivererSurname = scanner.nextLine();
            if (delivererSurname.isEmpty() || delivererSurname.length() < 3) {
                log.info(Messages.INVALID_DELIVERER_INPUT);
                isValid = false;
            } else{
                try{
                    checkDuplicateDelivererData(delivererSurname);
                } catch(DuplicateEntityException badData){
                    log.info(badData.getMessage());
                    isValid = false;
                }
            }
        } while (!isValid);

        long delivererContractId = delivererContractIdCounter++;

        do{
            isValid = true;
            System.out.println("Unesite plaću: ");
            try{
                delivererSalary = scanner.nextBigDecimal();
                scanner.nextLine();
                validateSalary(delivererSalary);
            }catch(InputMismatchException badData){
                log.info(Messages.INVALID_DELIVERER_INPUT);
                scanner.nextLine();
                isValid = false;
            } catch(InvalidAmountException badData){
                log.info(badData.getMessage());
                isValid = false;
            }
        }while(!isValid);

        do{
            isValid = true;
            contractStartTime = LocalDate.now();
            System.out.println("Unesite datum početka ugovora (YYYY-MM-DD): ");
            try{
                String pocetakUgovoraInput = scanner.nextLine();
                contractStartTime = LocalDate.parse(pocetakUgovoraInput);
            }catch(DateTimeException badData){
                log.info(Messages.INVALID_DATE_INPUT);
                isValid = false;
            }
        } while(!isValid);

        do{
            isValid = true;
            contractEndTime = LocalDate.now();
            System.out.println("Unesite datum završetka ugovora (YYYY-MM-DD): ");
            try{
                String zavrsetakUgovoraInput = scanner.nextLine();
                contractEndTime = LocalDate.parse(zavrsetakUgovoraInput);
            }catch(DateTimeException badData){
                log.info(Messages.INVALID_DATE_INPUT);
                isValid = false;
            }
        } while(!isValid);

        ContractType contractType = ContractType.NOT_DEFINED;
        do {
            isValid = true;
            System.out.println("Unesite tip ugovora (0 za FULL_TIME, 1 za PART_TIME): ");
            int tipUgovoraInput = scanner.nextInt();
            scanner.nextLine();
            if (tipUgovoraInput == 0) {
                contractType = ContractType.FULL_TIME;
            } else if (tipUgovoraInput == 1) {
                contractType = ContractType.PART_TIME;
            } else {
                System.out.println(Messages.INVALID_CONTRACT_INPUT);
                isValid = false;
            }
        } while(!isValid);

        Contract delivererContract = new Contract(delivererContractId, delivererSalary, contractStartTime, contractEndTime, contractType);
        Bonus delivererBonus = new Bonus(new BigDecimal(100.00));
        long id = delivererIdCounter++;

        return new Deliverer.BuilderDeliverer(id)
                .delivererFirstName(delivererName)
                .delivererLastName(delivererSurname)
                .delivererContract(delivererContract)
                .delivererBonusDostavljaca(delivererBonus)
                .build();
    }
}
