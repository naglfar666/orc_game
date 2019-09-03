package api.v1.controllers;

import api.v1.entities.GameObjectMap;
import api.v1.models.BaseResponse;
import api.v1.repositories.GameObjectMapRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/game_object")
public class GameObjectController {

    @Autowired
    private GameObjectMapRepo gameObjectMapRepo;

    @CrossOrigin
    @GetMapping(path = "/all")
    public ResponseEntity<?> getAllGameObjects() {
        Iterable<GameObjectMap> gameObjects = gameObjectMapRepo.findAll();

        return ResponseEntity
                .ok()
                .body(
                        BaseResponse.builder()
                        .type("success")
                        .data(gameObjects)
                        .build()
                );
    }
}
