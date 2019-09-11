package api.v1.repositories;

import api.v1.entities.UserRespect;
import org.springframework.data.repository.CrudRepository;

public interface UserRespectRepo extends CrudRepository<UserRespect, Integer> {
    UserRespect findByUserId(Integer userId);
}
