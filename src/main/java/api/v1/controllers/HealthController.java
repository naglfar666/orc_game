package api.v1.controllers;

import api.v1.entities.UserHealth;
import api.v1.models.BaseResponse;
import api.v1.repositories.UserHealthRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/health")
public class HealthController {

    @Autowired
    UserHealthRepo userHealthRepo;

    @CrossOrigin
    @GetMapping(path = "/get")
    public ResponseEntity<?> getHealth() {
        Integer userId = (Integer) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        UserHealth health = userHealthRepo.findByUserId(userId);

        if (health == null) {
            UserHealth newHealth = new UserHealth();
            newHealth.setUserId(userId);
            newHealth.setAmount(100);

            userHealthRepo.save(newHealth);
            return ResponseEntity.ok()
                    .body(
                            BaseResponse.builder()
                                    .type("success")
                                    .data(newHealth)
                                    .build()
                    );
        }

        return ResponseEntity.ok()
                .body(
                        BaseResponse.builder()
                        .type("success")
                        .data(health)
                        .build()
                );
    }
}
