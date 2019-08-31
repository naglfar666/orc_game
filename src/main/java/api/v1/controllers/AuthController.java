package api.v1.controllers;

import api.v1.entities.User;
import api.v1.handlers.TextResources;
import api.v1.models.BaseResponse;
import api.v1.models.validators.SignupModel;
import api.v1.repositories.UserRepo;
import api.v1.utils.PasswordEncryptor;
import api.v1.utils.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Date;

@RestController
@RequestMapping(path = "/api/v1/auth")
public class AuthController {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private JavaMailSender mailSender;

    @PostMapping(path = "/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody SignupModel fields) {
        User userExists = userRepo.findByEmail(fields.getEmail());

        if (userExists != null) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(
                            BaseResponse.builder()
                            .type("error")
                            .text(TextResources.USER_EMAIL_FOUND)
                            .build()
                    );
        }

        String activationKey = RandomString.getAlphaNumericString(32);

        User u = new User();
        u.setEmail(fields.getEmail());
        u.setPassword(PasswordEncryptor.encrypt(fields.getPassword()));
        u.setDateAdd(new Date().getTime());
        u.setActivationKey(activationKey);
        u.setStatus(0);

        userRepo.save(u);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(fields.getEmail());
        message.setSubject("Registration successful");
        message.setText("You successfully signed up. Your activation key is " + activationKey);
        mailSender.send(message);

        return ResponseEntity
                .ok(
                        BaseResponse.builder()
                        .type("success")
                        .build()
                );
    }
}
