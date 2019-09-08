package api.v1.repositories;

import api.v1.entities.GameObjectMapLootObject;
import org.springframework.data.repository.CrudRepository;

public interface GameObjectMapLootObjectRepo extends CrudRepository<GameObjectMapLootObject, Integer> {

    GameObjectMapLootObject findByGameObjectMapIdAndLootObjectId(Integer gameObjectMapId, Integer lootObjectId);
}
