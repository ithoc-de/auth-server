package de.ithoc.auth.domain.login;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController implements LoginApi {

    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @Override
    public ResponseEntity<LoginResponseBody> login(LoginRequestBody loginRequestBody) {

        boolean login = loginService.login(loginRequestBody.getUsername(), loginRequestBody.getPassword());

        return ResponseEntity.ok(new LoginResponseBody().success(login));
    }

}
