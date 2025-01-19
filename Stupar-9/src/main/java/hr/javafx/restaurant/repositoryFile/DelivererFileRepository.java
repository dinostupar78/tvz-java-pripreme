package hr.javafx.restaurant.repositoryFile;

import hr.javafx.restaurant.model.Bonus;
import hr.javafx.restaurant.model.Contract;
import hr.javafx.restaurant.model.Deliverer;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Represents a repository for managing {@link Deliverer} objects.
 * This class provides methods to retrieve, save, and manage deliverer data from a data source.
 * @param <T> a type parameter that extends {@link Deliverer}.
 */

public class DelivererFileRepository<T extends Deliverer> extends AbstractFileRepository<T> {
    private static final String DELIVERERS_FILE_PATH = "dat/deliverers.txt";
    private static final Integer NUMBER_OF_ROWS_PER_DELIVERERS = 5;

    public DelivererFileRepository(ContractFileRepository<Contract> contractRepository) {
        this.contractRepository = contractRepository;
    }

    public ContractFileRepository<Contract> contractRepository;

    @Override
    public T findById(Long id) {
        return findAll().stream()
                .filter(deliverer -> deliverer.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Set<T> findAll() {
        Set<T> deliverers = new HashSet<>();
        try{
            Stream<String> stream = Files.lines(Path.of(DELIVERERS_FILE_PATH));
            List<String> fileRows = stream.collect(Collectors.toList());

            for(Integer i = 0; i < (fileRows.size() / NUMBER_OF_ROWS_PER_DELIVERERS); i++){
                Long id = Long.parseLong(fileRows.get(i * NUMBER_OF_ROWS_PER_DELIVERERS));
                String firstName = fileRows.get(i * NUMBER_OF_ROWS_PER_DELIVERERS + 1);
                String lastName = fileRows.get(i * NUMBER_OF_ROWS_PER_DELIVERERS + 2);
                Long contractId = Long.parseLong(fileRows.get(i * NUMBER_OF_ROWS_PER_DELIVERERS + 3));

                Contract contract = contractRepository.findById(contractId);

                BigDecimal bonusAmount = BigDecimal.valueOf(Double.parseDouble(fileRows.get(i * NUMBER_OF_ROWS_PER_DELIVERERS + 4)));
                Bonus delivererBonus = new Bonus(bonusAmount);

                @SuppressWarnings("unchecked")
                T deliverer = (T) new Deliverer.BuilderDeliverer(id)
                        .delivererFirstName(firstName)
                        .delivererLastName(lastName)
                        .delivererContract(contract)
                        .delivererBonusDostavljaca(delivererBonus)
                        .build();

                deliverers.add(deliverer);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return deliverers;
    }

    @Override
    public void save(Set<T> entities) { // RADI
        try(PrintWriter writer = new PrintWriter(DELIVERERS_FILE_PATH)){
            for(T entity : entities){
                if (entity.getContract() == null) {
                    throw new NullPointerException("Entity " + entity.getId() + " has no contract.");
                }
                writer.println(entity.getId());
                writer.println(entity.getFirstName());
                writer.println(entity.getLastName());
                writer.println(entity.getContract().getId());
                writer.println(entity.getBonusDostavljaca().iznosBonusaNaOsnovnuPlacu());

            }
            writer.flush();

        } catch (FileNotFoundException e) {
            throw new RuntimeException("Failed to save entities to file: " + DELIVERERS_FILE_PATH, e);
        }
    }

    @Override
    public void save(T entity) {
        Set<T> entities = findAll();
        if(Optional.ofNullable(entity.getId()).isEmpty()){
            entity.setId(generateNewId());
        }
        entities.add(entity);
        save(entities);
    }

    private Long generateNewId(){
        return findAll().stream().map(b -> b.getId())
                .max((i1, i2) -> i1.compareTo(i2)).orElse(1l) + 1;
    }
}
