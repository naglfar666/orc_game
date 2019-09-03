package api.v1.repositories;

import api.v1.entities.GameObject;
import org.springframework.data.repository.CrudRepository;

public interface GameObjectRepo extends CrudRepository<GameObject, Integer> {
}
