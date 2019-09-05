package api.v1.repositories;

import api.v1.entities.Powerup;
import org.springframework.data.repository.CrudRepository;

public interface PowerupRepo extends CrudRepository<Powerup, Integer> {
}
