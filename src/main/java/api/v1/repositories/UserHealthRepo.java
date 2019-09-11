package api.v1.repositories;

import api.v1.entities.UserHealth;
import org.springframework.data.repository.CrudRepository;

public interface UserHealthRepo extends CrudRepository<UserHealth, Integer> {
    UserHealth findByUserId(Integer userId);
}
