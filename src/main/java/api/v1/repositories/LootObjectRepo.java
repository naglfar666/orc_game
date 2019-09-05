package api.v1.repositories;

import api.v1.entities.LootObject;
import org.springframework.data.repository.CrudRepository;

public interface LootObjectRepo extends CrudRepository<LootObject, Integer> {
}
