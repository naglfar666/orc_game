package api.v1.repositories;

import api.v1.entities.UserMapLootedObject;
import org.springframework.data.repository.CrudRepository;

public interface UserMapLootedObjectRepo extends CrudRepository<UserMapLootedObject, Integer> {
    UserMapLootedObject findByLootedObjectIdAndUserId(Integer lootedObjectId, Integer userId);
}
