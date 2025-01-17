package hr.javafx.restaurant.repository;

import hr.javafx.restaurant.model.Bonus;
import hr.javafx.restaurant.model.Contract;
import hr.javafx.restaurant.model.Deliverer;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

/**
 * Represents a repository for managing {@link Deliverer} objects.
 * This class provides methods to retrieve, save, and manage deliverer data from a data source.
 * @param <T> a type parameter that extends {@link Deliverer}.
 */

public class DelivererRepository <T extends Deliverer> extends AbstractRepository<T>{
    private static final String DELIVERERS_FILE_PATH = "dat/deliverers.txt";
    private static final Integer NUMBER_OF_ROWS_PER_DELIVERERS = 5;

    public DelivererRepository(ContractRepository<Contract> contractRepository) {
        this.contractRepository = contractRepository;
    }

    public ContractRepository<Contract> contractRepository;

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
        try {
            List<String> fileRows = Files.readAllLines(Path.of(DELIVERERS_FILE_PATH));
            int currentIndex = 0;

            while (currentIndex < fileRows.size()) {
                // Parsiraj ID
                Long id = Long.parseLong(fileRows.get(currentIndex++).trim());

                // Parsiraj ime i prezime
                String firstName = fileRows.get(currentIndex++).trim();
                String lastName = fileRows.get(currentIndex++).trim();

                // Parsiraj ID ugovora
                Long contractId = Long.parseLong(fileRows.get(currentIndex++).trim());

                // Parsiraj bonus
                BigDecimal bonusAmount = new BigDecimal(fileRows.get(currentIndex++).trim());

                // Parsiraj putanje slika (ako postoje)
                List<String> imagePaths = new ArrayList<>();
                if (currentIndex < fileRows.size() && !fileRows.get(currentIndex).isEmpty()) {
                    imagePaths = Arrays.asList(fileRows.get(currentIndex++).trim().split(","));
                } else {
                    currentIndex++; // Preskoči prazan redak
                }

                // Dohvati ugovor prema ID-u ugovora
                Contract contract = contractRepository.findById(contractId);

                // Stvori Bonus objekt
                Bonus delivererBonus = new Bonus(bonusAmount);

                // Kreiraj objekt Deliverer
                @SuppressWarnings("unchecked")
                T deliverer = (T) new Deliverer.BuilderDeliverer(id)
                        .delivererFirstName(firstName)
                        .delivererLastName(lastName)
                        .delivererContract(contract)
                        .delivererBonusDostavljaca(delivererBonus)
                        .build();

                // Dodaj putanje slika u objekt
                deliverer.setImagePaths(imagePaths);

                // Dodaj dostavljača u skup
                deliverers.add(deliverer);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading deliverers file: " + DELIVERERS_FILE_PATH, e);
        } catch (NumberFormatException e) {
            throw new RuntimeException("Error parsing deliverers data. Check the file format.", e);
        }
        return deliverers;
    }





    @Override
    public void save(Set<T> entities) {
        try (PrintWriter writer = new PrintWriter(DELIVERERS_FILE_PATH)) {
            for (T entity : entities) {
                if (entity.getContract() == null) {
                    throw new IllegalStateException("Deliverer " + entity.getId() + " has no associated contract.");
                }

                writer.println(entity.getId());
                writer.println(entity.getFirstName());
                writer.println(entity.getLastName());
                writer.println(entity.getContract().getId());
                writer.println(entity.getBonusDostavljaca().iznosBonusaNaOsnovnuPlacu());
                writer.println(String.join(",", entity.getImagePaths()));
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to save deliverers to file: " + DELIVERERS_FILE_PATH, e);
        }
    }

    @Override
    public void save(T entity) {
        Set<T> entities = findAll();
        if (entity.getId() == null) {
            entity.setId(generateNewId());
        }
        entities.removeIf(e -> e.getId().equals(entity.getId())); // Remove old instance
        entities.add(entity);
        save(entities);
    }

    private Long generateNewId(){
        return findAll().stream().map(b -> b.getId())
                .max((i1, i2) -> i1.compareTo(i2)).orElse(1l) + 1;
    }
}
