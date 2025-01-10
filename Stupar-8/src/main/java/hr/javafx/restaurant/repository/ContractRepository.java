package hr.javafx.restaurant.repository;

import hr.javafx.restaurant.enums.ContractType;
import hr.javafx.restaurant.model.Contract;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Represents a repository for managing {@link Contract} objects.
 * This class provides methods to retrieve, save, and manage contracts from a data source.
 * @param <T> a type parameter that extends {@link Contract}.
 */

public class ContractRepository <T extends Contract> extends AbstractRepository<T>{
    private static final String CONTRACTS_FILE_PATH = "dat/contracts.txt";
    private static final Integer NUMBER_OF_ROWS_PER_CONTRACTS = 5;

    @Override
    public T findById(Long id) {
        return findAll().stream()
                .filter(contract -> contract.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Set<T> findAll() {
        Set<T> contracts = new HashSet<>();
        try{
            Stream<String> stream = Files.lines(Path.of(CONTRACTS_FILE_PATH));
            List<String> fileRows = stream.collect(Collectors.toList());

            for(Integer i = 0; i < (fileRows.size() / NUMBER_OF_ROWS_PER_CONTRACTS); i++){
                Long id = Long.parseLong(fileRows.get(i * NUMBER_OF_ROWS_PER_CONTRACTS));
                BigDecimal salary = BigDecimal.valueOf(Double.parseDouble(fileRows.get(i * NUMBER_OF_ROWS_PER_CONTRACTS + 1)));
                LocalDate startTime = LocalDate.parse(fileRows.get(i * NUMBER_OF_ROWS_PER_CONTRACTS + 2));
                LocalDate endTime = LocalDate.parse(fileRows.get(i * NUMBER_OF_ROWS_PER_CONTRACTS + 3));
                ContractType contractType = ContractType.valueOf(fileRows.get(i * NUMBER_OF_ROWS_PER_CONTRACTS + 4));

                Contract contract = new Contract(id, salary, startTime, endTime, contractType);
                contracts.add((T) contract);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return contracts;
    }

    @Override
    public void save(List<T> entities) {
        try(PrintWriter writer = new PrintWriter(CONTRACTS_FILE_PATH)){
            for(T entity : entities){
                writer.println(entity.getId());
                writer.println(entity.getSalary());
                writer.println(entity.getStartTime());
                writer.println(entity.getEndTime());
                writer.println(entity.getContractType());
            }
            writer.flush();

        } catch (IOException e) {
            throw new RuntimeException("Failed to save entities to file: " + CONTRACTS_FILE_PATH, e);
        }
    }
}
