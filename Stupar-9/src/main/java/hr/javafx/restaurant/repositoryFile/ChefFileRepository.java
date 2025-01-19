package hr.javafx.restaurant.repositoryFile;

import hr.javafx.restaurant.model.Bonus;
import hr.javafx.restaurant.model.Chef;
import hr.javafx.restaurant.model.Contract;

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
 * Represents a repository for managing {@link Chef} objects.
 * This class provides methods to retrieve, save, and manage chefs from a data source.
 * @param <T> <T> a type parameter that extends {@link Chef}.
 */

public class ChefFileRepository<T extends Chef> extends AbstractFileRepository<T> {
    private static final String CHEFS_FILE_PATH = "dat/chefs.txt";
    private static final Integer NUMBER_OF_ROWS_PER_CHEFS = 5;

    public ContractFileRepository<Contract> contractRepository;

    public ChefFileRepository(ContractFileRepository<Contract> contractRepository) {
        this.contractRepository = contractRepository;
    }

    @Override
    public T findById(Long id) {
        return findAll().stream()
                .filter(chef -> chef.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Set<T> findAll() {
        Set<T> chefs = new HashSet<>();

        try{
            Stream<String> stream = Files.lines(Path.of(CHEFS_FILE_PATH));
            List<String> fileRows = stream.collect(Collectors.toList());

            for(Integer i = 0; i < (fileRows.size() / NUMBER_OF_ROWS_PER_CHEFS); i++){
                Long id = Long.parseLong(fileRows.get(i * NUMBER_OF_ROWS_PER_CHEFS));
                String firstName = fileRows.get(i * NUMBER_OF_ROWS_PER_CHEFS + 1);
                String lastName = fileRows.get(i * NUMBER_OF_ROWS_PER_CHEFS + 2);
                Long contractId = Long.parseLong(fileRows.get(i * NUMBER_OF_ROWS_PER_CHEFS + 3));

                Contract contract = contractRepository.findById(contractId);

                BigDecimal bonusAmount = BigDecimal.valueOf(Double.parseDouble(fileRows.get(i * NUMBER_OF_ROWS_PER_CHEFS + 4)));
                Bonus chefBonus = new Bonus(bonusAmount);

                @SuppressWarnings("unchecked")
                T chef = (T) new Chef.BuilderChef(id)
                        .chefFirstName(firstName)
                        .chefLastName(lastName)
                        .chefContract(contract)
                        .chefBonusKuhara(chefBonus)
                        .build();
                chefs.add(chef);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return chefs;
    }

    @Override
    public void save(Set<T> entities) { // RADI
        try(PrintWriter writer = new PrintWriter(CHEFS_FILE_PATH)){
            for(T entity : entities){
                if (entity.getContract() == null) {
                    throw new NullPointerException("Entity " + entity.getId() + " has no contract.");
                }
                writer.println(entity.getId());
                writer.println(entity.getFirstName());
                writer.println(entity.getLastName());
                writer.println(entity.getContract().getId());
                writer.println(entity.getBonusKuhara().iznosBonusaNaOsnovnuPlacu());

            }
            writer.flush();

        } catch (FileNotFoundException e) {
            throw new RuntimeException("Failed to save entities to file: " + CHEFS_FILE_PATH, e);
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
