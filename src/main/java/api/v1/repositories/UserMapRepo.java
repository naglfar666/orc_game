package api.v1.repositories;

import api.v1.entities.UserMap;
import org.springframework.data.repository.CrudRepository;

public interface UserMapRepo extends CrudRepository<UserMap, Integer> {

    UserMap findByUserId(Integer userId);

}
