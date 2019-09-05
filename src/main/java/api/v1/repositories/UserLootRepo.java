package api.v1.repositories;

import api.v1.entities.UserLoot;
import org.springframework.data.repository.CrudRepository;

public interface UserLootRepo extends CrudRepository<UserLoot, Integer> {
}
