package hr.java.restaurant.repository;

import hr.java.restaurant.model.Bonus;
import hr.java.restaurant.model.Contract;
import hr.java.restaurant.model.Deliverer;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    void save(List<T> entities) {

    }
}
