package api.v1.controllers;

import api.v1.components.UserMapScheduling;
import api.v1.entities.GameObjectMap;
import api.v1.entities.GameObjectMapLootObject;
import api.v1.entities.UserLoot;
import api.v1.entities.UserMapLootedObject;
import api.v1.handlers.TextResources;
import api.v1.models.BaseResponse;
import api.v1.repositories.GameObjectMapLootObjectRepo;
import api.v1.repositories.GameObjectMapRepo;
import api.v1.repositories.UserLootRepo;
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

    @Autowired
    private GameObjectMapLootObjectRepo gameObjectMapLootObjectRepo;

    @Autowired
    private UserLootRepo userLootRepo;

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
    @GetMapping(path = "/open/{mapObjectId}")
    public ResponseEntity<?> openObject(@PathVariable Integer mapObjectId) {
        GameObjectMap gameObjectMap = gameObjectMapRepo.findById(mapObjectId).orElse(null);

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
        // Вытаскиваем список всех лутаемых объектов и очищаем от тех, что были получены
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

    @CrossOrigin
    @GetMapping(path = "/loot/{mapObjectId}/{gameObjectMapLootObjectId}")
    public ResponseEntity<?> lootObject (
            @PathVariable Integer mapObjectId,
            @PathVariable Integer gameObjectMapLootObjectId
    ) {

        Integer userId = (Integer) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        // Проверяем, получал ли уже пользователь этот объект
        UserMapLootedObject lootedObjectExist = userMapLootedObjectRepo.findByLootedObjectIdAndUserId(gameObjectMapLootObjectId, userId);

        if (lootedObjectExist != null) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(
                            BaseResponse.builder()
                            .type("error")
                            .text(TextResources.USER_CANNOT_LOOT_OBJECT)
                            .build()
                    );
        }

        // Получаем лутаемый объект
        GameObjectMapLootObject lootObjectMap = gameObjectMapLootObjectRepo.findById(gameObjectMapLootObjectId).orElse(null);
        if (lootObjectMap == null) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(
                            BaseResponse.builder()
                                    .type("error")
                                    .text(TextResources.LOOT_OBJECT_ON_MAP_NOT_FOUND)
                                    .build()
                    );
        }

        // Проверяем, существует ли этот объект в таблице для лутания
        GameObjectMapLootObject lootedObjectAvailable = gameObjectMapLootObjectRepo.findById(gameObjectMapLootObjectId).orElse(null);

        if (lootedObjectAvailable == null) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(
                            BaseResponse.builder()
                                    .type("error")
                                    .text(TextResources.USER_CANNOT_LOOT_OBJECT)
                                    .build()
                    );
        }
        // Добавляем в таблицу, что пользователь уже получил этот объект

        Long dateLootAdd = new Date().getTime();
        UserMapLootedObject lootedObject = new UserMapLootedObject();
        lootedObject.setUserId(userId);
        lootedObject.setLootedObjectId(gameObjectMapLootObjectId);
        lootedObject.setDateAdd(dateLootAdd);

        userMapLootedObjectRepo.save(lootedObject);
        // Добавляем в лут пользователю этот объект
        UserLoot userLootExist = userLootRepo.findByUserIdAndLootObjectId(userId, lootObjectMap.getLootObject().getId());

        if (userLootExist != null) {
            userLootExist.setAmount(userLootExist.getAmount() + 1);
            userLootExist.setDateAdd(dateLootAdd);

            userLootRepo.save(userLootExist);
        } else {
            UserLoot newUserLoot = new UserLoot();

            newUserLoot.setAmount(1);
            newUserLoot.setLootObjectId(lootObjectMap.getLootObject().getId());
            newUserLoot.setDateAdd(dateLootAdd);
            newUserLoot.setUserId(userId);
            userLootRepo.save(newUserLoot);
        }

        return ResponseEntity.ok()
                .body(
                        BaseResponse.builder()
                        .type("success")
                        .build()
                );
    }
}
