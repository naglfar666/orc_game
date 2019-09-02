package api.v1.controllers;

import api.v1.entities.User;
import api.v1.handlers.Constants;
import api.v1.handlers.TextResources;
import api.v1.models.BaseResponse;
import api.v1.models.validators.SignupModel;
import api.v1.repositories.UserRepo;
import api.v1.utils.PasswordEncryptor;
import api.v1.utils.RandomString;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(path = "/api/v1/auth")
public class AuthController {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private JavaMailSender mailSender;

    @CrossOrigin
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

    @CrossOrigin
    @PostMapping(path = "/signin")
    public ResponseEntity<?> signin(@Valid @RequestBody SignupModel fields) {
        User userExist = userRepo.findByEmail(fields.getEmail());

        if (userExist == null) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(
                            BaseResponse.builder()
                            .type("error")
                            .text(TextResources.USER_EMAIL_NOT_FOUND)
                            .build()
                    );
        }

        if (!PasswordEncryptor.verifyPassword(fields.getPassword(), userExist.getPassword())) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(
                            BaseResponse.builder()
                            .type("error")
                            .text(TextResources.PASSWORD_NOT_CORRECT)
                            .build()
                    );
        }

        if (userExist.getStatus() == 0) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(
                            BaseResponse.builder()
                            .type("error")
                            .text(TextResources.USER_NOT_VERIFIED)
                            .build()
                    );
        }

        Map<String,Object> claims = new HashMap<String, Object>();

        claims.put("userId", userExist.getId());
        claims.put("sub", "Auth token");
        claims.put("roles","User");
        claims.put("iss",Constants.JWT_ISSUER);
        claims.put("iat", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        String jwtToken = Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS512, Constants.JWT_SECRET_KEY).compact();

        return ResponseEntity.ok(
                BaseResponse.builder()
                        .type("success")
                        .data("Bearer " + jwtToken)
                        .build()
        );
    }
}
