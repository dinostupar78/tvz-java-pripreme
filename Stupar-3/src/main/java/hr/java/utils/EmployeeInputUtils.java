package hr.java.utils;
import hr.java.restaurant.exception.DuplicateEntityException;
import hr.java.restaurant.exception.InvalidAmountException;
import hr.java.restaurant.model.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.Scanner;
import static hr.java.production.main.Main.log;
import static hr.java.utils.ExceptionUtils.*;

public class EmployeeInputUtils {
    private static long chefIdCounter = 0, waiterIdCounter = 0, delivererIdCounter = 0;

    public static Chef chefInput(Scanner scanner)  {
        String chefName, chefSurname;
        BigDecimal chefSalary = null;
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

        do {
            isValid = true;
            System.out.println("Unesite plaću: ");
            try {
                chefSalary = scanner.nextBigDecimal();
                scanner.nextLine();
            } catch (InputMismatchException badData) {
                log.info(Messages.INVALID_CHEF_INPUT);
                scanner.nextLine();
                isValid = false;
            } catch(InvalidAmountException badData){
                log.info(badData.getMessage());
                isValid = false;
            }
        } while(!isValid);

        System.out.println("Unesite datum početka ugovora (YYYY-MM-DD): ");
        String pocetakUgovoraInput = scanner.nextLine();
        contractStartTime = LocalDate.parse(pocetakUgovoraInput);

        System.out.println("Unesite datum završetka ugovora (YYYY-MM-DD): ");
        String zavrsetakUgovoraInput = scanner.nextLine();
        contractEndTime = LocalDate.parse(zavrsetakUgovoraInput);

        String contractType = "";
        do {
            isValid = true;
            System.out.println("Unesite tip ugovora (0 za FULL_TIME, 1 za PART_TIME): ");
            int tipUgovoraInput = scanner.nextInt();
            scanner.nextLine();
            if (tipUgovoraInput == 0) {
                contractType = Contract.FULL_TIME;
            } else if (tipUgovoraInput == 1) {
                contractType = Contract.PART_TIME;
            } else {
                log.info(Messages.INVALID_CONTRACT_INPUT);
                isValid = false;
            }
        } while (!isValid);

        Contract chefContract = new Contract(chefSalary, contractStartTime, contractEndTime, contractType);
        Bonus chefBonus = new Bonus(new BigDecimal(100.00));
        long id = chefIdCounter++;

        return new Chef.BuilderChef(id)
                .chefFirstName(chefName)
                .chefLastName(chefSurname)
                .chefContract(chefContract)
                .chefBonusKuhara(chefBonus)
                .build();
    }

    public static Waiter waiterInput(Scanner scanner){
        String waiterName, waiterSurname;
        BigDecimal chefSalary = new BigDecimal(0);
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
            System.out.println("Unesite prezime kuhara: ");
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

        do{
            isValid = true;
            System.out.println("Unesite plaću: ");
            try{
                chefSalary = scanner.nextBigDecimal();
                scanner.nextLine();
            }catch(InputMismatchException badData){
                log.info(Messages.INVALID_WAITER_INPUT);
                scanner.nextLine();
                isValid = false;
            } catch(InvalidAmountException badData){
                log.info(badData.getMessage());
                isValid = false;
            }
        } while(!isValid);

        System.out.println("Unesite datum početka ugovora (YYYY-MM-DD): ");
        String contractStartTimeInput = scanner.nextLine();
        contractStartTime = LocalDate.parse(contractStartTimeInput);

        System.out.println("Unesite datum završetka ugovora (YYYY-MM-DD): ");
        String contractEndTimeInput = scanner.nextLine();
        contractEndTime = LocalDate.parse(contractEndTimeInput);

        String contractType = "";
        do {
            isValid = true;
            System.out.println("Unesite tip ugovora (0 za FULL_TIME, 1 za PART_TIME): ");
            int tipUgovoraInput = scanner.nextInt();
            scanner.nextLine();
            if (tipUgovoraInput == 0) {
                contractType = Contract.FULL_TIME;
            } else if (tipUgovoraInput == 1) {
                contractType = Contract.PART_TIME;
            } else {
                log.info(Messages.INVALID_CONTRACT_INPUT);
                isValid = false;
            }
        } while(!isValid);

        Contract waiterContract = new Contract(chefSalary, contractStartTime, contractEndTime, contractType);
        Bonus waiterBonus = new Bonus(new BigDecimal(100.00));
        long id = waiterIdCounter++;

        return new Waiter.BuilderWaiter(id)
                .waiterFirstName(waiterName)
                .waiterLastName(waiterSurname)
                .waiterContract(waiterContract)
                .waiterBonusKonobara(waiterBonus)
                .build();
    }

    public static Deliverer delivererInput(Scanner scanner){
        String delivererName, delivererSurname;
        BigDecimal delivererSalary = null;
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

        do{
            isValid = true;
            System.out.println("Unesite plaću: ");
            try{
                delivererSalary = scanner.nextBigDecimal();
                scanner.nextLine();

            }catch(InputMismatchException badData){
                log.info(Messages.INVALID_DELIVERER_INPUT);
                scanner.nextLine();
                isValid = false;
            } catch(InvalidAmountException badData){
                log.info(badData.getMessage());
                isValid = false;
            }
        }while(!isValid);

        System.out.println("Unesite datum početka ugovora (YYYY-MM-DD): ");
        String contractStartTimeInput = scanner.nextLine();
        contractStartTime = LocalDate.parse(contractStartTimeInput);

        System.out.println("Unesite datum završetka ugovora (YYYY-MM-DD): ");
        String contractEndTimeInput = scanner.nextLine();
        contractEndTime = LocalDate.parse(contractEndTimeInput);

        String contractType = "";
        do {
            isValid = true;
            System.out.println("Unesite tip ugovora (0 za FULL_TIME, 1 za PART_TIME): ");
            int tipUgovoraInput = scanner.nextInt();
            scanner.nextLine();
            if (tipUgovoraInput == 0) {
                contractType = Contract.FULL_TIME;
            } else if (tipUgovoraInput == 1) {
                contractType = Contract.PART_TIME;
            } else {
                System.out.println(Messages.INVALID_CONTRACT_INPUT);
                isValid = false;
            }
        } while(!isValid);

        Contract delivererContract = new Contract(delivererSalary, contractStartTime, contractEndTime, contractType);
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