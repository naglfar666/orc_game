package api.v1.components;

import api.v1.entities.cache.UserMapCache;
import api.v1.repositories.UserMapRepo;
import api.v1.repositories.cache.UserMapCacheRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class UserMapScheduling {

    private static final Logger logger = LoggerFactory.getLogger(UserMapScheduling.class);

    @Autowired
    UserMapCacheRepo userMapCacheRepo;

    @Autowired
    UserMapRepo userMapRepo;

    @Scheduled(fixedRate = 1000 * 60)
    public void updateUserMap() {

        Iterable<UserMapCache> usersOnMap = userMapCacheRepo.findAll();

        logger.debug("User map position updated");
    }
}
