package hr.java.restaurant.repository;
import hr.java.restaurant.model.Bonus;
import hr.java.restaurant.model.Chef;
import hr.java.restaurant.model.Contract;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ChefRepository <T extends Chef> extends AbstractRepository<T>{
    private static final String CHEFS_FILE_PATH = "dat/chefs.txt";
    private static final Integer NUMBER_OF_ROWS_PER_CHEFS = 5;

    public ContractRepository<Contract> contractRepository;

    public ChefRepository(ContractRepository<Contract> contractRepository) {
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
    public List<T> findAll() {
        List<T> chefs = new ArrayList<>();

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
    void save(List<T> entities) {

    }
}
