package api.v1.repositories.cache;

import api.v1.entities.cache.UserMapCache;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapCacheRepo extends CrudRepository<UserMapCache, Integer> {
}
