package api.v1.controllers;

import api.v1.entities.UserLoot;
import api.v1.handlers.TextResources;
import api.v1.models.BaseResponse;
import api.v1.repositories.UserLootRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping(path = "/api/v1/food")
public class FoodController {

    @Autowired
    UserLootRepo userLootRepo;
    
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
