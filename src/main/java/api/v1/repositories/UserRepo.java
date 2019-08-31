package api.v1.repositories;

import api.v1.entities.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepo extends CrudRepository<User, Integer> {

    User findByEmail(String email);
    User findByActivationKey(String activationKey);

}
