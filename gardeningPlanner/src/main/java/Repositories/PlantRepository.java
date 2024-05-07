package Repositories;

import org.springframework.data.repository.CrudRepository;
import Tables.Plant;

public interface PlantRepository extends CrudRepository<Plant, Integer> {

}
