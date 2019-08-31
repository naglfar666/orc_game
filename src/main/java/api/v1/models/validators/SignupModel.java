package api.v1.models.validators;

import api.v1.handlers.TextResources;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class SignupModel {

    @NotNull(message = TextResources.EMAIL_EMPTY)
    @Email(message = TextResources.EMAIL_NOT_VALID)
    private String email;

    @NotNull(message = TextResources.PASSWORD_EMPTY)
    @Size(min = 6, max = 32, message = TextResources.PASSWORD_NOT_VALID)
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
