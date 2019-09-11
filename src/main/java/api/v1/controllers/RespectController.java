package api.v1.controllers;

import api.v1.entities.UserRespect;
import api.v1.models.BaseResponse;
import api.v1.repositories.UserRespectRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/respect")
public class RespectController {

    @Autowired
    private UserRespectRepo userRespectRepo;

    @CrossOrigin
    @GetMapping(path = "/get")
    public ResponseEntity<?> getRespect() {
        Integer userId = (Integer) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        UserRespect respect = userRespectRepo.findByUserId(userId);

        if (respect == null) {
            UserRespect newRespect = new UserRespect();
            newRespect.setUserId(userId);
            newRespect.setAmount(0);
            userRespectRepo.save(newRespect);

            return ResponseEntity.ok()
                    .body(
                            BaseResponse.builder()
                            .type("success")
                            .data(newRespect)
                            .build()
                    );
        }

        return ResponseEntity.ok()
                .body(
                        BaseResponse.builder()
                        .type("success")
                        .data(respect)
                        .build()
                );
    }
}
