package api.v1.controllers;

import api.v1.entities.UserLoot;
import api.v1.entities.UserMap;
import api.v1.entities.cache.UserMapCache;
import api.v1.handlers.TextResources;
import api.v1.models.BaseResponse;
import api.v1.models.validators.UserPositionModel;
import api.v1.repositories.UserLootRepo;
import api.v1.repositories.UserMapRepo;
import api.v1.repositories.cache.UserMapCacheRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;

@RestController
@RequestMapping(path = "/api/v1/user")
public class UserController {

    @Autowired
    UserMapCacheRepo userMapCacheRepo;

    @Autowired
    UserLootRepo userLootRepo;

    @CrossOrigin
    @PostMapping(path = "/set_position")
    public ResponseEntity<?>setPosition(@Valid @RequestBody UserPositionModel fields) {
        Integer userId = (Integer) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        UserMapCache userMapCache = userMapCacheRepo.findById(userId).orElse(null);

        if (userMapCache == null) {
            UserMapCache userMap = new UserMapCache(userId, fields.getxAxis(), fields.getyAxis(), new Date().getTime());
            userMapCacheRepo.save(userMap);
        } else {
            userMapCache.setxAxis(fields.getxAxis());
            userMapCache.setyAxis(fields.getyAxis());
            userMapCache.setDateAdd(new Date().getTime());
            userMapCacheRepo.save(userMapCache);
        }

//        UserMap userMap = userMapRepo.findByUserId(userId);
//
//        if (userMap != null) {
//            userMap.setyAxis(fields.getyAxis());
//            userMap.setxAxis(fields.getxAxis());
//            userMap.setDateAdd(new Date().getTime());
//            userMapRepo.save(userMap);
//        } else {
//            UserMap userNewMap = new UserMap();
//            userNewMap.setUserId(userId);
//            userNewMap.setyAxis(fields.getyAxis());
//            userNewMap.setxAxis(fields.getxAxis());
//            userNewMap.setDateAdd(new Date().getTime());
//
//            userMapRepo.save(userNewMap);
//        }

        return ResponseEntity
                .ok()
                .body(
                        BaseResponse.builder()
                        .type("success")
                        .build()
                );
    }

    @CrossOrigin
    @GetMapping(path = "/get_position")
    public ResponseEntity<?>getPosition() {
        Integer userId = (Integer) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        UserMapCache userMapCache = userMapCacheRepo.findById(userId).orElse(null);

        if (userMapCache == null) {
            UserMapCache userMap = new UserMapCache(userId, 1, 1, new Date().getTime());

            userMapCacheRepo.save(userMap);

            return ResponseEntity
                    .ok()
                    .body(
                            BaseResponse.builder()
                                    .type("success")
                                    .data(userMap)
                                    .build()
                    );
        }
//        UserMap userMap = userMapRepo.findByUserId(userId);

        return ResponseEntity
                .ok()
                .body(
                        BaseResponse.builder()
                        .type("success")
                        .data(userMapCache)
                        .build()
                );
    }

    @CrossOrigin
    @GetMapping(path = "/bag")
    public ResponseEntity<?> bag() {
        Integer userId = (Integer) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Iterable<UserLoot> loot = userLootRepo.findAllByUserIdOrderByIdDesc(userId);

        return ResponseEntity.ok()
                .body(
                        BaseResponse.builder()
                        .type("success")
                        .data(loot)
                        .build()
                );
    }

    @CrossOrigin
    @GetMapping(path = "/eat/{lootObjectId}")
    public ResponseEntity<?> eat (@PathVariable Integer lootObjectId) {
        Integer userId = (Integer) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        UserLoot loot = userLootRepo.findByUserIdAndLootObjectId(userId, lootObjectId);

        if (loot == null) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(
                            BaseResponse.builder()
                            .type("error")
                            .text(TextResources.USER_CANNOT_USE_LOOT_OBJECT)
                            .build()
                    );
        }

        if (loot.getAmount() > 1) {
            loot.setAmount(loot.getAmount() - 1);
            loot.setDateAdd(new Date().getTime());

            userLootRepo.save(loot);
        } else {
            userLootRepo.deleteById(loot.getId());
        }

        return ResponseEntity.ok()
                .body(
                        BaseResponse.builder()
                        .type("success")
                        .build()
                );
    }
}
