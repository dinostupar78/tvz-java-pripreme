package hr.javafx.restaurant.repository;

import hr.javafx.restaurant.model.Bonus;
import hr.javafx.restaurant.model.Contract;
import hr.javafx.restaurant.model.Waiter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Represents a repository for managing {@link Waiter} objects.
 * This class provides methods to retrieve and save waiters from/to a data source.
 * @param <T> a type parameter that extends {@link Waiter}.
 */

public class WaiterRepository <T extends Waiter> extends AbstractRepository<T>{
    private static final String WAITERS_FILE_PATH = "dat/waiters.txt";
    private static final Integer NUMBER_OF_ROWS_PER_WAITERS = 5;

    public WaiterRepository(ContractRepository<Contract> contractRepository) {
        this.contractRepository = contractRepository;
    }

    public ContractRepository<Contract> contractRepository;
    @Override
    public T findById(Long id) {
        return findAll().stream()
                .filter(waiter -> waiter.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Set<T> findAll() {
        Set<T> waiters = new HashSet<>();
        try{
            Stream<String> stream = Files.lines(Path.of(WAITERS_FILE_PATH));
            List<String> fileRows = stream.collect(Collectors.toList());

            for(Integer i = 0; i < (fileRows.size() / NUMBER_OF_ROWS_PER_WAITERS); i++){
                Long id = Long.parseLong(fileRows.get(i * NUMBER_OF_ROWS_PER_WAITERS));
                String firstName = fileRows.get(i * NUMBER_OF_ROWS_PER_WAITERS + 1);
                String lastName = fileRows.get(i * NUMBER_OF_ROWS_PER_WAITERS + 2);
                Long contractId = Long.parseLong(fileRows.get(i * NUMBER_OF_ROWS_PER_WAITERS + 3));

                Contract contract = contractRepository.findById(contractId);

                BigDecimal bonusAmount = BigDecimal.valueOf(Double.parseDouble(fileRows.get(i * NUMBER_OF_ROWS_PER_WAITERS + 4)));
                Bonus waiterBonus = new Bonus(bonusAmount);

                @SuppressWarnings("unchecked")
                T waiter = (T) new Waiter.BuilderWaiter(id)
                        .waiterFirstName(firstName)
                        .waiterLastName(lastName)
                        .waiterContract(contract)
                        .waiterBonusKonobara(waiterBonus)
                        .build();

                waiters.add(waiter);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return waiters;
    }


    @Override
    public void save(List<T> entities) { // RADI
        try(PrintWriter writer = new PrintWriter(WAITERS_FILE_PATH)){
            for(T entity : entities){
                if (entity.getContract() == null) {
                    throw new NullPointerException("Entity " + entity.getId() + " has no contract.");
                }
                writer.println(entity.getId());
                writer.println(entity.getFirstName());
                writer.println(entity.getLastName());
                writer.println(entity.getContract().getId());
                writer.println(entity.getBonus());

            }
            writer.flush();

        } catch (FileNotFoundException e) {
            throw new RuntimeException("Failed to save entities to file: " + WAITERS_FILE_PATH, e);
        }
    }
}
