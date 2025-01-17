package hr.javafx.restaurant.repository;

import hr.javafx.restaurant.model.Address;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Represents a repository for managing {@link Address} objects.
 * This class provides methods to retrieve, save, and manage addresses from a data source.
 * @param <T> a type parameter that extends {@link Address}.
 */

public class AddressRepository <T extends Address> extends AbstractRepository<T> {
    private static final String ADDRESSES_FILE_PATH = "dat/addresses.txt";
    private static final Integer NUMBER_OF_ROWS_PER_ADDRESSES = 5;

    @Override
    public T findById(Long id) {
        return findAll().stream()
                .filter(address -> address.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public HashSet<T> findAll() {
        HashSet<T> addresses = new HashSet<>();

        try{
            Stream<String> stream = Files.lines(Path.of(ADDRESSES_FILE_PATH));
            List<String> fileRows = stream.collect(Collectors.toList());

            for(Integer i = 0; i < (fileRows.size() / NUMBER_OF_ROWS_PER_ADDRESSES); i++){
                Long id = Long.parseLong(fileRows.get(i * NUMBER_OF_ROWS_PER_ADDRESSES));
                String street = fileRows.get(i * NUMBER_OF_ROWS_PER_ADDRESSES + 1);
                String houseNumber = fileRows.get(i * NUMBER_OF_ROWS_PER_ADDRESSES + 2);
                String city = fileRows.get(i * NUMBER_OF_ROWS_PER_ADDRESSES + 3);
                String postalCode = fileRows.get(i * NUMBER_OF_ROWS_PER_ADDRESSES + 4);

                @SuppressWarnings("unchecked")
                T address = (T) new Address.BuilderAddress(id)
                        .atStreet(street)
                        .atHouseNumber(houseNumber)
                        .atCity(city)
                        .atPostalCode(postalCode)
                        .build();
                addresses.add(address);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return addresses;
    }


    @Override
    public void save(T entity) {

    }

    @Override
    public void save(Set<T> entities) {
        try(PrintWriter writer = new PrintWriter(ADDRESSES_FILE_PATH);) {
            for(T entity : entities){
                writer.println(entity.getId());
                writer.println(entity.getStreet());
                writer.println(entity.getHouseNumber());
                writer.println(entity.getCity());
                writer.println(entity.getPostalCode());
            }
            writer.flush();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
