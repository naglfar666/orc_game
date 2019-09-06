package api.v1.controllers;

import api.v1.components.UserMapScheduling;
import api.v1.entities.GameObjectMap;
import api.v1.entities.GameObjectMapLootObject;
import api.v1.entities.UserMapLootedObject;
import api.v1.handlers.TextResources;
import api.v1.models.BaseResponse;
import api.v1.repositories.GameObjectMapRepo;
import api.v1.repositories.UserMapLootedObjectRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping(path = "/api/v1/game_object")
public class GameObjectController {

    private static final Logger logger = LoggerFactory.getLogger(UserMapScheduling.class);

    @Autowired
    private GameObjectMapRepo gameObjectMapRepo;

    @Autowired
    private UserMapLootedObjectRepo userMapLootedObjectRepo;

    @CrossOrigin
    @GetMapping(path = "/all")
    public ResponseEntity<?> getAllGameObjects() {
        Iterable<GameObjectMap> gameObjects = gameObjectMapRepo.findAll();

        for (GameObjectMap objectMap : gameObjects) {
            objectMap.getGameObjectMapLootObject().clear();
        }

        return ResponseEntity
                .ok()
                .body(
                        BaseResponse.builder()
                        .type("success")
                        .data(gameObjects)
                        .build()
                );
    }

    @CrossOrigin
    @GetMapping(path = "/open/{id}")
    public ResponseEntity<?> openObject(@PathVariable Integer id) {
        logger.debug("Requested game object open id:" + id.toString());
        GameObjectMap gameObjectMap = gameObjectMapRepo.findById(id).orElse(null);

        if (gameObjectMap == null) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(
                            BaseResponse.builder()
                            .type("error")
                            .text(TextResources.GAME_OBJECT_NOT_FOUND)
                            .build()
                    );
        }

        Integer userId = (Integer) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Set<GameObjectMapLootObject> lootObjects = gameObjectMap.getGameObjectMapLootObject();

        List<GameObjectMapLootObject> lootObjectsList = new ArrayList<>(lootObjects);
        for (GameObjectMapLootObject loot: lootObjectsList) {
            UserMapLootedObject lootedObject = userMapLootedObjectRepo.findByLootedObjectIdAndUserId(loot.getId(), userId);

            if (lootedObject != null) {
                logger.debug("Object is not null:" + lootedObject.getId().toString());
                lootObjects.remove(loot);
            }
        }

        return ResponseEntity
                .ok()
                .body(
                        BaseResponse.builder()
                        .type("success")
                        .data(lootObjects)
                        .build()
                );
    }
}
